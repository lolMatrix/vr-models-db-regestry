import {useEffect, useState} from "react";

export default function useLibraryList(url, token) {
    const [data, setData] = useState();

    useEffect(() => {
        fetch(
            url,
            {
                mode:'cors',
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            }
        ).then(response => response.json())
            .then(data => {
                console.log(data)
                setData(data)
            })
            .catch()
            .finally();
    }, [url, token]);
    return data;
}