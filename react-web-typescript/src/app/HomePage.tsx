import React from 'react';
import putDataAuth from '../api/PutDataAuth';
import { API_PUT_TIME_URL } from '../constants';
import { TimeDiff } from '../features/TimeDiff'
import { LatestTimeEntry } from '../features/LatestTimeEntry';
import { CheckInAction } from '../features/Actions';

const HomePage = () => {

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
            <CheckInAction />
            <button onClick={handleCheckOut}>Check out</button>
            <TimeDiff/>
            <LatestTimeEntry/>
        </div>
    );
}

export default HomePage