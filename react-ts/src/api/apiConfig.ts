const API_URL =
    process.env.NODE_ENV === 'production'
        ? (window as any).API_URL
        : 'http://localhost:8080/';no

export { API_URL };
