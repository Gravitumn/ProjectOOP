import React, {useState} from "react";
import { useNavigate, useParams } from "react-router-dom";

//icons to be imported
import { ReactComponent as Play } from "./Components/images/play.svg"
import { ReactComponent as PlayIdle } from "./Components/images/play_mouzNotOn.svg"


function Home() {
    let nav = useNavigate()

    const [mouzIn, setMouzIn] = useState(false)

    const icon = () => {
        if(mouzIn) return(<Play />)
        return(<PlayIdle />)
    }


    return (
        <div>
            <div class="p-6 max-w-screen mx-4 bg-white shadow-lg flex-col items-center space-x-4">
                <div>
                    <div class="text-xl font-medium text-black">Someapp</div>
                    <p class="text-slate-500">Test</p>
                </div>

                <div className="flex-row">
                    <div className="rounded-md" onClick={() => { nav('/game') }} onMouseOver={() => {setMouzIn(true)}} onMouseLeave={() => {setMouzIn(false)}}>
                        <a href=''>
                            {icon()}
                        </a>
                    </div>

                    <div onClick={() => { nav('*') }}>
                        404 test
                    </div>
                </div>

            </div>
            <div classname="text-center">
                @2022 by abc team
            </div>
        </div>

    )
}

export default Home

{/* <div class="p-6 max-w-sm mx-auto bg-white rounded-xl shadow-lg flex items-center space-x-4">
                <div class="shrink-0">
                    <img class="h-12 w-12" src="/img/logo.svg" alt="ChitChat Logo"/>
                </div>
                <div>
                    <div class="text-xl font-medium text-black">ChitChat</div>
                    <p class="text-slate-500">You have a new message!</p>
                </div>
            </div> */}

{/* <div className="">
            
            <div>
                <button onClick={() => { nav("/about") }}>About</button>
                <button onClick={() => { nav("/game") }}>Play</button>
            </div>

        </div> */}