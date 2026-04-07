import axios from "axios";

const API_URL = "/api/todo";

export const getTodos = () => axios.get(API_URL);