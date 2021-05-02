
const fetchData = async (url: string, auth: boolean, options: RequestInit) => {
    const requestHeaders: HeadersInit = new Headers();
    requestHeaders.set('Content-Type', 'application/json');

    if (auth) {
        const authHeader = `Bearer ${localStorage.getItem('token')}`;
        requestHeaders.set('Authentication', authHeader);
    }

    let optionsWithHeaders = { Headers: requestHeaders, ...options };

    const result = await fetch(url, optionsWithHeaders);
    if (result.ok) {
        const content = await result.text();
        if (content.length > 0) {
            return JSON.parse(content);
        }
    }

    if (result.status === 401) {
        throw new Error(String(result.status));
    }

    if (!result.ok) {
        throw new Error(result.statusText);
    }
};

export default fetchData;
