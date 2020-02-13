    export default async function postDataAuth(url: RequestInfo, body: any, token: string) {
    const response = await fetch(url, {
        method: 'post',
        headers: { 
            'Content-Type': 'application/json',
            'Authentication': `bearer ${token}`
        },
        body: JSON.stringify(body)
    })
    return response;
}