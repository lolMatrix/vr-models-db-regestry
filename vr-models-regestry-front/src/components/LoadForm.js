import {Alert, Button, Form} from "react-bootstrap";
import React, {useState} from "react";
import useUpload from "../hooks/useUpload";
import {useCookies} from "react-cookie";
import PropTypes from "prop-types";
import AuthenticationForm from "./AuthenticationForm";

export default function LoadForm() {
    const [cookies] = useCookies(['auth'])
    const [name, setName] = useState()
    const [description, setDescription] = useState()
    const [file, setFile] = useState()
    const [showAlert, setShowAlert] = useState(false);
    const [successful, setSuccessful] = useState(false);
    const [message, setMessage] = useState("Ошибка");
    const handleName = event => {
        let name = event.target.value
        if (name) {
            setName(name)
        }
    }
    const handleDescription = event => {
        let description = event.target.value
        if (description) {
            setDescription(description)
        }
    }
    const handleFile = event => {
        let files = event.target.files
        if (files) {
            setFile(files[0])
        }
    }

    const useUploadModel = () => {
        useUpload(name, description, file, cookies.auth).then(r => {
            setShowAlert(true)
            setSuccessful(r.ok)
            if (r.ok) {
                setMessage("Успешно загружено")
            } else {
                r.body.getReader().read().then(error => {
                    let message = new TextDecoder().decode(error.value)
                    setMessage(message)
                })
            }
        })
    }

    return (
        <Form>
            <Alert variant={successful ? "success" : "danger"} show={showAlert}>
                {message}
            </Alert>
            <Form.Group className="mb-3" controlId="name">
                <Form.Label>Название модели</Form.Label>
                <Form.Control type="text" placeholder="Название модели" onChange={handleName}/>
            </Form.Group>
            <Form.Group className="mb-3" controlId="desc">
                <Form.Label>Описание</Form.Label>
                <Form.Control type="textarea" placeholder="Описание" onChange={handleDescription} />
            </Form.Group>
            <Form.Group className="mb-3" controlId="file">
                <Form.Label>Контейнер модели</Form.Label>
                <Form.Control type="file" accept=".zip" onChange={handleFile}/>
            </Form.Group>
            <Form.Group className="mb-3" controlId="submit">
                <Button onClick={useUploadModel} disabled={!name && !description && !file}>Загрузить</Button>
            </Form.Group>
        </Form>
    )
}