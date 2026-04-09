import "./IdeasPage.css";

type Props = {
    onAddTodo: (description: string) => void;
};

export default function IdeasPage({ onAddTodo }: Props) {
    const ideas = [
        "Schreibtisch aufräumen",
        "10 Minuten spazieren gehen",
        "Ein Glas Wasser trinken",
        "E-Mails sortieren",
        "Kurze Dehnpause machen",
        "Etwas Neues lernen",
    ];

    return (
        <div className="page-container">
            <h1 className="page-title">Aufgabenvorschläge</h1>
            <p className="page-subtitle">
                Falls dir gerade die Inspiration fehlt, findest du hier ein paar sinnvolle Ideen.
            </p>

            <div className="ideas-list">
                {ideas.map((idea, index) => (
                    <div className="idea-card" key={index}>
                        <span className="idea-text">{idea}</span>
                        <button className="idea-button" onClick={() => onAddTodo(idea)}>
                            Als Todo anlegen
                        </button>
                    </div>
                ))}
            </div>
        </div>
    );
}