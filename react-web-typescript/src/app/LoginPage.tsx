import React from 'react';

const LoginPage = () => {
    return (
        <div>
            <h1>Login</h1>
            <label htmlFor="username">Username</label>
            <input type="text" placeholder="Enter Username" id="username" />
            <label htmlFor="password">Password</label>
            <input type="password" placeholder="Enter Password" id="password"/>
            <button>Login</button>
            <div>
                <p>Not registered? <a href="/register">Register</a></p>
                <p>Forgot password? Too bad!!</p>
            </div>
        </div>
    )
}

export default LoginPage;