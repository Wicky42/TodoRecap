import Overview from "../components/Overview";
import type { Todo } from "../types/Todo";

type Props = {
    todos: Todo[];
    onDelete: (id: string) => void;
    onStatusChange: (todo: Todo) => void;
};

export default function BoardPage({ todos, onDelete, onStatusChange }: Props) {
    return (
        <div className="page-container">
            <h1 className="page-title">Aufgabenübersicht</h1>
            <p className="page-subtitle">
                Verwalte offene, laufende und erledigte Aufgaben in einer klaren Struktur.
            </p>

            <Overview
                todos={todos}
                onDelete={onDelete}
                onStatusChange={onStatusChange}
            />
        </div>
    );
}