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
    const [alertMessage, setAlertMessage] = useState('Password incorrect');
    const [cookies, setCookies] = useCookies(['auth'])

    const useRegistration = () => {
        if (!username) {
            setAlertMessage('Login not provided')
            setShowAlert(true)
            return;
        }
        if (!password) {
            setAlertMessage('Password not provided')
            setShowAlert(true)
            return;
        }
        login('http://localhost:8080/auth/login', username, password).then(r => {
            setShow(!r.ok)
            console.log(r.ok)
            if (r.ok) {
                r.body.getReader().read().then( rawToken => {
                    let token = new TextDecoder().decode(rawToken.value)
                    setCookies('auth', token)
                })
            }
        })
    };

    const handleUsername = event => setUsername(event.target.value)
    const handlePassword = event => setPassword(event.target.value)

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    return (
        <>
            <Form className="d-flex">
                <Button variant="outline-info" onClick={handleShow}>sign up</Button>
            </Form>

            <Modal show={show} onHide={handleClose} animation={true} centered>
                <Modal.Header closeButton>
                    <Modal.Title>Modal heading</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Alert variant="danger" show={showAlert}>
                        {alertMessage}
                    </Alert>
                    <Form>
                        <Form.Group className="mb-3" controlId="username">
                            <Form.Label>User name</Form.Label>
                            <Form.Control type="text" placeholder="Enter user name" onChange={handleUsername}/>
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="password">
                            <Form.Label>Password</Form.Label>
                            <Form.Control type="password" placeholder="Password" onChange={handlePassword} />
                        </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>
                        Close
                    </Button>
                    <Button variant="primary" onClick={useRegistration}>
                        Login
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    );
}

export default AuthenticationForm;

AuthenticationForm.propsType = {
    setToken: PropTypes.func.isRequired
};