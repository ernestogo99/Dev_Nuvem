import { Box, Typography } from "@mui/material";
import { ProductList } from "../../shared/components";
import { type Icandy, CandyType } from "../../shared/interfaces";
import bigode from "../../assets/Group 55.png";
import * as React from "react";

export const Cake = React.forwardRef<HTMLDivElement>((props, ref) => {
  const mockCandies: Icandy[] = [
    {
      id: 1,
      name: "Choco Delight",
      price: 4.99,
      description: "Delicioso chocolate ao leite com recheio cremoso",
      type: CandyType.CAKE,
      imageKey: "choco-delight.jpg",
    },
    {
      id: 2,
      name: "Vanilla Dream",
      price: 3.99,
      description: "Bolo de baunilha fofinho com cobertura de chantilly",
      type: CandyType.CAKE,
      imageKey: "vanilla-dream.jpg",
    },
    {
      id: 3,
      name: "Strawberry Surprise",
      price: 5.49,
      description: "Bolo de morango com recheio cremoso e calda doce",
      type: CandyType.CAKE,
      imageKey: "strawberry-surprise.jpg",
    },
    {
      id: 4,
      name: "Chocolate Heaven",
      price: 6.29,
      description: "Bolo de chocolate intenso com pedaços de chocolate",
      type: CandyType.CAKE,
      imageKey: "chocolate-heaven.jpg",
    },
    {
      id: 5,
      name: "Lemon Zest",
      price: 4.49,
      description: "Bolo de limão fresco com cobertura suave",
      type: CandyType.CAKE,
      imageKey: "lemon-zest.jpg",
    },
    {
      id: 6,
      name: "Red Velvet",
      price: 5.99,
      description: "Clássico bolo red velvet com creme de queijo",
      type: CandyType.CAKE,
      imageKey: "red-velvet.jpg",
    },
    {
      id: 7,
      name: "Caramel Delight",
      price: 4.79,
      description: "Bolo macio com recheio de caramelo salgado",
      type: CandyType.CAKE,
      imageKey: "caramel-delight.jpg",
    },
  ];

  return (
    <Box ref={ref}>
      <Typography
        mb={10}
        textAlign="center"
        sx={{ fontFamily: "Federo" }}
        variant="h4"
      >
        Cakes
      </Typography>
      <ProductList text="Add a new cake" products={mockCandies} />
      <Box textAlign="center" mt={10}>
        <img src={bigode} />
      </Box>
    </Box>
  );
});
