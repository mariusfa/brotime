export async function PostData(url: RequestInfo, body: any) {
    const response = await fetch(url, {
        method: 'post',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(body)
    })
    console.log("her")
    console.log(response.ok)
    return response;
}