import React from "react";
import { useNavigate } from 'react-router-dom'


function Game() {
    let nav = useNavigate()

    return (
        <div >
            {/* game bar */}
            <div className="bg-gray-800 flex mx-auto flex-row justify-around text-slate-300">
                {/* pause */}
                <div>
                    pauz
                </div>
                {/* virus left */}
                <div>
                    10/10
                </div>
                <div>
                    Wave: 3/10
                </div>
                <div>
                    22222
                </div>
                <div>
                    4/5
                </div>
            </div>

            
            Game Starto

            <button onClick={() => nav('/')}>Back</button>
        </div>
    )
}

export default Game