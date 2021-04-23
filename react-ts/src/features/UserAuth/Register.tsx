import React, { useState } from 'react';
import { useHistory } from 'react-router';
import { API_URL } from '../../api/apiConfig';
import postData from '../../api/postData';
import errorMessages from './errorMessages';

interface FormError {
    id: number;
    message: string;
}

const Register = () => {
    const [formData, setFormData] = useState({
        username: '',
        password: '',
        password2: '',
    });
    const [errors, setErrors] = useState<FormError[]>([]);
    const [isSubmitting, setIsSubmitting] = useState(false);
    const history = useHistory();

    console.log(API_URL);

    const validateForm = () => {
        let newErrors: FormError[] = [];

        if (!formData.username) {
            newErrors.push(errorMessages.MISSING_USERNAME);
        }

        if (!formData.password) {
            newErrors.push(errorMessages.MISSING_PASSWORD);
        }

        if (formData.password && formData.password.length < 6) {
            newErrors.push(errorMessages.PASSWORD_LENGTH);
        }

        if (!formData.password2) {
            newErrors.push(errorMessages.RETYPE_PASSWORD);
        }

        if (
            formData.password &&
            formData.password2 &&
            formData.password !== formData.password2
        ) {
            newErrors.push(errorMessages.PASSWORD_MISMATCH);
        }

        setErrors(newErrors);
        return newErrors.length === 0;
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        const { username, password } = formData;
        if (validateForm()) {
            setIsSubmitting(true);
            try {
                const result = await postData('api/user/register', {
                    username,
                    password,
                });
                if (result.ok) {
                    history.push('/login');
                    return;
                } else if (!result.ok && result.status === 409) {
                    setErrors([errorMessages.USER_CONFLICT]);
                }
            } catch (error) {
                setErrors([errorMessages.GENERAL_ERROR]);
            }
            setIsSubmitting(false);
        }
    };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setFormData({ ...formData, [e.target.name]: e.target.value.trim() });
    };

    return (
        <>
            <form onSubmit={handleSubmit}>
                <h1>Register</h1>
                {errors &&
                    errors.map(({ id, message }) => <p key={id}>{message}</p>)}
                <label htmlFor='username'>Username</label>
                <input
                    type='text'
                    id='username'
                    name='username'
                    onChange={handleChange}
                    value={formData.username}
                />
                <label htmlFor='password'>Password</label>
                <input
                    type='password'
                    id='password'
                    name='password'
                    onChange={handleChange}
                    value={formData.password}
                />
                <label htmlFor='password2'>Retype password</label>
                <input
                    type='password'
                    id='password2'
                    name='password2'
                    onChange={handleChange}
                    value={formData.password2}
                />
                {isSubmitting ? (
                    <p>Registering user...</p>
                ) : (
                    <button type='submit'>Register</button>
                )}
            </form>
        </>
    );
};

export default Register;
