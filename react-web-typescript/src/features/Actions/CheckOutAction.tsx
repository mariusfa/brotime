import React from 'react';
import { API_PUT_TIME_URL } from '../../constants';
import putDataAuth from '../../api/PutDataAuth';

const CheckOutAction = () => {
    
    const handleCheckOut = () => {
        const newEndTime = new Date().getTime();
        const body = {
            'endTime': newEndTime
        }

        const storedToken = localStorage.getItem('token');
        if (storedToken) {
            putDataAuth(API_PUT_TIME_URL, body, storedToken)
        }
    }

    return (
        <div>
            <button onClick={handleCheckOut}>Check out</button>
        </div>
    );
}

export default CheckOutAction;