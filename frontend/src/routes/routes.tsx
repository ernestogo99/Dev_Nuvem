import { createBrowserRouter } from "react-router-dom";
import { Home, Login } from "../pages";

export const route = createBrowserRouter([
  {
    path: "/login",
    element: <Login />,
  },
  {
    path: "/",
    element: <Home></Home>,
  },
]);
