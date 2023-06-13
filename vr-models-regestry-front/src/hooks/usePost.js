async function usePost(url, login, password) {
    try {
        return await fetch(
            url,
            {
                mode: 'cors',
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(
                    {
                        name: login,
                        password: password
                    }
                )
            }
        )
    } catch (e) {
        console.log(e)
        return {
            ok: false,
            value: e.body
        }
    }
}

export default usePost;