export async function postData(url: RequestInfo, body: any) {
    const response = await fetch(url, {
        method: 'post',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(body)
    })
    return response;
}