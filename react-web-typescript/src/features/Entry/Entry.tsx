import React from 'react';
import StyledField from '../../styles/StyledField';
import StyledCard from '../../styles/StyledCard';
import StyledLabel from '../../styles/StyledLabel';
import styled from '@emotion/styled';
import { formatDate } from '../../util/DateUtils';

const EntryContainer = styled.div`
    margin: 0.5rem;
    text-align: center;
`;

const Entry = (props: any) => {
    const { startTime, endTime } = props.entry;
    const label = props.label;
    return (
        <EntryContainer>
            <StyledCard>
                {label &&
                    <StyledLabel>{label}</StyledLabel>
                }
                <StyledField>{formatDate(startTime)}</StyledField>
                <StyledField>{formatDate(endTime)}</StyledField>
            </StyledCard>

        </EntryContainer>
    );
}

export default Entry;