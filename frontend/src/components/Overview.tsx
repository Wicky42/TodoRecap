import TodoList from "./TodoList";
import type { Todo } from "../types/Todo";
import "./Overview.css";

type Props = {
    todos: Todo[];
    onDelete: (id: string) => void;
    onStatusChange: (todo: Todo) => void;
};

export default function Overview({ todos, onDelete, onStatusChange }: Props) {
    return (
        <div id="todoOverall">
            <div className="todo-column">
                <label>Open</label>
                <TodoList
                    todos={todos.filter((todo) => todo.status === "OPEN")}
                    onDelete={onDelete}
                    onStatusChange={onStatusChange}
                />
            </div>

            <div className="todo-column">
                <label>In Progress</label>
                <TodoList
                    todos={todos.filter((todo) => todo.status === "IN_PROGRESS")}
                    onDelete={onDelete}
                    onStatusChange={onStatusChange}
                />
            </div>

            <div className="todo-column">
                <label>Done</label>
                <TodoList
                    todos={todos.filter((todo) => todo.status === "DONE")}
                    onDelete={onDelete}
                    onStatusChange={onStatusChange}
                />
            </div>
        </div>
    );
}