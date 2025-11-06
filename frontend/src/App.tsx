// frontend/src/App.tsx

import React from 'react';

import Login from './pages/Login/Login'; 


const App: React.FC = () => {
    return (
        // Renders the Login component
        <div className="App">
            <Login />
        </div>
    );
};

export default App;