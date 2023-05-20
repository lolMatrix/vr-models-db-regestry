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
    const [alertMessage, setAlertMessage] = useState('Password incorrect');

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
        console.log(`passwords: ['${password}', '${confirmPassword}'], equials: ${confirmPassword === password}`)
        if (confirmPassword !== password) {
            setAlertMessage('Password incorrect')
            setShowAlert(true)
            return;
        }
        registration('http://localhost:8080/auth/reg', username, password).then(r => setShow(!r.ok))
    };

    const handleUsername = event => setUsername(event.target.value)
    const handlePassword = event => setPassword(event.target.value)
    const handleConfirmPassword = event => setConfirmPassword(event.target.value)

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    return (
        <>
            <Form className="d-flex">
                <Button variant="info" onClick={handleShow}>sing in</Button>
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
                        <Form.Group className="mb-3" controlId="confirmPassword">
                            <Form.Label>Confitm password</Form.Label>
                            <Form.Control type="password" placeholder="Password" onChange={handleConfirmPassword} />
                        </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>
                        Close
                    </Button>
                    <Button variant="primary" onClick={useRegistration}>
                        Registration
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    );
}

export default RegistrationForm;