import {useEffect, useState} from "react";
import {useCookies} from "react-cookie";

function useFetch(url) {
    const [data, setData] = useState();
    const [cookies] = useCookies(['auth'])
    useEffect(() => {
        fetch(
            url,
            {
                mode: 'cors',
                headers: cookies.auth !== undefined ? {'Authorization': `Bearer ${cookies.auth}`} : {}
            }
        ).then(response => response.json())
            .then(data => {
                console.log(data)
                setData(data)
            })
            .catch()
            .finally();
    }, [url]);
    return data;
}

export default useFetch;