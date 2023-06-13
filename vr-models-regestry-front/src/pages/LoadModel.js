import {Component} from "react";
import {Container} from "react-bootstrap";
import LoadForm from "../components/LoadForm";

export default class LoadModel extends Component {
    render() {
        return (
            <Container>
                <LoadForm/>
            </Container>
        )
    }
}