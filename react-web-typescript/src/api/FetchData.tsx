export async function fetchData(url: RequestInfo) {
    const response = await fetch(url);
    const body = await response.json();
    return body
}