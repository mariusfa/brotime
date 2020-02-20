import React from 'react';
import StyledField from '../../styles/StyledField';
import StyledCard from '../../styles/StyledCard';
import styled from '@emotion/styled';
import { formatDate } from '../../util/DateUtils';

const EntryContainer = styled.div`
    margin: 0.5rem;
    text-align: center;
`;

const Label = styled.div`
    color: #737373;
    margin: 0.2rem;
`;

const Entry = (props: any) => {
    const { startTime, endTime } = props.entry;
    const label = props.label;
    return (
        <EntryContainer>
            <StyledCard>
                {label &&
                    <Label>{label}</Label>
                }
                <StyledField>{formatDate(startTime)}</StyledField>
                <StyledField>{formatDate(endTime)}</StyledField>
            </StyledCard>

        </EntryContainer>
    );
}

export default Entry;