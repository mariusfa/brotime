import React, { useState } from "react";
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
  const [passwordErrorMessage, setPasswordErrorMessage] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [repeatPassword, setRepeatPassword] = useState("");

  const handleUsernameChange = event => {
    setUsername(event.target.value);
  };

  const handlePasswordChange = event => {
    const passwordNewValue = event.target.value;
    setPassword(event.target.value);
    comparePasswords(passwordNewValue, repeatPassword);
  };

  const handleRepeatPasswordChange = event => {
    const repeatPasswordNewValue = event.target.value;
    setRepeatPassword(repeatPasswordNewValue);
    comparePasswords(password, repeatPasswordNewValue);
  };

  const comparePasswords = (password, repeatPassword) => {
    if (
      password.length > 0 &&
      repeatPassword.length > 0 &&
      password !== repeatPassword
    ) {
      setPasswordErrorMessage("Password mismatch");
    } else {
      setPasswordErrorMessage("");
    }
  };

  const handleSubmit = async event => {
    event.preventDefault();
    console.log("hello");
    if (
      password.length > 0 &&
      repeatPassword.length > 0 &&
      password === repeatPassword
    ) {
      try {
        const response = await fetch(
          "http://localhost:8080/api/user/register",
          {
            method: "post",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
              username: username,
              password: password
            })
          }
        );
        if (!response.ok) {
          throw Error(response.statusText);
        }
        const responseJson = await response.json();
        console.log(responseJson);
      } catch (error) {
        console.log(error);
      }
    }
  };

  return (
    <Container component="main" maxWidth="xs">
      <CustomContainer>
        <Typography component="h1" variant="h5">
          Sign up
        </Typography>
        <CustomForm onSubmit={handleSubmit}>
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
            onChange={handleUsernameChange}
            value={username}
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
            onChange={handlePasswordChange}
            value={password}
          />
          <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            name="repeatPassword"
            label="Password"
            type="password"
            id="repeatPassword"
            autoComplete="current-password"
            error={passwordErrorMessage.length > 0}
            helperText={passwordErrorMessage}
            onChange={handleRepeatPasswordChange}
            value={repeatPassword}
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
