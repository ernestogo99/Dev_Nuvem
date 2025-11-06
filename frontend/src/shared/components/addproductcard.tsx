import { Add } from "@mui/icons-material";
import { Card, CardContent, IconButton, Typography } from "@mui/material";
import type React from "react";

interface IaddProductCard {
  text: string;
  onClick: () => void;
}

export const AddproductCard: React.FC<IaddProductCard> = ({
  text,
  onClick,
}) => {
  return (
    <Card
      sx={{
        width: 180,
        height: 100,
        borderRadius: 3,
        backgroundColor: "#FFD98E",
        position: "relative",
        textAlign: "center",
        p: 2,
      }}
    >
      <CardContent>
        <IconButton onClick={onClick}>
          <Add />
        </IconButton>
        <Typography>{text}</Typography>
      </CardContent>
    </Card>
  );
};
