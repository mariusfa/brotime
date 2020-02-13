export default async function putDataAuth(url: RequestInfo, body: any, token: string) {
    const response = await fetch(url, {
        method: 'put',
        headers: {
            'Content-Type': 'application/json',
            'Authentication': `bearer ${token}`
        },
        body: JSON.stringify(body)
    })
    return response;
}