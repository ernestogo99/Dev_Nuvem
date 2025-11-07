import { Box, Grid } from "@mui/material";
import type React from "react";
import type { Icandy } from "../interfaces";
import { ProductCard } from "./productcard";
import { AddproductCard } from "./addproductcard";
import { useAuthContext } from "../contexts";

interface IproductListProps {
  products: Icandy[];
  onEdit?: (id: number) => void;
  handleOpenModal?: (candy: Icandy) => void;
  text?: string;
}

export const ProductList: React.FC<IproductListProps> = ({
  products,
  handleOpenModal,
  onEdit,
  text,
}) => {
  const { isLogged } = useAuthContext();
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
            <ProductCard
              candy={product}
              onEdit={onEdit}
              handleOpenModal={handleOpenModal!}
            />
          </Grid>
        ))}
        {isLogged && <AddproductCard text={text!}></AddproductCard>}
      </Grid>
    </Box>
  );
};
