import { API_URL } from "./apiConfig";

const getData = async (endpoint: string) => {
    const result = await fetch(`${API_URL}${endpoint}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authentication': localStorage.getItem('token')!!
        }
    });
    return result
};

export default getData;
