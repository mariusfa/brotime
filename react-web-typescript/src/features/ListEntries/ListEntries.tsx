import React from 'react';
import styled from '@emotion/styled';

const StyledDate = styled.span`
  padding: 0.1rem;
  margin: 0.1rem;
`;

const ListEntries = ({ entries }: any) => {

  const entriesView = entries.map((item: any) =>
    <div key={item.id}>
      <StyledDate>{item.startTime}</StyledDate>
      <StyledDate>{item.endTime}</StyledDate>
    </div>
  );

  return (
    <div>
      {entriesView}
    </div>
  );
}

export default ListEntries;