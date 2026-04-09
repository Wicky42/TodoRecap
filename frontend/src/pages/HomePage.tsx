import TodoInput from "../components/TodoInput";
import type { Todo } from "../types/Todo";
import "./HomePage.css";

type Props = {
    onAddTodo: (description: string) => void;
    todos: Todo[];
};

export default function HomePage({ onAddTodo, todos }: Props) {
    const openTodos = todos.filter((todo) => todo.status === "OPEN").length;
    const inProgressTodos = todos.filter((todo) => todo.status === "IN_PROGRESS").length;
    const doneTodos = todos.filter((todo) => todo.status === "DONE").length;

    return (
        <div className="page-container">
            <h1 className="page-title">Willkommen bei Super Kanban</h1>
            <p className="page-subtitle">
                Behalte deine Aufgaben im Blick und organisiere deinen Tag klar und strukturiert.
            </p>

            <div className="homepage-stats">
                <div className="stat-card stat-open">
                    <h3>Offene Aufgaben</h3>
                    <p>{openTodos}</p>
                </div>

                <div className="stat-card stat-progress">
                    <h3>In Bearbeitung</h3>
                    <p>{inProgressTodos}</p>
                </div>

                <div className="stat-card stat-done">
                    <h3>Erledigt</h3>
                    <p>{doneTodos}</p>
                </div>
            </div>

            <div className="homepage-input-section section-card section-spacing">
                <h2>Neue Aufgabe anlegen</h2>
                <p className="page-subtitle">Füge schnell eine neue Aufgabe hinzu.</p>
                <TodoInput onAddTodo={onAddTodo} />
            </div>
        </div>
    );
}