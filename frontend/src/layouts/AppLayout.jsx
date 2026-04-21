import { Outlet } from "react-router-dom";
import NavBar from "../components/NavBar";

export default function AppLayout() {
  return (
    <div className="app-shell">
      <NavBar />
      <main className="page">
        <Outlet />
      </main>
    </div>
  );
}
