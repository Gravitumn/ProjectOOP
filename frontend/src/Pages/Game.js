import React from "react";
import { useNavigate } from 'react-router-dom'
import Pauz from './Components/images/pauz.png'


function Game() {
    let nav = useNavigate()

    return (
        <div >
            {/* game bar */}
            <div className="bg-gray-800 flex mx-auto flex-row justify-around text-slate-300">
                {/* pause */}
                <div onClick={() => { }}>
                    <img src={Pauz} alt='Pause' width='40px' height='40px'/>
                </div>
                {/* virus left */}
                <div>
                    10/10
                </div>
                {/*wave counter*/}
                <div>
                    Wave: 3/10
                </div>
                {/*funds*/}
                <div>
                    22222
                </div>
                {/*lives*/}
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