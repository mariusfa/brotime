import React, { useCallback, useState, useEffect } from 'react';
import { API_GET_LATEST_TIME_URL } from '../../constants';
import getDataAuth from '../../api/GetDataAuth';
import { Entry } from '../Entry';

const LatestTimeEntry = () => {
    const [latestTimeEntry, setLatestTimeEntry] = useState<any>({});

    const getLatestEntry = useCallback(async () => {
        const storedToken = localStorage.getItem('token');
        if (storedToken) {
            const latestResponse = await getDataAuth(API_GET_LATEST_TIME_URL, storedToken);
            if (latestResponse.ok) {
                const latestJson = await latestResponse.json();
                setLatestTimeEntry(latestJson);
            }
        }
    }, [])

    useEffect(() => {
        getLatestEntry();
    }, [getLatestEntry])

    return(
        <div>
            {latestTimeEntry &&
                <Entry entry={latestTimeEntry} label="LATEST" />
            }
        </div>
    )
}

export default LatestTimeEntry;