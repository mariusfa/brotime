import React, { useState, useEffect, useCallback } from 'react';
import getDataAuth from '../api/GetDataAuth';
import postDataAuth from '../api/PostDataAuth';
import putDataAuth from '../api/PutDataAuth';
import { API_DIFF_URL, API_POST_TIME_URL, API_PUT_TIME_URL, API_GET_LATEST_TIME_URL } from '../constants';
import { useHistory } from 'react-router';
import TimeInterface from '../interfaces/TimeInterface';
import { Entry } from '../features/Entry';

const HomePage = () => {
    const [timeDiff, setTimeDiff] = useState<Number>(0);
    const [latestTimeEntry, setLatestTimeEntry] = useState<any>({});
    const history = useHistory();

    const getDiff = useCallback(async () => {
        const storedToken = localStorage.getItem('token');
        if (storedToken) {
            const diffResponse = await getDataAuth(API_DIFF_URL, storedToken);
            if (diffResponse.ok) {
                const diffJson = await diffResponse.json();
                setTimeDiff(diffJson.timeDiff / 3600_000);
            } else if (diffResponse.status === 401) {
                history.push('/login');
            }
        } else {
            history.push('/login');
        }
    }, [history])

    const getLatest = useCallback(async () => {
        const storedToken = localStorage.getItem('token');
        if (storedToken) {
            const latestResponse = await getDataAuth(API_GET_LATEST_TIME_URL, storedToken);
            if (latestResponse.ok) {
                const latestJson = await latestResponse.json();
                console.log("test")
                console.log(latestJson);
                setLatestTimeEntry(latestJson)
            } else if (latestResponse.status === 401) {
                history.push('/login')
            }
        } else {
            history.push('/login')
        }
    }, [history])

    useEffect(() => {
        getDiff();
        getLatest();
    }, [getDiff, getLatest])

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
            getDiff()
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
            getDiff();
            getLatest();
        }
    }

    return (
        <div>
            <button onClick={handleCheckIn}>Check in</button>
            <button onClick={handleCheckOut}>Check out</button>
            <div>tilgode</div>
            <div>{timeDiff}</div>
            {latestTimeEntry &&
                <Entry entry={latestTimeEntry} label="latest" />
            }
        </div>
    );
}

export default HomePage