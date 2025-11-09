// src/shared/hooks/useCandyList.ts
import { useEffect, useState, useCallback } from "react";
import { type Icandy, type IcandyRequest } from "../interfaces";
import { candyService } from "../services";
import toast from "react-hot-toast";

export function useCandyLogic(fetchMethod: () => Promise<Icandy[] | Error>) {
  const [list, setList] = useState<Icandy[]>([]);
  const [selectedCandy, setSelectedCandy] = useState<Icandy | null>(null);
  const [open, setOpen] = useState(false);

  const reloadList = useCallback(() => {
    fetchMethod().then((response) => {
      if (response instanceof Error) {
        toast.error(response.message);
      } else {
        setList(response);
      }
    });
  }, [fetchMethod]);

  useEffect(() => {
    reloadList();
  }, [reloadList]);

  const openDialog = (candy: Icandy) => {
    setSelectedCandy(candy);
    setOpen(true);
  };

  const closeDialog = () => {
    setSelectedCandy(null);
    setOpen(false);
  };

  const deleteItem = useCallback(() => {
    if (!selectedCandy) return;

    candyService.deleteCandy(selectedCandy.id).then((response) => {
      if (response instanceof Error) {
        toast.error(response.message);
      } else {
        setList((old) => old.filter((x) => x.id !== selectedCandy.id));
        toast.success("Deleted!");
        closeDialog();
      }
    });
  }, [selectedCandy]);

  const addItem = useCallback(
    async (candy: IcandyRequest, imageFile: File) => {
      const response = await candyService.createCandy(candy, imageFile);
      if (response instanceof Error) {
        toast.error(response.message);
      } else {
        toast.success("Candy created!");
        reloadList();
      }
    },
    [reloadList]
  );

  return {
    list,
    selectedCandy,
    open,
    openDialog,
    closeDialog,
    deleteItem,
    reloadList,
    addItem,
  };
}
