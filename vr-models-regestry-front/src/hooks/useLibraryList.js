import {useEffect, useState} from "react";
import {useCookies} from "react-cookie";

export default function useLibraryList(url) {
    const [data, setData] = useState();
    const [cookies] = useCookies(['auth'])
    useEffect(() => {
        fetch(
            url,
            {
                mode:'cors',
                headers: {
                    'Authorization': `Bearer ${cookies.auth}`
                }
            }
        ).then(response => response.json())
            .then(data => {
                console.log(data)
                setData(data)
            })
            .catch()
            .finally();
    }, [url, cookies]);
    return data;
}