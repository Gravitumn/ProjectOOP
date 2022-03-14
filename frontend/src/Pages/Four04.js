import React from "react";
import { useNavigate } from 'react-router-dom'
import { ReactComponent as SadEmoji } from './Components/images/d_.svg'

function Four04() {
    let nav = useNavigate()

    const baccToHome = () => {
        return (
            nav('/')
        )
    }

    const home = () => {
        const timer = 5000
        setTimeout(baccToHome, timer)
        clearTimeout(timer)
    }



    return (
        <div className="min-h-screen items-center">
            <div className="flex-col p-6 max-w-sm sm:mx-auto -px-4 bg-white shadow-lg flex items-center content-center space-x-4">
                <div className="text-3xl font-bold italic underline-offset-2 mb-4">404: not found</div>
                <div>
                    <SadEmoji />
                </div> {/*images*/}
                <div>You will be redirected soon.</div>
                <div classname='bg-gray-400 shadow-lg' onClick={() => { baccToHome() }}>
                    Or click here to go back
                </div>

                {home()}    {/*redirect fn here*/}
            </div>
            <div className="text-center">
                @2022 by abc team
            </div>
        </div>

    )
}

export default Four04