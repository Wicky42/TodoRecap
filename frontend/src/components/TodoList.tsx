import type {Todo} from "../types/Todo";
import TodoItem from "./TodoItem";

type Props = {
    todos: Todo[];
    onDelete: (id: string) => void;
    onStatusChange: (todo: Todo) => void;
};

export default function TodoList({ todos, onDelete, onStatusChange }: Props) {
    return (
        <div>
            {todos.map((todo) => (
                <TodoItem
                    key={todo.id}
                    todo={todo}
                    onDelete={onDelete}
                    onStatusChange={onStatusChange}
                />
            ))}
        </div>
    );
}