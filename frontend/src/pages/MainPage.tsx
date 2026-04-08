import Overview from "../components/Overview";
import TodoInput from "../components/TodoInput";
import { useEffect, useState } from "react";
import type { Todo } from "../types/Todo";
import { deleteTodo, getTodos, updateTodo } from "../services/todoService";

export function MainPage() {
    const [todos, setTodos] = useState<Todo[]>([]);

    function loadTodos() {
        getTodos()
            .then((res) => setTodos(res.data))
            .catch((err) => console.log(err));
    }

    useEffect(() => {
        loadTodos();
    }, []);

    function handleDelete(id: string) {
        deleteTodo(id)
            .then(() => loadTodos())
            .catch((err) => console.error(err));
    }

    function handleStatusChange(updatedTodo: Todo) {
        updateTodo(updatedTodo)
            .then(() => loadTodos())
            .catch((err) => console.error(err));
    }

    return (
        <div>
            <h1>Super Kanban</h1>
            <h2>Lege Aufgaben an und verwalte sie!</h2>
            <p>Worauf wartest du noch? Was ist zu tun?</p>

            <TodoInput onTodoCreated={loadTodos} />
            <Overview
                todos={todos}
                onDelete={handleDelete}
                onStatusChange={handleStatusChange}
            />
        </div>
    );
}