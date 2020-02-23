import React from 'react';
import { TimeDiff } from '../features/TimeDiff'
import { LatestTimeEntry } from '../features/LatestTimeEntry';
import { CheckInAction, CheckOutAction } from '../features/Actions';

const HomePage = () => {
    return (
        <div>
            <CheckInAction />
            <CheckOutAction />
            <TimeDiff/>
            <LatestTimeEntry/>
        </div>
    );
}

export default HomePage