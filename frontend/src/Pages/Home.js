import React, { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

//icons to be imported
import { ReactComponent as Play } from "./Components/images/play.svg"
import { ReactComponent as PlayIdle } from "./Components/images/play_mouzNotOn.svg"


function Home() {
    //universal warp lol
    let nav = useNavigate()

    const [mouzIn, setMouzIn] = useState(false)

    //play button
    const icon = () => {
        if (mouzIn) return (<Play />)
        return (<PlayIdle />)
    }


    //how to play section - injected in the form of a fn
    const howToPlay = () => {
        return (
            <div className="p-6 mx-4 bg-gray-200">
                {/* header */}
                <div className="text-xl">
                    How to play
                </div>

                {/* goal */}
                <div className="mt-6">
                    Player's goal is to survive a designated number of rounds swarmed by viruses to win.
                </div>

                {/* shows controls */}
                <div className="">
                    Click to deploy, but remember, you have limited funds. Always plan to spend your fund carefully

                    Any antivirus defeated by a virus will turn into one.

                    If you are out of funds and have no antiviruses on the field, it will result in game over.

                    Once a wave is cleared, you gain more funds to deploy more antiviruses.
                </div>
            </div>
        )
    }


    return (
        <div classname='flex-col justify-center'>
            <div class="p-6 max-w-screen mx-4 bg-white shadow-lg items-center">

                <div className="flex flex-row justify-between">
                    <div className="rounded-md" onClick={() => { nav('/game') }} onMouseOver={() => { setMouzIn(true) }} onMouseLeave={() => { setMouzIn(false) }}>
                        <a href=''>
                            {icon()}
                        </a>
                    </div>
                    <div className='align-right'>
                        <div class="text-xl font-medium text-black">Game name here</div>
                        <p class="text-slate-500">Can you protect your body?</p>
                    </div>
                    

                </div>
                {/* 404 test - debug only */}
                <div onClick={() => { nav('*') }}>
                    404 test
                </div>
            </div>
            {howToPlay()}
            <div classname="text-center">
                @2022 by abc team
            </div>
        </div>

    )
}

export default Home