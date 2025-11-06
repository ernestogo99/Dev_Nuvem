import React, {
  createContext,
  useContext,
  useEffect,
  useState,
  type ReactNode,
} from "react";

interface IauthContextProps {
  isLogged: boolean;
}

interface IchildrenProps {
  children: ReactNode;
}

const authContext = createContext<IauthContextProps | undefined>(undefined);

export const useAuthContext = () => {
  const context = useContext(authContext);

  if (!context) {
    throw new Error("the auth context must be in a provider");
  }

  return context;
};

export const AuthContextProvider: React.FC<IchildrenProps> = ({ children }) => {
  const [isLogged, setIslogged] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem("token");
    setIslogged(!!token);
  }, []);

  return (
    <authContext.Provider value={{ isLogged }}>{children}</authContext.Provider>
  );
};
