import { createContext, useContext, useState, type ReactNode } from "react";

interface IEditCandyDialogContext {
  isOpen: boolean;
  candyId: number | null;
  openEditDialog: (id: number) => void;
  closeEditDialog: () => void;
}

const EditCandyDialogContext = createContext<
  IEditCandyDialogContext | undefined
>(undefined);

export const useEditCandyDialog = () => {
  const context = useContext(EditCandyDialogContext);
  if (!context) {
    throw new Error(
      "useEditCandyDialog must be used inside EditCandyDialogProvider"
    );
  }
  return context;
};

export const EditCandyDialogProvider = ({
  children,
}: {
  children: ReactNode;
}) => {
  const [isOpen, setIsOpen] = useState(false);
  const [candyId, setCandyId] = useState<number | null>(null);

  const openEditDialog = (id: number) => {
    setCandyId(id);
    setIsOpen(true);
  };

  const closeEditDialog = () => {
    setIsOpen(false);
    setCandyId(null);
  };

  return (
    <EditCandyDialogContext.Provider
      value={{ isOpen, candyId, openEditDialog, closeEditDialog }}
    >
      {children}
    </EditCandyDialogContext.Provider>
  );
};
