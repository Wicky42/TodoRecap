import type {Todo} from "../types/Todo";
import "./TodoItem.css";

type Props = {
    todo: Todo;
};

export default function TodoItem({ todo }: Props) {
    return (
        <div className="todo-item">
            <div>
                <span className="todo-text">{todo.description}</span>
                <span className={`todo-status status-${todo.status}`}>
          {todo.status}
        </span>
            </div>

            <div className="todo-actions">
                <button className="edit-btn">Edit</button>
                <button className="status-btn">Status</button>
            </div>
        </div>
    );
}