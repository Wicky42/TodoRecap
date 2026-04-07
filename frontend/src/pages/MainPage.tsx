import {useEffect, useState} from "react";
import {getTodos} from "../services/todoService.ts";
import type {Todo} from "../types/Todo.ts";
import TodoItem from "../components/TodoItem.tsx";
import TodoList from "../components/TodoList.tsx";

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

            <div id={"todoOverall"}>
                <TodoList todos={todos}></TodoList>
                <TodoList todos={todos}></TodoList>
            </div>

        </div>
    );
}