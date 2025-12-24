import "@fontsource/figtree/700.css";
import "@fontsource/figtree/400.css";
import { RouterProvider } from "react-router-dom";
import { route } from "./routes";

function App() {
  return (
    <>
      <RouterProvider router={route}></RouterProvider>
    </>
  );
}

export default App;
