//'https://jsonplaceholder.typicode.com/posts?_limit=10'
async function usePost(url, login, password) {
    try {
        let response = await fetch(
            url,
            {
                mode: 'cors',
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(
                    {
                        name: login,
                        password: password
                    }
                )
            }
        )
        return response
    } catch (e) {
        console.log(e)
        return null
    }
}

export default usePost;