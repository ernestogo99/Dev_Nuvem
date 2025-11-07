import { Box } from "@mui/material";
import { useRef } from "react";
import Navbar from "../../shared/components/Navbar";
import { Docinhos } from "../docinhos/docinhos";
import { Cake } from "../cake/cake";
import { Brownies } from "../brownies/brownies";
import { Muffins } from "../muffins/muffins";
import { CreateCandy } from "../createcandy/createcandy";

export const Home = () => {
  const cakesRef = useRef<HTMLDivElement>(null);
  const muffinsRef = useRef<HTMLDivElement>(null);
  const browniesRef = useRef<HTMLDivElement>(null);
  const docinhosRef = useRef<HTMLDivElement>(null);

  const sections = { cakesRef, muffinsRef, browniesRef, docinhosRef };
  return (
    <Box>
      <Navbar sections={sections}></Navbar>
      <Cake ref={cakesRef}></Cake>
      <Brownies ref={browniesRef}></Brownies>
      <Muffins ref={muffinsRef}></Muffins>
      <Docinhos ref={docinhosRef}></Docinhos>
      <CreateCandy></CreateCandy>
    </Box>
  );
};
