import React from "react";
import { Route } from 'react-router-dom'
import { useNavigate } from 'react-router-dom'

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
        <div className="p-6 max-w-sm mx-auto bg-white rounded-xl shadow-lg flex items-center space-x-4">
            <div className="text-center">404 not found</div>
            <div>You will be redirected soon.</div>
            {home()}
        </div>
    )
}

export default Four04