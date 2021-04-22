import React, { useState } from 'react';

const Register = () => {
    const [formData, setFormData] = useState({
        username: '',
        password: '',
        password2: ''
    });
    
	const handleSubmit = (e: React.FormEvent) => {
		e.preventDefault();
		console.log('testing');
	};

	return (
		<>
			<form onSubmit={handleSubmit}>
				<h1>Register</h1>
				<label htmlFor='username'>Username</label>
				<input type='text' id='username' name='username'/>
				<label htmlFor='password'>Password</label>
				<input type='password' id='password' name='password'/>
				<label htmlFor='password2'>Retype password</label>
				<input type='password' id='password2' name='password2'/>
				<button type='submit'>Register</button>
			</form>
		</>
	);
};

export default Register;
