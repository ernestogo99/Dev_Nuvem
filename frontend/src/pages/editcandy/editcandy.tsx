// src/shared/components/EditCandy.tsx
import { zodResolver } from "@hookform/resolvers/zod";
import { PhotoCamera } from "@mui/icons-material";
import {
  Dialog,
  DialogContent,
  Box,
  IconButton,
  Typography,
  TextField,
  FormControl,
  Select,
  MenuItem,
  Stack,
  Button,
} from "@mui/material";
import React from "react";
import { useForm, Controller } from "react-hook-form";
import toast from "react-hot-toast";

import {
  type IcandyRequest,
  candySchema,
  CandyType,
} from "../../shared/interfaces";
import { candyService } from "../../shared/services";
import { useEditCandyDialog } from "../../shared/contexts";

export const EditCandy: React.FC = () => {
  const { isOpen, candyId, closeEditDialog } = useEditCandyDialog();

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

  React.useEffect(() => {
    if (!isOpen || !candyId) return;

    const loadCandy = async () => {
      const result = await candyService.getCandyById(candyId);
      if (result instanceof Error) {
        toast.error(result.message);
        closeEditDialog();
      } else {
        reset({
          name: result.name,
          description: result.description,
          price: result.price,
          type: result.type,
        });
        if (result.imageUrl) {
          setPreviewUrl(result.imageUrl);
        }
      }
    };

    loadCandy();
  }, [isOpen, candyId, reset, closeEditDialog]);

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
    closeEditDialog();
  };

  const onSubmitForm = async (data: IcandyRequest) => {
    const result = await candyService.updateCandy(
      candyId!,
      data,
      selectedFile ?? undefined
    );

    if (result instanceof Error) {
      toast.error(result.message);
    } else {
      toast.success(`Candy ${result.name} updated successfully!`);
      handleClose();
    }
  };

  React.useEffect(() => {
    return () => {
      if (previewUrl && selectedFile) URL.revokeObjectURL(previewUrl);
    };
  }, [previewUrl, selectedFile]);

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
          {/* Upload imagem */}
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
              "&:hover": { backgroundColor: "#f7f7f7" },
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

          {/* Nome */}
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

          {/* Descrição */}
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
              {...register("price", { valueAsNumber: true })}
              error={!!errors.price}
              helperText={errors.price?.message}
              fullWidth
              type="number"
              inputProps={{
                step: "0.01",
                inputMode: "decimal",
              }}
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
              Update
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
