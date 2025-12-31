import { api } from "../../../shared/services/axiosconfig/config.ts";
import type { LogEntry } from "../../../shared/interfaces/log.ts";


const LOGS_ENDPOINT = "/logs";

const getAllLogs = async (): Promise<LogEntry[] | Error> => {
    try {
        const { data } = await api.get<LogEntry[]>(LOGS_ENDPOINT);
        return data;
    } catch (error: any) {
        const message = error.response?.data?.message || "Erro ao carregar os logs. Verifique sua conexão e permissões.";
        return new Error(message);
    }
};

export const logService = {
    getAllLogs,
};