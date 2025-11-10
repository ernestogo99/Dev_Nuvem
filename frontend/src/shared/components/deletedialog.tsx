import React from "react";

import {
  Box,
  Button,
  Dialog,
  DialogActions,
  DialogTitle,
  IconButton,
} from "@mui/material";
import { Close } from "@mui/icons-material";

export interface IdeleteDialog {
  show: boolean;
  tittle: string;
  Onclose: () => void;
  handleDelete: () => void;
}

export const DeleteMessage: React.FC<IdeleteDialog> = ({
  Onclose,
  show,
  tittle,
  handleDelete,
}) => {
  return (
    <Dialog
      aria-labelledby="alert-dialog-title"
      onClose={Onclose}
      fullWidth
      maxWidth="sm"
      open={show}
      sx={{
        "& .MuiDialog-paper": {
          border: "2px solid #ffffff",
          borderRadius: 3,
          boxShadow: "0px 10px 25px rgba(0, 0, 0, 0.1)",
          padding: 2,
          backgroundColor: "#FFD98E",
        },
      }}
    >
      <Box display="flex" justifyContent="flex-end">
        <IconButton onClick={Onclose}>
          <Close />
        </IconButton>
      </Box>

      <DialogTitle textAlign="center" id="alert-dialog-title">
        {tittle}
      </DialogTitle>

      <DialogActions sx={{ justifyContent: "center", paddingBottom: 2 }}>
        <Button
          variant="contained"
          color="error"
          sx={{ minWidth: 120 }}
          onClick={handleDelete}
        >
          Yes
        </Button>
        <Button
          variant="contained"
          color="inherit"
          onClick={Onclose}
          sx={{ minWidth: 120 }}
        >
          Cancel
        </Button>
      </DialogActions>
    </Dialog>
  );
};
