import { Box, Typography } from "@mui/material";
import { DeleteMessage, ProductList } from "../../shared/components";

import bigode from "../../assets/Group 55.png";
import * as React from "react";

import { candyService } from "../../shared/services";
import { useCandyLogic } from "../../shared/hooks";
import { useEditCandyDialog } from "../../shared/contexts";
import { EditCandy } from "../editcandy/editcandy";

export const Docinhos = React.forwardRef<HTMLDivElement>((props, ref) => {
  const { list, open, openDialog, closeDialog, deleteItem } = useCandyLogic(
    candyService.getallDocinhos
  );
  const { openEditDialog } = useEditCandyDialog();

  return (
    <Box ref={ref}>
      <Typography
        mb={10}
        textAlign="center"
        sx={{ fontFamily: "Federo" }}
        variant="h4"
      >
        Docinhos
      </Typography>
      <ProductList
        onEdit={openEditDialog}
        handleOpenModal={openDialog}
        text="new Candy"
        products={list}
      />
      <Box textAlign="center" mt={10}>
        <img src={bigode} />
      </Box>
      <DeleteMessage
        handleDelete={deleteItem}
        show={open}
        Onclose={closeDialog}
        tittle="Do you want to delete this candy?"
      ></DeleteMessage>
      <EditCandy></EditCandy>
    </Box>
  );
});
