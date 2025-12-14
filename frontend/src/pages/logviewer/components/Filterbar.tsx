import { 
    Box, 
    TextField, 
    MenuItem, 
    Button, 
    FormControlLabel, 
    Checkbox, 
    Stack 
} from '@mui/material';
import FilterListIcon from '@mui/icons-material/FilterList';
import CleaningServicesIcon from '@mui/icons-material/CleaningServices';

interface FilterBarProps {
    filters: any; 
    actions: any;
}

export const FilterBar = ({ filters, actions }: FilterBarProps) => {
    const { actionFilter, useDateFilter, startTimeFilter, endTimeFilter } = filters;

    return (
        <Box component="form" onSubmit={actions.applyFilters} sx={{ p: 2, bgcolor: 'background.paper', borderRadius: 2, boxShadow: 1 }}>
            <Stack direction={{ xs: 'column', md: 'row' }} spacing={2} alignItems="center">
                
                {/* Select de Ação */}
                <TextField
                    select
                    label="Tipo de Ação"
                    value={actionFilter}
                    onChange={(e) => filters.setActionFilter(e.target.value)}
                    size="small"
                    sx={{ minWidth: 150 }}
                >
                    <MenuItem value="">Todas</MenuItem>
                    <MenuItem value="CREATE">CREATE</MenuItem>
                    <MenuItem value="UPDATE">UPDATE</MenuItem>
                    <MenuItem value="DELETE">DELETE</MenuItem>
                </TextField>

                {/* Checkbox */}
                <FormControlLabel
                    control={
                        <Checkbox 
                            checked={useDateFilter} 
                            onChange={(e) => filters.setUseDateFilter(e.target.checked)} 
                        />
                    }
                    label="Filtrar por Data"
                />

                {/* Inputs de Data (Só aparecem se checkbox marcado) */}
                {useDateFilter && (
                    <>
                        <TextField
                            type="datetime-local"
                            label="De"
                            InputLabelProps={{ shrink: true }}
                            value={startTimeFilter}
                            onChange={(e) => filters.setStartTimeFilter(e.target.value)}
                            size="small"
                            required
                        />
                        <TextField
                            type="datetime-local"
                            label="Até"
                            InputLabelProps={{ shrink: true }}
                            value={endTimeFilter}
                            onChange={(e) => filters.setEndTimeFilter(e.target.value)}
                            size="small"
                            required
                        />
                    </>
                )}

                {/* Botões */}
                <Box sx={{ flexGrow: 1 }} /> {/* Empurra botões para direita */}
                
                <Button variant="contained" type="submit" startIcon={<FilterListIcon />}>
                    Filtrar
                </Button>
                <Button variant="outlined" onClick={actions.handleReset} startIcon={<CleaningServicesIcon />}>
                    Limpar
                </Button>
            </Stack>
        </Box>
    );
};