import { Add } from "@mui/icons-material";
import { Card, CardContent, IconButton, Typography } from "@mui/material";
import type React from "react";
import { useDialogContext } from "../contexts";

interface IaddProductCard {
  text: string;
}

export const AddproductCard: React.FC<IaddProductCard> = ({ text }) => {
  const { handleOpenDialog } = useDialogContext();
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
        <IconButton onClick={handleOpenDialog}>
          <Add />
        </IconButton>
        <Typography>{text}</Typography>
      </CardContent>
    </Card>
  );
};
