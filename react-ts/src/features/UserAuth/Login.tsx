import React, { useState } from 'react';
import { useHistory } from 'react-router-dom';
import postData from '../../api/postData';
import errorMessages from './errorMessages';
import { FormError } from './types';

const Login = () => {
    const [formData, setFormData] = useState({
        username: '',
        password: '',
    });
    const [isSubmitting, setIsSubmitting] = useState(false);
    const [errors, setErrors] = useState<FormError[]>([]);
    const history = useHistory();

    const validateFormData = () => {
        let newErrors = [];

        if (!formData.username) {
            newErrors.push(errorMessages.MISSING_USERNAME);
        }

        if (!formData.password) {
            newErrors.push(errorMessages.MISSING_PASSWORD);
        }

        if (formData.password && formData.password.length < 6) {
            newErrors.push(errorMessages.PASSWORD_LENGTH);
        }

        setErrors(newErrors);
        return newErrors.length === 0;
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        if (validateFormData()) {
            setIsSubmitting(true);
            try {
                const { username, password } = formData;
                const result = await postData('api/user/login', {
                    username,
                    password,
                }, false);
                if (result.ok) {
                    const token = await result.text();
                    localStorage.setItem('token', token);
                    localStorage.setItem('username', username);
                    history.push('/dashboard');
                    return;
                } else {
                    setErrors([errorMessages.GENERAL_ERROR]);
                }
            } catch (error) {
                setErrors([errorMessages.GENERAL_ERROR]);
            }
            setIsSubmitting(false);
        }
    };

    const handleOnChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    return (
        <>
            <form onSubmit={handleSubmit}>
                <h2>Login</h2>
                {errors &&
                    errors.map(({ id, message }) => <p key={id}>{message}</p>)}
                <label htmlFor='username'>Username</label>
                <input
                    type='text'
                    id='username'
                    name='username'
                    onChange={handleOnChange}
                    value={formData.username}
                />
                <label htmlFor='password'>Username</label>
                <input
                    type='password'
                    id='password'
                    name='password'
                    onChange={handleOnChange}
                    value={formData.password}
                />
                {isSubmitting ? (
                    <p>Logging in...</p>
                ) : (
                    <button type='submit'>Login</button>
                )}
            </form>
        </>
    );
};

export default Login;
