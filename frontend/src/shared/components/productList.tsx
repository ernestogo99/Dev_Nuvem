import { Box, Grid } from "@mui/material";
import type React from "react";
import type { Icandy } from "../interfaces";
import { ProductCard } from "./productcard";
import { AddproductCard } from "./addproductcard";

interface IproductListProps {
  products: Icandy[];
  onEdit?: (id: number) => void;
  onDelete?: (id: number) => void;
  text?: string;
  onClick?: () => void;
}

export const ProductList: React.FC<IproductListProps> = ({
  products,
  onDelete,
  onEdit,
  text,
}) => {
  return (
    <Box mt={4}>
      <Grid gap={7} container justifyContent="center">
        {products.map((product) => (
          <Grid
            key={product.id}
            size={{ xs: 12, sm: 6, md: 1 }}
            display="flex"
            justifyContent="center"
            flexWrap="wrap"
          >
            <ProductCard candy={product} onEdit={onEdit} onDelete={onDelete} />
          </Grid>
        ))}
        <AddproductCard text={text!}></AddproductCard>
      </Grid>
    </Box>
  );
};
