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

import {
  type IcandyRequest,
  candySchema,
  CandyType,
} from "../../shared/interfaces";
import { useDialogContext } from "../../shared/contexts";
import { useForm, Controller } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import React from "react";
import { candyService } from "../../shared/services";
import toast from "react-hot-toast";

export const CreateCandy = () => {
  const { isOpen, handleCloseDialog } = useDialogContext();

  const [selectedFile, setSelectedFile] = React.useState<File | null>(null);
  const [previewUrl, setPreviewUrl] = React.useState<string | null>(null);

  const {
    register,
    handleSubmit,
    control,
    formState: { errors },
    reset,
  } = useForm<IcandyRequest>({
    resolver: zodResolver(candySchema),
    defaultValues: {
      type: CandyType.CAKE,
    },
  });

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files.length > 0) {
      const file = e.target.files[0];
      setSelectedFile(file);
      setPreviewUrl(URL.createObjectURL(file));
    }
  };

  const handleClose = () => {
    setSelectedFile(null);
    setPreviewUrl(null);
    reset();
    handleCloseDialog();
  };

  const onSubmitForm = async (data: IcandyRequest) => {
    if (!selectedFile) {
      toast.error("Please select an image.");
      return;
    }

    const result = await candyService.createCandy(data, selectedFile);

    if (result instanceof Error) {
      toast.error(result.message);
    } else {
      toast.success(`Candy ${result.name} created successfully!`);
      handleClose();
    }
  };

  React.useEffect(() => {
    return () => {
      if (previewUrl) URL.revokeObjectURL(previewUrl);
    };
  }, [previewUrl]);

  return (
    <Dialog
      open={isOpen}
      onClose={handleClose}
      PaperProps={{
        sx: {
          backgroundColor: "#F3D491",
          borderRadius: "24px",
          padding: 3,
          width: 380,
          overflow: "hidden",
        },
      }}
    >
      <DialogContent sx={{ p: 0 }}>
        <Box
          component="form"
          onSubmit={handleSubmit(onSubmitForm)}
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
              backgroundColor: "#fff",
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
              cursor: "pointer",
              boxShadow: "0 0 4px rgba(0,0,0,0.15)",
              overflow: "hidden",
              "&:hover": {
                backgroundColor: "#f7f7f7",
              },
            }}
          >
            <input type="file" hidden onChange={handleFileChange} />
            {previewUrl ? (
              <Box
                component="img"
                src={previewUrl}
                alt="Preview"
                sx={{ width: "100%", height: "100%", objectFit: "cover" }}
              />
            ) : (
              <IconButton sx={{ pointerEvents: "none" }}>
                <PhotoCamera sx={{ fontSize: 70 }} />
              </IconButton>
            )}
          </Box>

          <Box width="100%">
            <Typography sx={{ fontWeight: 500, mb: 0.5 }}>
              Sweet Name
            </Typography>
            <TextField
              {...register("name")}
              error={!!errors.name}
              helperText={errors.name?.message}
              fullWidth
              placeholder="Name"
              sx={{ backgroundColor: "#fff", borderRadius: "10px" }}
            />
          </Box>

          <Box width="100%">
            <Typography sx={{ fontWeight: 500, mb: 0.5 }}>
              Sweet description
            </Typography>
            <TextField
              {...register("description")}
              error={!!errors.description}
              helperText={errors.description?.message}
              fullWidth
              placeholder="Description"
              sx={{ backgroundColor: "#fff", borderRadius: "10px" }}
            />
          </Box>

          <Box width="100%">
            <Typography sx={{ fontWeight: 500, mb: 0.5 }}>
              Sweet price
            </Typography>
            <TextField
              inputProps={{
                step: "0.01",
                inputMode: "decimal",
              }}
              {...register("price", { valueAsNumber: true })}
              error={!!errors.price}
              helperText={errors.price?.message}
              fullWidth
              type="number"
              placeholder="0.00"
              sx={{ backgroundColor: "#fff", borderRadius: "10px" }}
            />
          </Box>

          <Box width="100%">
            <Typography sx={{ fontWeight: 500, mb: 0.5 }}>
              Sweet Type
            </Typography>
            <Controller
              control={control}
              name="type"
              render={({ field }) => (
                <FormControl
                  fullWidth
                  sx={{ backgroundColor: "#fff", borderRadius: "10px" }}
                >
                  <Select {...field}>
                    {Object.values(CandyType).map((t) => (
                      <MenuItem key={t} value={t}>
                        {t}
                      </MenuItem>
                    ))}
                  </Select>
                </FormControl>
              )}
            />
          </Box>

          <Stack
            direction="column"
            spacing={2}
            mt={3}
            justifyContent="center"
            width="100%"
          >
            <Button type="submit" variant="contained" color="success">
              Create
            </Button>
            <Button variant="contained" color="error" onClick={handleClose}>
              Cancel
            </Button>
          </Stack>
        </Box>
      </DialogContent>
    </Dialog>
  );
};
