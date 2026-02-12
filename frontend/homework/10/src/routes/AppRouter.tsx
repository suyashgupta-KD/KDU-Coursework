import { createBrowserRouter } from "react-router-dom";
import { Home } from "../pages/Home";
import { UserInfo } from "../pages/UserInfo";
import { ROUTES } from "./routePaths";
export const router = createBrowserRouter([
  {
    path: ROUTES.HOME,
    element: <Home />,
  },
  {
    path: ROUTES.USER_INFO,
    element: <UserInfo />,
  },
]);
