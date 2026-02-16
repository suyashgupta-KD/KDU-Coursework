import { createBrowserRouter } from "react-router-dom";

import { Registration } from "../pages/Registration/Registration";
import { Status } from "../pages/Status/Status";

export const router = createBrowserRouter([
  {
    path: "/",
    element: <Registration />,
  },
  {
    path: "/status/:id",
    element: <Status />,
  },
]);
