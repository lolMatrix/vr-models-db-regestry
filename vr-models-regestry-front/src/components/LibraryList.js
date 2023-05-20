import {useCookies} from "react-cookie";
import useLibraryList from "../hooks/useLibraryList";
import {ListGroup} from "react-bootstrap";
import React from "react";

export default function LibraryList() {
    const [cookies] = useCookies(['auth'])
    const data = useLibraryList('http://localhost:8080/lib', cookies.auth)
    if (data === undefined) {
        return (
            <h1>Please, wait</h1>
        )
    }
    return (
        <ListGroup>
            {
                data.map((post) => {
                    return (
                        <ListGroup.Item className="d-flex justify-content-between align-items-start">
                            <div className="ms-2 me-auto">
                                <div className="fw-bold">{post.name}</div>
                                <p>{post.description}</p>
                            </div>
                        </ListGroup.Item>
                    );
                })
            }
        </ListGroup>
    )
}