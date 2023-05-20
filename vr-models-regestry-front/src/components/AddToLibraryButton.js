import {useCookies} from "react-cookie";
import {Button} from "react-bootstrap";
import React from "react";

export default function AddToLibraryButton() {
    const [cookies] = useCookies(['auth'])
    if (cookies.auth !== undefined) {
        return (
            <div bg="primary" pill>
                <Button>Add to library</Button>
            </div>
        )
    }
    else return (<></>)
}