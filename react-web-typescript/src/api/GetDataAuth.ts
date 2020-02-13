export default async function getDataAuth(url: RequestInfo, token: string) {
    const response = await fetch(url, {
        method: 'get',
        headers: { 
            'Content-Type': 'application/json',
            'Authentication': `bearer ${token}`,
        },
    })
    return response;
}