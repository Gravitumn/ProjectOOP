import React, { useState } from "react";


// const [gameBoard, setGameBoard] = useState([])

function GamePad() {

    const board = () => {
        const row = () => {
            const tile = () => {
                console.log("tile added")
                return (
                    <div>
                        welp
                    </div>
                )
            }

            const rowData = []


            for (var i = 0; i < 10; i++) {
                rowData.push(tile())
            }

            console.log(rowData)
            console.log("row added")
            return (
                <div className="flex flex-row">
                    {rowData.map((tile) => (
                        <div>
                            {tile}
                        </div>
                    ))}
                </div>
            )
        }

        const boardData = []

        for (var i = 0; i < 10; i++) {
            boardData.push(row())
        }

        console.log(boardData)
        return (
            <div className="flex flex-col">
                {boardData.map((row) => {
                    <div>
                        {row}
                    </div>
                })}

            </div>
        )
    }

    return (
        <div className="flex flex-col mx-auto py-6">
            <div>kek</div>
            {board()}
        </div>
    )
}


export default GamePad