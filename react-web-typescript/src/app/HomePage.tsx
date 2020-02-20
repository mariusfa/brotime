import React, { useState, useEffect, useCallback } from 'react';
import getDataAuth from '../api/GetDataAuth';
import postDataAuth from '../api/PostDataAuth';
import putDataAuth from '../api/PutDataAuth';
import { API_DIFF_URL, API_POST_TIME_URL, API_PUT_TIME_URL, API_GET_LATEST_TIME_URL } from '../constants';
import { useHistory } from 'react-router';
import { Entry } from '../features/Entry';
import { TimeDiff } from '../features/TimeDiff'

const HomePage = () => {
    const [latestTimeEntry, setLatestTimeEntry] = useState<any>({});
    const history = useHistory();


    const getLatest = useCallback(async () => {
        const storedToken = localStorage.getItem('token');
        if (storedToken) {
            const latestResponse = await getDataAuth(API_GET_LATEST_TIME_URL, storedToken);
            if (latestResponse.ok) {
                const latestJson = await latestResponse.json();
                setLatestTimeEntry(latestJson)
            } else if (latestResponse.status === 401) {
                history.push('/login')
            }
        } else {
            history.push('/login')
        }
    }, [history])

    useEffect(() => {
        getLatest();
    }, [getLatest])

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
            getLatest()
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
            getLatest();
        }
    }

    return (
        <div>
            <button onClick={handleCheckIn}>Check in</button>
            <button onClick={handleCheckOut}>Check out</button>
            <TimeDiff/>
            {latestTimeEntry &&
                <Entry entry={latestTimeEntry} label="LATEST" />
            }
        </div>
    );
}

export default HomePage