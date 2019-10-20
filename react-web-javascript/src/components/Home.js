import React, { useEffect, useState } from "react";
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import styled from "styled-components";
import TimeTable from "./TimeTable";

const HomeContainer = styled.div`
  text-align: center;
  background-color: #f5f5f5;
  height: 100vh;
  overflow: auto;
  padding: 1rem;
`;

const createData = (start, end) => {
  const startDate = new Date(start);
  const endDate = new Date(end);
  return {
    date: `${startDate.getDate()}.${startDate.getMonth()}`,
    start: `${startDate.getHours()}:${startDate.getMinutes()}`,
    end: `${endDate.getHours()}:${endDate.getMinutes()}`
  };
};

const rows = [
  createData(21, 1, 22222),
  createData(20, 12, 2),
  createData(19, 12, 2),
  createData(18, 133, 2),
  createData(17, 155, 2111),
  createData(16, 11111, 2)
];

export default function Home() {
  const [timeList, setTimeList] = useState([]);

  useEffect(async () => {
    try {
      const response = await fetch("http://10.0.0.46:8080/api/time", {
        headers: { "Content-Type": "application/json" }
      });
      if (!response.ok) {
        throw Error(response.statusText);
      }
      const responseJson = await response.json();
      const newTimeList = responseJson.map(item =>
        createData(item.start, item.end)
      );
      console.log(newTimeList);
      setTimeList(newTimeList);
    } catch (error) {
      console.log(error);
    }
  }, []);

  return (
    <HomeContainer>
      <Card>
        <CardContent>
          <TimeTable rows={timeList} />
        </CardContent>
      </Card>
    </HomeContainer>
  );
}
