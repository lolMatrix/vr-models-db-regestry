export default function addToLibrary(id, token, setResult) {
    fetch(
        `http://localhost:8080/lib/${id}`,
        {
            mode: 'cors',
            method: 'POST',
            headers: {'Authorization': `Bearer ${token}`},
        }
    ).then(data => setResult(data.ok))
        .catch(e => console.log(e.data))
        .finally()
}