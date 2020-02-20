import React, { useState, useEffect, useCallback } from 'react';
import { API_OVERVIEW_URL } from '../constants';
import { ListEntries } from '../features/ListEntries';
import getDataAuth from '../api/GetDataAuth';


const OverviewPage = () => {
    const [timeEntries, setTimeEntries] = useState<any>([]);

    const getData = useCallback(async () => {
        const storedToken = localStorage.getItem('token');
        if (storedToken) {
            const response = await getDataAuth(API_OVERVIEW_URL, storedToken);
            if (response.ok) {
                const responseJson = await response.json();
                setTimeEntries(responseJson);
            }
        }
    }, [])

    // const dataTransformer = (data: any) => {
    //     return data.map(({ startTime, endTime, ...rest }: any) => {
    //         return {
    //             'startTime': formatDate(startTime),
    //             'endTime': formatDate(endTime),
    //             ...rest
    //         }
    //     })
    // }

    useEffect(() => {
        getData();
    }, [getData])

    return (
        <ListEntries entries={timeEntries} />
    );
}

export default OverviewPage;