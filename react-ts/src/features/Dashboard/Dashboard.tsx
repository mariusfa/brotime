import { useEffect, useState } from 'react';
import getData from '../../api/getData';
import postData from '../../api/postData';

interface TimeEntry {
    startTime: number;
    endTime: number;
    timeZone: string;
}

interface TimeDiff {
    timeDiff: number;
}

const isToday = (timestamp: number): boolean => {
    const timestampDate = new Date(timestamp);
    const today = new Date(timestamp);
    return (
        timestampDate.getDate() === today.getDate() &&
        timestampDate.getMonth() === today.getMonth() &&
        timestampDate.getFullYear() === today.getFullYear()
    );
};

const Dashboard = () => {
    const [latestTime, setLatestTime] = useState<TimeEntry | undefined>();
    const [timeDiff, setTimeDiff] = useState<TimeDiff | undefined>();

    const fetchLatestTime = async () => {
        const response = await getData('api/time');
        if (response.ok) {
            const content = await response.text();
            if (content.length > 0) {
                const latestTimeData = JSON.parse(content);
                setLatestTime(latestTimeData);
            }
        } else {
            console.log('fetch error');
        }
    };

    useEffect(() => {
        fetchLatestTime();
    }, []);

    const handleTimestampClick = async () => {
        if (latestTime && isToday(latestTime.endTime)) {
            console.log('Update time');
        } else {
            const response = await postData(
                'api/time',
                { timeStamp: Date.now(), timeZone: 'Europe' },
                true
            );

            if (response.ok) {
                fetchLatestTime()
            } else {
                console.log('Post error');
            }
        }
    };

    return (
        <>
            <button onClick={handleTimestampClick}>Timestamp</button>
            {latestTime ? (
                <div>
                    <p>{latestTime.startTime}</p>
                    <p>{latestTime.endTime}</p>
                    <p>{latestTime.timeZone}</p>
                </div>
            ) : (
                <p>No time entry to show yet</p>
            )}

            {latestTime && timeDiff && (
                <div>
                    <p>Current time status:</p>
                    <p>{timeDiff}</p>
                </div>
            )}
        </>
    );
};

export default Dashboard;
