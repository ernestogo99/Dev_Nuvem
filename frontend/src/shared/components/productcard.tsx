import { Card, CardContent, Typography, IconButton, Box } from "@mui/material";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import type { Icandy } from "../interfaces";
import { useAuthContext } from "../contexts/authcontext";

interface ProductCardProps {
  candy: Icandy;
  onEdit?: (id: number) => void;
  onDelete?: (id: number) => void;
}

export const ProductCard: React.FC<ProductCardProps> = ({
  candy,
  onEdit,
  onDelete,
}) => {
  const { isLogged } = useAuthContext();
  return (
    <Card
      sx={{
        width: 180,
        borderRadius: 3,
        backgroundColor: "#FFD98E",
        position: "relative",
        textAlign: "center",
        paddingTop: 2,
      }}
    >
      {isLogged && (
        <Box
          sx={{
            position: "absolute",
            top: 8,
            right: 8,
            display: "flex",
            gap: 1,
          }}
        >
          <IconButton size="small" onClick={() => onEdit!(candy.id)}>
            <EditIcon sx={{ color: "#0044cc", fontSize: 18 }} />
          </IconButton>
          <IconButton size="small" onClick={() => onDelete!(candy.id)}>
            <DeleteIcon sx={{ color: "#d00000", fontSize: 18 }} />
          </IconButton>
        </Box>
      )}

      <Box
        sx={{
          width: 100,
          height: 100,
          borderRadius: "50%",
          backgroundColor: "#d9d9d9",
          margin: "0 auto",
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
        }}
      >
        {candy.imageKey ? (
          <img
            src={candy.imageKey?.toString()}
            alt={candy.name}
            style={{
              width: "100%",
              height: "100%",
              borderRadius: "50%",
              objectFit: "cover",
            }}
          />
        ) : (
          <span style={{ fontSize: 28, opacity: 0.4 }}>📷</span>
        )}
      </Box>

      <CardContent sx={{ paddingBottom: "8px !important" }}>
        <Typography variant="subtitle1" fontWeight={700}>
          {candy.name}
        </Typography>

        <Typography variant="body2" sx={{ opacity: 0.7 }}>
          {candy.description}
        </Typography>

        <Typography variant="subtitle1" sx={{ marginTop: 1, fontWeight: 700 }}>
          R$ {candy.price.toFixed(2)}
        </Typography>
      </CardContent>
    </Card>
  );
};
