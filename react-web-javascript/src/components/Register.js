import React from "react";
import styled from "styled-components";
import Container from "@material-ui/core/Container";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";
import Grid from "@material-ui/core/Grid";
import Link from "@material-ui/core/Link";
import TextField from "@material-ui/core/TextField";

const CustomContainer = styled.div`
  display: flex;
  margin-top: 1rem;
  flex-direction: column;
  align-items: center;
`;

const CustomSignUpButton = styled(Button)`
  && {
    margin: 1rem 0;
  }
`;

const CustomForm = styled.form`
  margin-top: 1rem;
  width: 100%;
`;

export default function Register() {
  return (
    <Container component="main" maxWidth="xs">
      <CustomContainer>
        <Typography component="h1" variant="h5">
          Sign up
        </Typography>
        <CustomForm>
          <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            id="username"
            label="Username"
            name="username"
            autoComplete="username"
            autoFocus
          />
          <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            name="password"
            label="Password"
            type="password"
            id="password"
            autoComplete="current-password"
          />
          <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            name="password2"
            label="Password"
            type="password"
            id="password2"
            autoComplete="current-password"
          />
          <CustomSignUpButton
            type="submit"
            fullWidth
            variant="contained"
            color="primary"
          >
            Sign Up
          </CustomSignUpButton>
          <Grid container justify="flex-end">
            <Grid item>
              <Link href="/login" variant="body2">
                Already have an account? Sign in
              </Link>
            </Grid>
          </Grid>
        </CustomForm>
      </CustomContainer>
    </Container>
  );
}
