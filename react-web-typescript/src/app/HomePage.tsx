import React, { useState, useEffect } from 'react';
import getDataAuth from '../api/GetDataAuth'
import postDataAuth from '../api/PostDataAuth'
import { API_DIFF_URL, API_POST_TIME_URL } from '../constants';
import { useHistory } from 'react-router';
import { async } from 'q';

const HomePage = () => {
    const [timeDiff, setTimeDiff] = useState<Number>(0)
    const history = useHistory()

    useEffect(() => {
        const getDiff = async () => {
            const storedToken = localStorage.getItem('token')
            if (storedToken) {
                const diffData = await getDataAuth(API_DIFF_URL, storedToken);
                const diffJson = await diffData.json()
                setTimeDiff(diffJson.timeDiff)
            } else {
                history.push('/login')
            }
        }

        getDiff()
    },[])

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
            const response = await postDataAuth(API_POST_TIME_URL,body, storedToken)
            console.log(response)
        }
    }

    const handleCheckOut = () => {
        console.log('Check out')
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