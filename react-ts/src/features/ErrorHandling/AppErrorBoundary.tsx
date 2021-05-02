import React from 'react';
import { ErrorBoundary } from "react-error-boundary";
import ErrorFallback from './ErrorFallback';

const AppErrorBoundary:React.FC = ({children}) => {
    return (
        <ErrorBoundary FallbackComponent={ErrorFallback}>
            {children}
        </ErrorBoundary>
    );
}

export default AppErrorBoundary
