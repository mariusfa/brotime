import React from 'react';
import postDataAuth from '../api/PostDataAuth';
import putDataAuth from '../api/PutDataAuth';
import { API_POST_TIME_URL, API_PUT_TIME_URL } from '../constants';
import { TimeDiff } from '../features/TimeDiff'
import { LatestTimeEntry } from '../features/LatestTimeEntry';

const HomePage = () => {

    const handleCheckIn = async () => {
        const timeStamp = new Date().getTime();
        const timeZone = "Europe/Berlin";
        const body = {
            'timeStamp': timeStamp,
            'timeZone': timeZone
        }

        const storedToken = localStorage.getItem('token');
        if (storedToken) {
            const response = await postDataAuth(API_POST_TIME_URL, body, storedToken)
        }
    }

    const handleCheckOut = async () => {
        const newEndTime = new Date().getTime();
        const body = {
            'endTime': newEndTime
        }

        const storedToken = localStorage.getItem('token');
        if (storedToken) {
            const response = await putDataAuth(API_PUT_TIME_URL, body, storedToken);
        }
    }

    return (
        <div>
            <button onClick={handleCheckIn}>Check in</button>
            <button onClick={handleCheckOut}>Check out</button>
            <TimeDiff/>
            <LatestTimeEntry/>
        </div>
    );
}

export default HomePage