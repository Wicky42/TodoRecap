import axios from "axios";
import type {Todo} from "../types/Todo.ts";

const API_URL = "/api/todo";

export const getTodos = () => axios.get(API_URL);

export function createTodo(todo: Omit<Todo, "id">) {
    return axios.post(API_URL, todo);
}

export function deleteTodo(id: string) {
    return axios.delete(`${API_URL}/${id}`);
}

export function updateTodo(updatedTodo: Todo) {
    return axios.put(`${API_URL}/${updatedTodo.id}`, updatedTodo);
}