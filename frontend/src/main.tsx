import { createRoot } from "react-dom/client";
import { ThemeProvider } from "@mui/material/styles";
import "./index.css";
import App from "./App.tsx";
import React from "react";
import CssBaseline from "@mui/material/CssBaseline";
import customTheme from "./theme";
import { AuthContextProvider } from "./shared/contexts/authcontext.tsx";
import { DialogContextProvider } from "./shared/contexts/createcandydialogcontext.tsx";
import { Toaster } from "react-hot-toast";
import { EditCandyDialogProvider } from "./shared/contexts/editcandydialogcontext.tsx";
createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <ThemeProvider theme={customTheme}>
      <AuthContextProvider>
        <DialogContextProvider>
          <EditCandyDialogProvider>
            <CssBaseline />
            <Toaster></Toaster>
            <App />
          </EditCandyDialogProvider>
        </DialogContextProvider>
      </AuthContextProvider>
    </ThemeProvider>
  </React.StrictMode>
);
