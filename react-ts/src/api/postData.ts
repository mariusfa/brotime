import { API_URL } from "./apiConfig";

const postData = async (endpoint: string, data: any) => {
    const result = await fetch(`${API_URL}${endpoint}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    });
    return result
};

export default postData;
