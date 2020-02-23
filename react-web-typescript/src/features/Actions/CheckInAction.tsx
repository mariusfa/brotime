import React from 'react';
import postDataAuth from '../../api/PostDataAuth';
import { API_POST_TIME_URL } from '../../constants';

const CheckInAction = () => {
    
    const handleCheckIn = () => {
        const timeStamp = new Date().getTime();
        const timeZone = "Europe/Berlin";
        const body = {
            'timeStamp': timeStamp,
            'timeZone': timeZone
        }

        const storedToken = localStorage.getItem('token');
        if (storedToken) {
            postDataAuth(API_POST_TIME_URL, body, storedToken)
        }
    }

    return (
        <div>
            <button onClick={handleCheckIn}>Check in</button>
        </div>
    );
}

export default CheckInAction;