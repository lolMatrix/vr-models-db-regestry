import useFetch from "../hooks/useFetch";
import {Button, Col, ListGroup} from "react-bootstrap";
import React, {useState} from "react";
import AddToLibraryButton from "./AddToLibraryButton";

function ModelList() {
    const [page, setPage] = useState(0)
    const data = useFetch(`http://localhost:8080/container?page=${page}`)
    console.log(data)

    function useList(currentPage) {
        setPage(currentPage)
    }

    function NextPage() {
        useList(page + 1)
    }

    function PrevPage() {
        useList(page - 1)
    }

    if (data === undefined) {
        return (
            <h1>Пожалуйста, подождите...</h1>
        )
    }
    return (
        <>
            <ListGroup>
                {
                    data.content.map((post) => {
                        return (
                            <ListGroup.Item className="d-flex justify-content-between align-items-start">
                                <div className="ms-2 me-auto">
                                    <div className="fw-bold">{post.name}</div>
                                    <p>{post.description}</p>
                                </div>
                                <AddToLibraryButton id={post.id} haveInLibrary={post.haveInLibrary}/>
                            </ListGroup.Item>
                        );
                    })
                }
            </ListGroup>
            <Col className="mt-2">
                <Button className="mx-1"
                        onClick={PrevPage}
                        style={{display: (data.totalPages > 1 && page !== 0) ? 'inline-block' : 'none'}}>
                    Назад
                </Button>
                <Button onClick={NextPage}
                        className="mx-1"
                        style={{display: (data.totalPages > 1 && page < data.totalPages - 1) ? 'inline-block' : 'none'}}>
                    Вперед
                </Button>
            </Col>
        </>
    );

}

export default ModelList;