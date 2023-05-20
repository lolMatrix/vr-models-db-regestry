import {Component} from "react";
import LibraryList from "../components/LibraryList";
import {Container} from "react-bootstrap";

export default class Library extends Component {
    render() {
        return (
            <Container>
                <LibraryList/>
            </Container>
        );
    }
}