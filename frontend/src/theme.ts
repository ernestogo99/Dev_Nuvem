import { createTheme } from '@mui/material/styles';

const customTheme = createTheme({
  palette: {
    primary: { main: '#FFDC92' },
    secondary: { main: '#2F2F2F' },
    background: { default: '#F8F8F8' },
    text: { primary: '#2F2F2F' },
  },
  typography: {
    fontFamily: ['Figtree', 'sans-serif'].join(','),
    h1: { fontWeight: 600 },
    body1: { fontWeight: 400 },
  },
  components: {
    MuiCssBaseline: {
      styleOverrides: {
        body: {
          backgroundColor: '#F8F8F8',
          color: '#2F2F2F',
        },
      },
    },
  },
});

export default customTheme;