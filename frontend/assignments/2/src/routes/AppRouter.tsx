import { createBrowserRouter } from "react-router-dom";
import { Booking } from "../pages/Booking";
import { Confirmation } from "../pages/Confirmation";
import { ROUTES } from "./routePaths";

export const router = createBrowserRouter([
  {
    path: ROUTES.BOOKING,
    element: <Booking />,
  },
  {
    path: ROUTES.CONFIRMATION,
    element: <Confirmation />,
  },
]);
