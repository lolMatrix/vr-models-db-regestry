import {Component} from "react";
import ModelList from "../components/ModelList";
import {Container} from "react-bootstrap";

export default class Home extends Component {
    render() {
        return (
            <Container>
                <ModelList/>
            </Container>
        )
    }
}