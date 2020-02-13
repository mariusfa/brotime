import React, { useState, useEffect, useCallback } from 'react';
import getDataAuth from '../api/GetDataAuth'
import postDataAuth from '../api/PostDataAuth'
import putDataAuth from '../api/PutDataAuth'
import { API_DIFF_URL, API_POST_TIME_URL, API_PUT_TIME_URL } from '../constants';
import { useHistory } from 'react-router';

const HomePage = () => {
    const [timeDiff, setTimeDiff] = useState<Number>(0)
    const history = useHistory()

    const getDiff = useCallback(async () => {
        const storedToken = localStorage.getItem('token')
        if (storedToken) {
            const diffData = await getDataAuth(API_DIFF_URL, storedToken);
            const diffJson = await diffData.json()
            setTimeDiff(diffJson.timeDiff/3600_000)
        } else {
            history.push('/login')
        }
    }, [history])

    useEffect(() => {
        getDiff()
    },[getDiff])

    const handleCheckIn = async () => {
        console.log('Check in')
        const timeStamp = new Date().getTime()
        const timeZone = "Europe/Berlin"
        const body = {
            'timeStamp': timeStamp,
            'timeZone': timeZone
        }

        const storedToken = localStorage.getItem('token')
        if (storedToken) {
            const response = await postDataAuth(API_POST_TIME_URL, body, storedToken)
            getDiff()
            console.log(response)
        }
    }

    const handleCheckOut = async () => {
        console.log('Check out')
        const newEndTime = new Date().getTime()
        const body = {
            'endTime': newEndTime
        }

        const storedToken = localStorage.getItem('token')
        if (storedToken) {
            const response = await putDataAuth(API_PUT_TIME_URL, body, storedToken)
            console.log(response)
            getDiff()
        }
    }

    return (
        <div>
            <button onClick={handleCheckIn}>Check in</button>
            <button onClick={handleCheckOut}>Check out</button>
            <div>{timeDiff}</div>
            <div>tilgode</div>
        </div>
    );
}

export default HomePage