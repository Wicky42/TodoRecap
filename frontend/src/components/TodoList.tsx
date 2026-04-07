import type {Todo} from "../types/Todo";
import TodoItem from "./TodoItem";

type Props = {
    todos: Todo[];
};

export default function TodoList({ todos }: Props) {
    return (
        <div>
            {todos.map(todo => (
                <TodoItem key={todo.id} todo={todo} />
            ))}
        </div>
    );
}