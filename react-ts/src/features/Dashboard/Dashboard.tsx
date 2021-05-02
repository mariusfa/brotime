import { useEffect, useState } from 'react';
import getData from '../../api/getData';
import postData from '../../api/postData';
import putData from '../../api/putData';

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

    const fetchTimeDiff = async () => {
        const response = await getData('api/time/diff');
        if (response.ok) {
            const content = await response.text();
            if (content.length > 0) {
                const timeDiffData = JSON.parse(content);
                setTimeDiff(timeDiffData!!.timeDiff);
            }
        } else {
            console.log('fetch error');
        }
    }

    const updateLatestTime = async () => {
        await putData('api/time', { ...latestTime, endTime: Date.now() });
    }

    const postNewTime = async () => {
        await postData(
            'api/time',
            { timeStamp: Date.now(), timeZone: 'Europe' },
            true
        );
    }

    useEffect(() => {
        fetchLatestTime();
        fetchTimeDiff();
    }, []);

    const handleTimestampClick = async () => {
        if (latestTime && isToday(latestTime.endTime)) {
            await updateLatestTime()
        } else {
            await postNewTime();
        }
        fetchLatestTime();
        fetchTimeDiff();
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
