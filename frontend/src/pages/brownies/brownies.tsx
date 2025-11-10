import { Box, Typography } from "@mui/material";
import { DeleteMessage, ProductList } from "../../shared/components";
import bigode from "../../assets/Group 55.png";
import * as React from "react";
import { useCandyLogic } from "../../shared/hooks";
import { candyService } from "../../shared/services";
import { EditCandy } from "../editcandy/editcandy";
import { useEditCandyDialog } from "../../shared/contexts";

export const Brownies = React.forwardRef<HTMLDivElement>((_props, ref) => {
  const { list, open, openDialog, closeDialog, deleteItem } = useCandyLogic(
    candyService.getallBrownies
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
        Brownies
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
