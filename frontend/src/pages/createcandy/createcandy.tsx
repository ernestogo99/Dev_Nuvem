import { PhotoCamera } from "@mui/icons-material";
import {
  Box,
  Button,
  Dialog,
  DialogContent,
  FormControl,
  IconButton,
  MenuItem,
  Select,
  Stack,
  TextField,
  Typography,
} from "@mui/material";

import { CandyType } from "../../shared/interfaces";
import { useDialogContext } from "../../shared/contexts";

export const CreateCandy = () => {
  const { isOpen, handleCloseDialog } = useDialogContext();
  return (
    <Dialog
      open={isOpen}
      onClose={handleCloseDialog}
      PaperProps={{
        sx: {
          backgroundColor: "#F3D491",
          borderRadius: "24px",
          padding: 5,
          width: 380,
          overflow: "hidden",
        },
      }}
    >
      <DialogContent sx={{ p: 0 }}>
        <Box
          component="form"
          display="flex"
          flexDirection="column"
          gap={2.5}
          alignItems="center"
          justifyContent="center"
        >
          <Box
            component="label"
            sx={{
              width: "180px",
              height: "150px",
              borderRadius: "20px",
              backgroundColor: "white",
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
              cursor: "pointer",
              boxShadow: "0 0 4px rgba(0,0,0,0.15)",
              transition: "0.2s",
              "&:hover": {
                backgroundColor: "#f7f7f7",
              },
            }}
          >
            <input type="file" hidden />
            <IconButton>
              <PhotoCamera sx={{ fontSize: 70 }}></PhotoCamera>
            </IconButton>
          </Box>

          <Box width="100%">
            <Typography sx={{ fontWeight: 500, mb: 0.5 }}>
              Sweet title
            </Typography>
            <TextField
              fullWidth
              placeholder="Title"
              sx={{
                backgroundColor: "#fff",
                borderRadius: "10px",
              }}
            />
          </Box>

          <Box width="100%">
            <Typography sx={{ fontWeight: 500, mb: 0.5 }}>
              Sweet description
            </Typography>
            <TextField
              fullWidth
              placeholder="Description"
              sx={{
                backgroundColor: "#fff",
                borderRadius: "10px",
              }}
            />
          </Box>

          <Box width="100%">
            <Typography sx={{ fontWeight: 500, mb: 0.5 }}>
              Sweet price
            </Typography>
            <TextField
              fullWidth
              type="number"
              placeholder="0.00"
              sx={{
                backgroundColor: "#fff",
                borderRadius: "10px",
              }}
            />
          </Box>
        </Box>

        <Box width="100%">
          <Typography sx={{ fontWeight: 500, mb: 0.5 }}>Sweet Type</Typography>
          <FormControl
            fullWidth
            sx={{ backgroundColor: "#fff", borderRadius: "10px" }}
          >
            <Select defaultValue={CandyType.CAKE}>
              {Object.values(CandyType).map((t) => (
                <MenuItem key={t} value={t}>
                  {t}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Box>

        <Stack direction="column" spacing={2} mt={3} justifyContent="center">
          <Button variant="contained" color="success">
            Create
          </Button>

          <Button variant="contained" color="error" onClick={handleCloseDialog}>
            Cancel
          </Button>
        </Stack>
      </DialogContent>
    </Dialog>
  );
};
