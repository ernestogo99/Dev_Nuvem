import { Box, Button } from '@mui/material';
import * as React from 'react';

const NavigationMenu: React.FC = () => {
    return (
        <Box
          mt={3}
          sx={{
            display: { xs: 'none', sm: 'flex' },
            gap: 4,
            alignItems: 'center',
            '& .MuiButton-root': {
              '&:hover': {
                backgroundColor: 'transparent',
              },
            },
          }}
        >
            <Button color="inherit" href="#cakes" sx={{ fontWeight: 400 }}>
                CAKES
            </Button>
            <Button color="inherit" href="#muffins" sx={{ fontWeight: 400 }}>
                MUFFINS
            </Button>
            <Button color="inherit" href="#brownies" sx={{ fontWeight: 400 }}>
                BROWNIES
            </Button>
            <Button color="inherit" href="#docinhos" sx={{ fontWeight: 400 }}>
                DOCINHOS
            </Button>
        </Box>
    )
};

export default NavigationMenu;