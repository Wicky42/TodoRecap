import { useState } from "react";
import { createTodo } from "../services/todoService";

type Props = {
    onTodoCreated: () => void;
};

export default function TodoInput({ onTodoCreated }: Props) {
    const [description, setDescription] = useState("");

    const handleSubmit = () => {
        if (!description.trim()) return;

        createTodo({
            description: description,
            status: "OPEN"
        })
            .then(() => {
                setDescription("");
                onTodoCreated();
            })
            .catch(err => console.error(err));
    };

    return (
        <div>
            <input
                type="text"
                placeholder="Neues Todo..."
                value={description}
                onChange={(e) => setDescription(e.target.value)}
            />
            <button onClick={handleSubmit}>Hinzufügen</button>
        </div>
    );
}