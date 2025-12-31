import React, { useState } from 'react';
import { 
    Table, TableBody, TableCell, TableContainer, 
    TableHead, TableRow, Paper, Chip, CircularProgress, Typography, Box,
    TablePagination 
} from '@mui/material';
import type { LogEntry } from '../../../shared/interfaces/log';
import { CandyDetails } from './CandyDetails';

interface LogTableProps {
    logs: LogEntry[];
    isLoading: boolean;
}

const getActionColor = (action: string): "default" | "success" | "warning" | "error" => {
    switch (action) {
        case 'CREATE': return 'success';
        case 'UPDATE': return 'warning';
        case 'DELETE': return 'error';
        default: return 'default';
    }
};

export const LogTable = ({ logs, isLoading }: LogTableProps) => {
    // Estado para controlar a paginação
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(5); // Começa mostrando 5

    if (isLoading) {
        return <Box sx={{ display: 'flex', justifyContent: 'center', p: 4 }}><CircularProgress /></Box>;
    }
    
    if (!logs.length) {
        return <Typography align="center" sx={{ p: 4, color: 'text.secondary' }}>Nenhum registro encontrado.</Typography>;
    }

    // Lógica para cortar o array e mostrar só a página atual
    const visibleLogs = logs.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage);

    // Manipuladores de eventos
    const handleChangePage = (_event: unknown, newPage: number) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0); // Volta para a primeira página
    };

    return (
        <Paper elevation={2} sx={{ width: '100%', overflow: 'hidden' }}>
            <TableContainer>
                <Table size="small">
                    <TableHead sx={{ bgcolor: 'action.hover' }}>
                        <TableRow>
                            <TableCell><strong>Data/Hora</strong></TableCell>
                            <TableCell><strong>Tipo</strong></TableCell>
                            <TableCell><strong>Detalhes dos Doces</strong></TableCell>
                            <TableCell><strong>ID</strong></TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {visibleLogs.map(log => (
                            <TableRow key={log.id} hover>
                                <TableCell sx={{ whiteSpace: 'nowrap' }}>
                                    {new Date(log.timestamp).toLocaleString()}
                                </TableCell>
                                <TableCell>
                                    <Chip 
                                        label={log.actionType} 
                                        color={getActionColor(log.actionType)} 
                                        size="small" 
                                        variant="outlined" 
                                        sx={{ fontWeight: 'bold' }}
                                    />
                                </TableCell>
                                <TableCell>
                                    <CandyDetails items={log.candies} />
                                </TableCell>
                                <TableCell sx={{ color: 'text.secondary', fontSize: '0.70em' }}>
                                    {log.id}
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
            
            {/* O Rodapé da Paginação */}
            <TablePagination
                rowsPerPageOptions={[5, 10, 25]}
                component="div"
                count={logs.length}
                rowsPerPage={rowsPerPage}
                page={page}
                onPageChange={handleChangePage}
                onRowsPerPageChange={handleChangeRowsPerPage}
                labelRowsPerPage="Linhas por página:"
            />
        </Paper>
    );
};