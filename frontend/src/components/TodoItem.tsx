import type {Todo} from "../types/Todo";
import "./TodoItem.css";

type Props = {
    todo: Todo;
    onDelete: (id: string) => void;
    onStatusChange: (todo: Todo) => void;
};

export default function TodoItem({ todo, onDelete, onStatusChange }: Props) {
    function getNextStatus() {
        if (todo.status === "OPEN") return "IN_PROGRESS";
        if (todo.status === "IN_PROGRESS") return "DONE";
        return "OPEN";
    }

    return (
        <div className="todo-item">
            <div className="todo-content">
                <span className="todo-text">{todo.description}</span>
                <span className={`todo-status status-${todo.status}`}>
          {todo.status}
        </span>
            </div>

            <div className="todo-actions">
                <button onClick={() => onStatusChange({ ...todo, status: getNextStatus() })}>
                    >>
                </button>
                <button onClick={() => onDelete(todo.id)}>
                    Löschen
                </button>
            </div>
        </div>
    );
}