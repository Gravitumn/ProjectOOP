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
            //pause page
            return (
                <div className="bg-gray-300 flex items-center min-h-screen flex-col my-auto">
                    <div className="text-3xl font-semibold my-12">
                        Paused
                    </div>
                    <div>
                        <button className="p-2 rounded bg-green-200 mx-4" onClick={() => { setPaused(false) }}>
                            Continue
                        </button>
                        <button className="p-2 rounded bg-red-200 mx-4" onClick={() => { nav('/') }}>
                            Quit
                        </button>
                    </div>
                </div>
            )
        }
        //game page
        return (
            <div className="flex flex-col itmes-center mx-screen">
                {/* game bar */}
                <div className="pt-2 bg-gray-800 flex flex-row justify-around text-slate-300 my-auto items-center">
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

                

                {GamePad()}

            </div>
        )
    }


    return (
        <div className="bg-gray-300">  
            {/* injects code */}
            {page()}
        </div>
    )
}

export default Game