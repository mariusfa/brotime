import { API_URL } from './apiConfig';

const postData = async (endpoint: string, data: any, auth: boolean = true) => {
    const requestHeaders: HeadersInit = new Headers();
    requestHeaders.set('Content-Type', 'application/json');
    if (auth) {
        const authHeader = `Bearer ${localStorage.getItem('token')}`;
        requestHeaders.set('Authentication', authHeader);
    }

    const result = await fetch(`${API_URL}${endpoint}`, {
        method: 'POST',
        headers: requestHeaders,
        body: JSON.stringify(data),
    });
    return result;
};

export default postData;
