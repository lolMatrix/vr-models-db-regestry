import React from "react";
import {Container, Image, Nav, Navbar} from "react-bootstrap";
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom';
import logo from '../logo.svg'
import Home from "../pages/Home";
import Library from "../pages/Library";
import NavLinks from "./NavLinks";
import AuthorizationForms from "./AuthorizationForms";
import LoadModel from "../pages/LoadModel";

export default function Header() {
    return (
        <>
            <Navbar collapseOnSelect expand="md mb-2" bg="dark" variant="dark">
                <Container>
                    <Navbar.Brand href="/">
                        <Image src={logo}
                               height="30"
                               width="30"
                               className="d-inline-block align-top"/>
                    </Navbar.Brand>
                    <Navbar.Toggle aria-controls="navbarScroll"/>
                    <Navbar.Collapse id="navbarScroll">
                        <Nav className="me-auto my-2 my-lg-0" navbarScroll>
                            <NavLinks/>
                        </Nav>
                        <AuthorizationForms/>
                    </Navbar.Collapse>
                </Container>
            </Navbar>

            <Router>
                <Routes>
                    <Route exact path="/" element={<Home/>}/>
                    <Route exact path="/library" element={<Library/>}/>
                    <Route exact path="/upload" element={<LoadModel/>}/>
                </Routes>
            </Router>
        </>
    )
}