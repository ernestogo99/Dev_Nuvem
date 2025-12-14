import type { Icandy } from "./candy";

export interface LogEntry {
    id: string;
    actionType: string; 
    timestamp: string;
    
    candies: Icandy[];
}
