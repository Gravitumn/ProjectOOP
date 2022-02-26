import React from "react";
import { useNavigate, useParams } from "react-router-dom";


function Home() {
    let nav = useNavigate()
    return (
        <div class="p-6 max-w-sm mx-auto bg-white rounded-xl shadow-lg flex items-center space-x-4">
            <div class="shrink-0">
                <img class="h-12 w-12" src="/img/logo.svg" alt="ChitChat Logo"/>
            </div>
            <div>
                <div class="text-xl font-medium text-black">ChitChat</div>
                <p class="text-slate-500">Test</p>
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