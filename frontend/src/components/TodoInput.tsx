import {useState} from "react";
import "./TodoInput.css";

type Props = {
    onAddTodo: (description: string) => void;
};

export default function TodoInput({ onAddTodo }: Props) {
    const [description, setDescription] = useState("");

    function handleSubmit() {
        if (!description.trim()) return;

        onAddTodo(description);
        setDescription("");
    }

    return (
        <div className="todo-input-wrapper">
            <input
                className="todo-input"
                type="text"
                placeholder="Neues Todo..."
                value={description}
                onChange={(e) => setDescription(e.target.value)}
            />

            <button className="todo-add-button" onClick={handleSubmit}>
                Hinzufügen
            </button>
        </div>
    );
}