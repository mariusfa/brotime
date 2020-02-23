import React, { useCallback, useState, useEffect } from 'react';
import styled from '@emotion/styled';
import StyledCard from '../../styles/StyledCard';
import StyledLabel from '../../styles/StyledLabel';
import StyledField from '../../styles/StyledField';
import getDataAuth from '../../api/GetDataAuth';
import { API_DIFF_URL } from '../../constants';

const HOUR_MILLIS = 3600_000;
const NUMBER_OF_DECIMALS = 2;

const TilGodeContainer = styled.div`
    margin: 0.5rem;
    text-align: center;
`;

const TimeDiff = () => {
    const [timeDiff, setTimeDiff] = useState<number>(0);

    const getDiff = useCallback(async () => {
        const storedToken = localStorage.getItem('token');
        if (storedToken) {
            const response = await getDataAuth(API_DIFF_URL, storedToken);
            if (response.ok) {
                const timeDiffResponse = await response.json();
                setTimeDiff(timeDiffResponse.timeDiff / HOUR_MILLIS);
            }
        }
    }, [])

    useEffect(() => {
        getDiff();
    }, [getDiff]);

    return (
        <TilGodeContainer>
            <StyledCard>
                <StyledLabel>FLEX</StyledLabel>
                <StyledField>{timeDiff.toFixed(NUMBER_OF_DECIMALS)}</StyledField>
            </StyledCard>
        </TilGodeContainer>
    );
}

export default TimeDiff;