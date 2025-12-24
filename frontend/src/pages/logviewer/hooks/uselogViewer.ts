import { useState, useEffect, type FormEvent } from 'react';
import { logService } from '../services/logservice'; 
import type { LogEntry } from '../../../shared/interfaces/log';

export const useLogViewer = () => {
    // Estados de Dados
    const [allLogs, setAllLogs] = useState<LogEntry[]>([]);
    const [displayLogs, setDisplayLogs] = useState<LogEntry[]>([]);
    const [isLoading, setIsLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    // Estados de Filtro
    const [actionFilter, setActionFilter] = useState<string>('');
    const [useDateFilter, setUseDateFilter] = useState<boolean>(false);
    const [startTimeFilter, setStartTimeFilter] = useState<string>('');
    const [endTimeFilter, setEndTimeFilter] = useState<string>('');

    // Busca Dados
    const fetchAllLogs = async () => {
        setIsLoading(true);
        setError(null);
        const response = await logService.getAllLogs();

        if (response instanceof Error) {
            console.error("Erro ao buscar logs:", response.message);
            setError(response.message);
        } else {
            setAllLogs(response);
            setDisplayLogs(response);
        }
        setIsLoading(false);
    };

    useEffect(() => {
        fetchAllLogs();
    }, []);

    // Lógica de Filtragem
    const applyFilters = (e?: FormEvent) => {
        if (e) e.preventDefault();
        let result = [...allLogs];

        if (actionFilter) {
            result = result.filter(log => log.actionType === actionFilter);
        }

        if (useDateFilter && startTimeFilter && endTimeFilter) {
            const start = new Date(startTimeFilter).getTime();
            const end = new Date(endTimeFilter).getTime();
            result = result.filter(log => {
                const logTime = new Date(log.timestamp).getTime();
                return logTime >= start && logTime <= end;
            });
        }
        setDisplayLogs(result);
    };

    const handleReset = () => {
        setActionFilter('');
        setUseDateFilter(false);
        setStartTimeFilter('');
        setEndTimeFilter('');
        setDisplayLogs(allLogs);
    };

    // Retorna tudo que a tela precisa
    return {
        displayLogs,
        isLoading,
        error,
        filters: {
            actionFilter, setActionFilter,
            useDateFilter, setUseDateFilter,
            startTimeFilter, setStartTimeFilter,
            endTimeFilter, setEndTimeFilter
        },
        actions: {
            applyFilters,
            handleReset
        }
    };
};