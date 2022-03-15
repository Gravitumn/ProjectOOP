import React from "react";
import { useNavigate } from "react-router-dom"

function About() {
    let nav = useNavigate()

    return (
        <div className="bg-white mx-4 p-6">
            <div className="flex flex-row">
                <div className="text-bold text-2xl mr-4">
                    About us
                </div>
                <button className="" onClick={() => nav('/')}>Back</button>
            </div>
            <div className="my-2">
                <div>
                    Laziest team since 2021 /s
                </div>

            </div>
        </div>
    )
}

export default About