import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { RouterProvider } from "react-router-dom";
import { router } from "./routes/AppRouter";
import { Provider } from "react-redux";
import { store } from "./redux/store";
import { ErrorBoundary } from "./components/common/error/ErrorBoundary";
import "./styles/globals.scss";

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <Provider store={store}>
      <ErrorBoundary>
        <RouterProvider router={router} />
      </ErrorBoundary>
    </Provider>
  </StrictMode>,
);
