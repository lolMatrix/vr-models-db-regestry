import AuthenticationForm from "./AuthenticationForm";
import RegistrationForm from "./RegistrationForm";
import React, {useEffect} from "react";
import {Button, Form} from "react-bootstrap";
import {useCookies} from "react-cookie";

export default function AuthorizationForms() {
    const [cookies, , removeCookies] = useCookies(['auth'])

    function logOut() {
        window.location.href = '/'
        removeCookies('auth')
    }

    if (cookies.auth === undefined) {
        return (
            <>
                <AuthenticationForm/>
                <RegistrationForm/>
            </>
        )
    } else {
        return (
            <>
                <Form className="d-flex">
                    <Button variant="outline-info" onClick={logOut}>log out</Button>
                </Form>
            </>
        )
    }
}