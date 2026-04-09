import {BrowserRouter, Route, Routes} from "react-router-dom";
import Navigation from "./components/Navigation.tsx";
import HomePage from "./pages/HomePage.tsx";
import BoardPage from "./pages/BoardPage.tsx";
import IdeasPage from "./pages/IdeasPage.tsx";
import {useEffect, useState} from "react";
import type {Todo} from "./types/Todo.ts";
import {createTodo, deleteTodo, getTodos, updateTodo} from "./services/todoService.ts";

export default function App() {
    const [todos, setTodos] = useState<Todo[]>([]);

    function loadTodos() {
        getTodos()
            .then(res => setTodos(res.data))
            .catch(err => console.log(err));
    }

    useEffect(() => {
        loadTodos();
    }, []);

    function handleAddTodo(description: string) {
        return createTodo({
            description: description,
            status: "OPEN"
        })
            .then(() => loadTodos())
            .catch(err => console.error(err));
    }

    function handleDeleteTodo(id: string) {
        return deleteTodo(id)
            .then(() => loadTodos())
            .catch(err => console.error(err));
    }

    function handleStatusChange(updatedTodo: Todo) {
        return updateTodo(updatedTodo)
            .then(() => loadTodos())
            .catch(err => console.error(err));
    }

    return (
        <BrowserRouter>
            <Navigation />
            <Routes>
                <Route
                    path="/"
                    element={<HomePage onAddTodo={handleAddTodo} todos={todos} />}
                />
                <Route
                    path="/board"
                    element={
                        <BoardPage
                            todos={todos}
                            onDelete={handleDeleteTodo}
                            onStatusChange={handleStatusChange}
                        />
                    }
                />
                <Route
                    path="/ideas"
                    element={<IdeasPage onAddTodo={handleAddTodo} />}
                />
            </Routes>
        </BrowserRouter>
    );
}