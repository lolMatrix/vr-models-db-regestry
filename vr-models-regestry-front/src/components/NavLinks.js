import {NavLink} from "react-bootstrap";
import React from "react";
import {useCookies} from "react-cookie";

export default function NavLinks() {
    const [cookies, setCookies] = useCookies(['auth'])
    if (cookies.auth !== undefined) {
        return (
            <>
                <NavLink href="/">Список моделей</NavLink>
                <NavLink href="/library">Библиотека</NavLink>
                <NavLink href="/upload">Загрузить модель</NavLink>
            </>
        )
    } else {
        return (
            <>
                <NavLink href="/">Список моделей</NavLink>
            </>
        )
    }
}