import { API_URL } from './apiConfig';

const putData = async (endpoint: string, data: any) => {
    const authHeader = `Bearer ${localStorage.getItem('token')}`;

    const result = await fetch(`${API_URL}${endpoint}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            Authentication: authHeader,
        },
        body: JSON.stringify(data),
    });

    if (result.status === 401) {
        window.location.replace('/login');
    }
    
    return result;
};

export default putData;
