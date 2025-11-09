import { Box, Button } from "@mui/material";
import * as React from "react";

interface NavigationMenuProps {
  sections: {
    cakesRef: React.RefObject<HTMLDivElement | null>;
    muffinsRef: React.RefObject<HTMLDivElement | null>;
    browniesRef: React.RefObject<HTMLDivElement | null>;
    docinhosRef: React.RefObject<HTMLDivElement | null>;
  };
}

const NavigationMenu: React.FC<NavigationMenuProps> = ({ sections }) => {
  const handleScroll = (ref: React.RefObject<HTMLDivElement | null>) => {
    const navbarHeight = 64;
    if (ref.current) {
      const top = ref.current.offsetTop - navbarHeight;
      window.scrollTo({ top, behavior: "smooth" });
    }
  };

  return (
    <Box
      mt={2}
      sx={{
        display: { sm: "flex" },
        gap: 4,
        alignItems: "center",
      }}
    >
      <Button color="inherit" onClick={() => handleScroll(sections.cakesRef)}>
        CAKES
      </Button>
      <Button color="inherit" onClick={() => handleScroll(sections.muffinsRef)}>
        MUFFINS
      </Button>
      <Button
        color="inherit"
        onClick={() => handleScroll(sections.browniesRef)}
      >
        BROWNIES
      </Button>
      <Button
        color="inherit"
        onClick={() => handleScroll(sections.docinhosRef)}
      >
        DOCINHOS
      </Button>
    </Box>
  );
};

export default NavigationMenu;
