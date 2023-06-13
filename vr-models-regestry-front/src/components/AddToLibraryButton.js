import {useCookies} from "react-cookie";
import {Button} from "react-bootstrap";
import React, {useEffect, useState} from "react";
import PropTypes from "prop-types";
import addToLibrary from "../hooks/addToLibrary";

export default function AddToLibraryButton({ haveInLibrary, id }) {
    const [cookies] = useCookies(['auth'])
    const [inLibrary, setInLibrary] = useState(haveInLibrary)

    useEffect(() => {
        haveInLibrary = inLibrary
    })
    const add = () => {
        addToLibrary(id, cookies.auth, setInLibrary)
    }

    if (cookies.auth !== undefined) {
        return (
            <div>
                <Button
                    variant={inLibrary ? "success" : "outline-primary"}
                    onClick={add}
                    disabled={inLibrary}>
                    {inLibrary ? "Добавлено" : "Добавить"}
                </Button>
            </div>
        )
    }
    else return (<></>)
}


AddToLibraryButton.propsType = {
    haveInLibrary: PropTypes.func.isRequired,
    id: PropTypes.func.isRequired
};