import {NavLink} from "react-bootstrap";
import React from "react";
import {useCookies} from "react-cookie";

export default function NavLinks() {
    const [cookies, setCookies] = useCookies(['auth'])
    if (cookies.auth !== undefined) {
        return (
            <>
                <NavLink href="/">Home</NavLink>
                <NavLink href="/library">Library</NavLink>
            </>
        )
    } else {
        return (
            <>
                <NavLink href="/">Home</NavLink>
            </>
        )
    }
}