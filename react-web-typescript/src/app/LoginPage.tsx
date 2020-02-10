import React, { useState } from 'react';
import { PostData } from '../api/PostData';
import { useHistory } from 'react-router-dom';

const LoginPage = () => {
    const [username, setUsername] = useState("")
    const [password, setPassword] = useState("")
    let history = useHistory()

    const handleOnSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        const data = {
            "username": username,
            "password": password
        }

        const response = await PostData("http://localhost:8080/api/user/login", data)
        console.log(response);
        if (response.ok) {
            history.push("/");
        } else {
            alert("Login failed")
        }
    }

    return (
        <form onSubmit={handleOnSubmit}>
            <h1>Login</h1>
            <label htmlFor="username">Username</label>
            <input type="text" placeholder="Enter Username" id="username" value={username} onChange={e => setUsername(e.target.value)} required/>
            <label htmlFor="password">Password</label>
            <input type="password" placeholder="Enter Password" id="password" value={password} onChange={e => setPassword(e.target.value)} required/>
            <input type="submit" value="Login"/>
            <div>
                <p>Not registered? <a href="/register">Register</a></p>
                <p>Forgot password? Too bad!!</p>
            </div>
        </form>
    )
}

export default LoginPage;