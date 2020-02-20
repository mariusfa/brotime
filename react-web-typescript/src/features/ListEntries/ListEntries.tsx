import React from 'react';
import styled from '@emotion/styled';
import { Entry } from '../Entry';

const ListEntries = ({ entries }: any) => {

    const entriesView = entries.map((item: any) =>
        <Entry key={item.id} entry={item} />
    );

    return (
        <div>
            {entriesView}
        </div>
    );
}

export default ListEntries;