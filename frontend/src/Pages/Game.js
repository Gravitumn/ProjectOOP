import React, { useState } from "react";
import { useNavigate } from 'react-router-dom'
import Pauz from './Components/images/pauz.png'

//board to be imported
import GamePad from "./GamePad";




function Game() {
    let nav = useNavigate()

    const [paused, setPaused] = useState(false)

    const page = () => {
        if (paused) {
            return (
                <div className="min-h-screen items-center">
                    <button className="bg-green-200" onClick={() => {setPaused=(false)}}>
                        Continue
                    </button>
                    <button className="bg-red-200">
                        Quit
                    </button>
                </div>
            )
        }
        return (
            <div>
                {/* game bar */}
                <div className="bg-gray-800 flex mx-auto flex-row justify-around text-slate-300 my-auto items-center">
                    {/* pause */}
                    <div onClick={() => { }}>
                        <button onClick={() => { setPaused(true) }}>
                            <img src={Pauz} alt='Pause' width='40px' height='40px' />
                        </button>

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


                <GamePad />

                <button onClick={() => nav('/')}>Back</button>
            </div>
        )
    }


    return (
        <div>
            {page()}
        </div>
    )
}

export default Game