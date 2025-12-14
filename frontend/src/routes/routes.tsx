import { createBrowserRouter } from "react-router-dom";
import { Home, Login } from "../pages";
import LogViewer from "../pages/logviewer/logViewer";

export const route = createBrowserRouter([
  {
    path: "/login",
    element: <Login />,
  },
  {
    path: "/",
    element: <Home></Home>,
  },
  {
    path: "/logs",
    element: <LogViewer />,
  }
]);
