import React, {
  createContext,
  useContext,
  useState,
  type ReactNode,
} from "react";

interface IcandyDialogProps {
  isOpen: boolean;
  handleOpenDialog: () => void;
  handleCloseDialog: () => void;
}

interface IchildrenProps {
  children: ReactNode;
}

const candyDialogContext = createContext<IcandyDialogProps | undefined>(
  undefined
);

export const useDialogContext = () => {
  const context = useContext(candyDialogContext);

  if (!context) {
    throw new Error("candy dialog context must be in a provider");
  }

  return context;
};

export const DialogContextProvider: React.FC<IchildrenProps> = ({
  children,
}) => {
  const [isOpen, setIsopen] = useState(false);

  const handleCloseDialog = () => {
    setIsopen(false);
  };

  const handleOpenDialog = () => {
    setIsopen(true);
  };

  return (
    <candyDialogContext.Provider
      value={{ isOpen, handleCloseDialog, handleOpenDialog }}
    >
      {children}
    </candyDialogContext.Provider>
  );
};
