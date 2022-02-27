import React from "react";
import { useNavigate } from 'react-router-dom'


function Game() {
    let nav = useNavigate()

    return (
        <div>
            Game Starto
            <button onClick={() => nav('/')}>Back</button>
        </div>
    )
}

export default Game