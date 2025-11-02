import { createRoot } from 'react-dom/client'
import { ThemeProvider } from '@mui/material/styles';
import './index.css'
import App from './App.tsx'
import React from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import customTheme from './theme'; // { changed code }

createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <ThemeProvider theme={customTheme}>
      <CssBaseline /> {/* { changed code } */}
      <App />
    </ThemeProvider>
  </React.StrictMode>
)
