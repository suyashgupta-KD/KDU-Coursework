import { Outlet } from "react-router-dom";
import { Navbar } from "../components/layout/Navbar";

export function MainLayout() {
  return (
    <div className="min-h-screen">
      <Navbar />
      <main className="mx-auto max-w-6xl p-4">
        <Outlet />
      </main>
    </div>
  );
}
