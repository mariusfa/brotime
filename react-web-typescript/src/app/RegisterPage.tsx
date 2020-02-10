import React, { useState } from 'react'
import { PostData } from '../api/PostData';

const RegisterPage = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [repeatPassword, setRepeatPassword] = useState("");

    const handleOnSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        console.log(username)
        console.log(password)
        console.log(repeatPassword)
        const data = {
            "username": username,
            "password": password
        }
        const result = await PostData("http://localhost:8080/api/user/regiser", data)
        console.log("result: ")
        console.log(result)
    }

    return (
        <form onSubmit={handleOnSubmit}>
            <h1>Register</h1>
            <hr/>
            <label>Username</label>
            <input type="text" placeholder="Username" value={username} onChange={e => setUsername(e.target.value)} required/>
            <label>Password</label>
            <input type="password" placeholder="Password" value={password} onChange={e => setPassword(e.target.value)} required/>
            <label>Repeat Password</label>
            <input type="password" placeholder="Repeat Password" value={repeatPassword} onChange={e => setRepeatPassword(e.target.value)} required/>
            <input type="submit" value="Register"/>
            <div>
                <p>Already have an account? <a href="/login">Sign in</a>.</p>
            </div>
        </form>
    );
}

export default RegisterPage;