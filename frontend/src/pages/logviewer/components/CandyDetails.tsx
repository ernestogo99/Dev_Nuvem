import { memo } from 'react'; 
import { Box, Typography } from '@mui/material';
import type { Icandy } from '../../../shared/interfaces/candy';

const Field = ({ label, value }: { label: string, value: any }) => (
    <Typography variant="body2" component="span" sx={{ mr: 2, display: 'inline-block' }}>
        <strong>{label}:</strong> {value}
    </Typography>
);

export const CandyDetails = memo(({ items }: { items: Icandy[] }) => {
    if (!items?.length) {
        return <Typography variant="caption" color="text.secondary">Nenhum doce registrado</Typography>;
    }

    return (
        <Box>
            {items.map((candy, index) => (
                <Box key={index} sx={{ mb: 1, pb: 0.5, borderBottom: '1px dashed #eee' }}>
                    <Field label="Nome" value={candy.name} />
                    <Field label="Preço" value={`R$ ${candy.price}`} />
                    <Field label="Id" value={candy.id} />
                </Box>
            ))}
        </Box>
    );
});