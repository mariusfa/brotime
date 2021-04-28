import { useEffect, useState } from 'react';
import getData from '../../api/getData';

interface TimeEntry {
    startTime: number;
    endTime: number;
    timeZone: string;
}

interface TimeDiff {
    timeDiff: number;
}

const Dashboard = () => {
    const [latestTime, setLatestTime] = useState<TimeEntry | undefined>();
    const [timeDiff, setTimeDiff] = useState<TimeDiff | undefined>();

    const fetchLatestTime = async () => {
        const response = await getData('api/time');
        if (response.ok) {
            const contentLength = (await response.text()).length
            if (contentLength > 0) {
                const latestTimeData = await response.json()
                setLatestTime(latestTimeData)
            }
        } else {
            console.log("fetch error");
        }
    }

    useEffect(() => {
        fetchLatestTime();
    }, []);

    return (
        <>
            <button>Timestamp</button>
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
