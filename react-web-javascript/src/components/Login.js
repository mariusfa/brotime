import React from "react";
import Typography from "@material-ui/core/Typography";
import Container from "@material-ui/core/Container";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import Grid from "@material-ui/core/Grid";
import Link from "@material-ui/core/Link";
import styled from "styled-components";

const CustomContainer = styled.div`
  display: flex;
  margin-top: 1rem;
  flex-direction: column;
  align-items: center;
`;

const CustomForm = styled.form`
  margin-top: 1rem;
  width: 100%;
`;

const CustomSigninButton = styled(Button)`
  && {
    margin: 1rem 0;
  }
`;

export default function Login() {
  return (
    <Container component="main" maxWidth="xs">
      <CustomContainer>
        <Typography component="h1" variant="h5">
          Sign in
        </Typography>
        <CustomForm>
          <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            id="email"
            label="Email Address"
            name="email"
            autoComplete="email"
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
          <CustomSigninButton
            type="submit"
            fullWidth
            variant="contained"
            color="primary"
          >
            Sign In
          </CustomSigninButton>
          <Grid container>
            <Grid item>
              <Link href="#" variant="body2">
                {"Don't have an account? Sign Up"}
              </Link>
            </Grid>
          </Grid>
        </CustomForm>
      </CustomContainer>
    </Container>
  );
}
