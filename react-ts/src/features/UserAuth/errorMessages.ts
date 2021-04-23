const errorMessages = {
    MISSING_USERNAME: { id: 0, message: 'Plz fill username' },
    MISSING_PASSWORD: { id: 1, message: 'Plz fill password' },
    RETYPE_PASSWORD: { id: 2, message: 'Plz retype pasword' },
    PASSWORD_MISMATCH: { id: 3, message: 'Passwords mismatch' },
    PASSWORD_LENGTH: {
        id: 4,
        message: 'Password length too short, min length is 6',
    },
    USER_CONFLICT: { id: 5, message: 'User already exists' },
    GENERAL_ERROR: { id: 6, message: 'Something went wrong' },
};

export default errorMessages;
