import React, { useState, useEffect } from 'react';
import { fetchData } from '../api/FetchData';
import { API_URL } from '../constants';
import { ListEntries } from '../features/ListEntries';
import { formatDate } from '../util/DateUtils';


const OverviewPage = () => {
  const [timeEntries, setTimeEntries] = useState<any>([]);

  useEffect(() => {
    const getData = async () => {
      const data = await fetchData(API_URL);
      const transformedData = dataTransformer(data);
      setTimeEntries(transformedData);
    }

    const dataTransformer = (data: any) => {
      return data.map(({ startTime, endTime, ...rest }: any) => {
        return {
          'startTime': formatDate(startTime),
          'endTime': formatDate(endTime),
          ...rest
        }
      })
    }

    getData();
  }, [])

  return (
      <ListEntries entries={timeEntries} />
  );
}

export default OverviewPage;