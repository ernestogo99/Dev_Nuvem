import { useNavigate } from 'react-router-dom';
import { Container, Typography, Button, Alert, Box, Stack } from '@mui/material';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import HistoryIcon from '@mui/icons-material/History';

import { useLogViewer } from './hooks/uselogViewer';
import { FilterBar } from './components/Filterbar';
import { LogTable } from './components/Logtable';

export const LogViewer = () => {
    const navigate = useNavigate();
    const { displayLogs, isLoading, error, filters, actions } = useLogViewer();

    return (
        <Container maxWidth="xl" sx={{ py: 4 }}>
            
            {/* Cabeçalho */}
            <Stack direction="row" justifyContent="space-between" alignItems="center" mb={3}>
                <Typography variant="h4" component="h1" sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
                    <HistoryIcon fontSize="large" color="primary" /> Histórico de Logs
                </Typography>
                
                <Button 
                    variant="outlined" 
                    startIcon={<ArrowBackIcon />} 
                    onClick={() => navigate('/')}
                >
                    Voltar para Home
                </Button>
            </Stack>

            {/* Filtros */}
            <Box sx={{ mb: 3 }}>
                <FilterBar filters={filters} actions={actions} />
            </Box>

            {/* Erro */}
            {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}

            {/* Tabela */}
            <LogTable logs={displayLogs} isLoading={isLoading} />
            
        </Container>
    );
};

export default LogViewer;