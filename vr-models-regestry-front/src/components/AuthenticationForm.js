import React, { useState } from 'react';
import Modal from 'react-bootstrap/Modal';
import {Alert, Button, Form} from "react-bootstrap";
import login from "../hooks/usePost";
import PropTypes from "prop-types";
import {useCookies} from "react-cookie";

function AuthenticationForm() {
    const [show, setShow] = useState(false);
    const [showAlert, setShowAlert] = useState(false);
    const [username, setUsername] = useState();
    const [password, setPassword] = useState();
    const [alertMessage, setAlertMessage] = useState('Имя пользователя или пароль неверны');
    const [, setCookies] = useCookies(['auth'])

    const useAuth = () => {
        if (!username) {
            setAlertMessage('Не представлено имя пользователя')
            setShowAlert(true)
            return;
        }
        if (!password) {
            setAlertMessage('Не представлен пароль')
            setShowAlert(true)
            return;
        }
        login('http://localhost:8080/auth/login', username, password).then(r => {
            if (r.ok) {
                r.body.getReader().read().then(rawToken => {
                    let token = new TextDecoder().decode(rawToken.value)
                    setCookies('auth', token)
                    window.location.href = '/'
                })
            } else {
                r.body.getReader().read().then(error => {
                    console.log(error)
                    let message = new TextDecoder().decode(error.value)
                    setShowAlert(true)
                    setAlertMessage(message)
                })
            }
        })
    };

    const handleUsername = event => setUsername(event.target.value)
    const handlePassword = event => setPassword(event.target.value)

    const handleClose = () => {
        setShow(false);
        setShowAlert(false)
    }
    const handleShow = () => setShow(true);

    return (
        <>
            <Form className="d-flex">
                <Button variant="outline-info" onClick={handleShow}>Авторизация</Button>
            </Form>

            <Modal show={show} onHide={handleClose} animation={true} centered>
                <Modal.Header closeButton>
                    <Modal.Title>Авторизация</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Alert variant="danger" show={showAlert}>
                        {alertMessage}
                    </Alert>
                    <Form>
                        <Form.Group className="mb-3" controlId="username">
                            <Form.Label>Имя пользователя</Form.Label>
                            <Form.Control type="text" placeholder="Имя пользователя" onChange={handleUsername}/>
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="password">
                            <Form.Label>Пароль</Form.Label>
                            <Form.Control type="password" placeholder="Пароль" onChange={handlePassword} />
                        </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>
                        Закрыть
                    </Button>
                    <Button variant="primary" onClick={useAuth}>
                        Авторизоваться
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    );
}

export default AuthenticationForm;
