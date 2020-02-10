export async function PostData(url: RequestInfo, body: any) {
    const response = await fetch(url, {
        method: 'post',
        body: JSON.stringify(body)
    })
    console.log(response)
    return await response.json();
}