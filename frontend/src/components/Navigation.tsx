import { Link } from "react-router-dom";
import "./Navigation.css";

export default function Navigation() {
    return (
        <nav className="nav">
            <h2 className="nav-title">Super Kanban</h2>
            <div className="nav-links">
                <Link to="/">Start</Link>
                <Link to="/board">Übersicht</Link>
                <Link to="/ideas">Vorschläge</Link>
            </div>
        </nav>
    );
}