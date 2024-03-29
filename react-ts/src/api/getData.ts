import { API_URL } from "./apiConfig";

const getData = async (endpoint: string) => {
    const authHeader = `Bearer ${localStorage.getItem('token')}`;
    
    const result = await fetch(`${API_URL}${endpoint}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authentication': authHeader
        }
    });

    if (result.status === 401) {
        window.location.replace('/login');
    }
    
    return result
};

export default getData;
