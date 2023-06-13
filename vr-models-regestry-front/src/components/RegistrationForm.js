import React, { useState } from 'react';
import Modal from 'react-bootstrap/Modal';
import {Alert, Button, Form} from "react-bootstrap";
import registration from "../hooks/usePost";

function RegistrationForm() {
    const [show, setShow] = useState(false);
    const [showAlert, setShowAlert] = useState(false);
    const [username, setUsername] = useState();
    const [password, setPassword] = useState();
    const [confirmPassword, setConfirmPassword] = useState();
    const [alertMessage, setAlertMessage] = useState('Логин или пароль не верен');

    const useRegistration = () => {
        if (!username) {
            setAlertMessage('Не представлен логин')
            setShowAlert(true)
            return;
        }
        if (!password) {
            setAlertMessage('Пароль не представлен')
            setShowAlert(true)
            return;
        }
        if (confirmPassword !== password) {
            setAlertMessage('Пароли не совпадают')
            setShowAlert(true)
            return;
        }
        registration('http://localhost:8080/auth/reg', username, password).then(r => {
            setAlertMessage('Логин или пароль не верен')
            setShow(!r.ok)
        })
    };

    const handleUsername = event => setUsername(event.target.value)
    const handlePassword = event => setPassword(event.target.value)
    const handleConfirmPassword = event => setConfirmPassword(event.target.value)

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    return (
        <>
            <Form className="d-flex">
                <Button variant="info" onClick={handleShow}>Регистрация</Button>
            </Form>

            <Modal show={show} onHide={handleClose} animation={true} centered>
                <Modal.Header closeButton>
                    <Modal.Title>Регистрация</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Alert variant="danger" show={showAlert}>
                        {alertMessage}
                    </Alert>
                    <Form>
                        <Form.Group className="mb-3" controlId="username">
                            <Form.Label>Имя пользователя</Form.Label>
                            <Form.Control type="text" placeholder="Введите имя" onChange={handleUsername}/>
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="password">
                            <Form.Label>Пароль</Form.Label>
                            <Form.Control type="password" placeholder="Введите пароль" onChange={handlePassword} />
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="confirmPassword">
                            <Form.Label>Подтвердите пароль</Form.Label>
                            <Form.Control type="password" placeholder="Подтверждение" onChange={handleConfirmPassword} />
                        </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>
                        Закрыть
                    </Button>
                    <Button variant="primary" onClick={useRegistration}>
                        Зарегистрироваться
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    );
}

export default RegistrationForm;