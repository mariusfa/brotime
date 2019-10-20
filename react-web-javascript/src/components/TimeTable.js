import React from "react";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";

export default function TimeTable({ rows }) {
  return (
    <Table>
      <TableHead>
        <TableRow>
          <TableCell>Dato</TableCell>
          <TableCell>Start</TableCell>
          <TableCell>Slutt</TableCell>
        </TableRow>
      </TableHead>
      <TableBody>
        {rows.map(row => (
          <TableRow key={row.date}>
            <TableCell>{row.date}</TableCell>
            <TableCell>{row.start}</TableCell>
            <TableCell>{row.end}</TableCell>
          </TableRow>
        ))}
      </TableBody>
    </Table>
  );
}
