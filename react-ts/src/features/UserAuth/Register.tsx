import React, { useState } from 'react';

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

    const validateForm = () => {
        let newErrors: FormError[] = [];

        if (!formData.username) {
            newErrors.push({ id: 0, message: 'Plz fill username' });
        }

        if (!formData.password) {
            newErrors.push({ id: 1, message: 'Plz fill password' });
        }

        if (formData.password && formData.password.length < 6) {
            newErrors.push({
                id: 4,
                message: 'Password length too short, min length is 6',
            });
        }

        if (!formData.password2) {
            newErrors.push({ id: 2, message: 'Plz retype pasword' });
        }

        if (
            formData.password &&
            formData.password2 &&
            formData.password !== formData.password2
        ) {
            newErrors.push({ id: 3, message: 'Passwords mismatch' });
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
                const result = await fetch(
                    'http://localhost:8080/api/user/register',
                    {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify({
                            username,
                            password,
                        }),
                    }
                );
                if (result.ok) {
                    console.log('redirect');
                } else if (!result.ok && result.status === 409) {
                    console.log('conflict');
                }
            } catch (error) {
                console.log('general error');
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
