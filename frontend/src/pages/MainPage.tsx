import {useEffect, useState} from "react";
import {getTodos} from "../services/todoService.ts";
import type {Todo} from "../types/Todo.ts";
import TodoList from "../components/TodoList.tsx";
import "./MainPage.css";

export function MainPage() {

    const [todos, setTodos] = useState<Todo[]>([]);

    useEffect(()=> {
        getTodos()
            .then(res => setTodos(res.data))
            .catch(err => console.log(err));
    }, []);

    return (
        <div>
            <h1>Super Kanban</h1>
            <h2>Lege Aufgaben an und verwalte sie!</h2>
            <p>Worauf wartest du noch? Was ist zu tun?</p>

            <input />
            <button>Anlegen</button>

            <div id="todoOverall">
                <div className="todo-column">
                    <TodoList todos={todos.filter( todo => todo.status === "OPEN")} />
                </div>
                <div className="todo-column">
                    <TodoList todos={todos.filter(todo => todo.status === "IN_PROGRESS")} />
                </div>
                <div className="todo-column">
                    <TodoList todos={todos.filter((todo => todo.status === "DONE"))} />
                </div>
            </div>

        </div>
    );
}