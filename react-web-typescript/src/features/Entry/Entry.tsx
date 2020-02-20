import React from 'react';
import StyledField from '../../styles/StyledField';
import StyledCard from '../../styles/StyledCard';

const Entry = (props: any) => {
    const { startTime, endTime } = props.entry;
    const label = props.label;
    return (
        <StyledCard>
            {label &&
                <div>{label}</div>
            }
            <StyledField>{startTime}</StyledField>
            <StyledField>{endTime}</StyledField>
        </StyledCard>
    );
}

export default Entry;