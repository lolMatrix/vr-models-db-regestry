export default async function useUpload(name, description, file, token) {
    let data = new FormData()
    data.append("container", file, file.name)
    return await fetch(
        `http://localhost:8080/container/${name}/${description}`,
        {
            mode: 'cors',
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`
            },
            body: data
        }
    )
}
