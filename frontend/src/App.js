import './App.css';
import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom"

//pages to be imported
import Game from './Pages/Game'
import About from './Pages/About'
import Home from './Pages/Home'
import Four04 from './Pages/Four04'


function App() {

  return (
    <div className='bg-gray-500 min-h-screen items-center'>
      <Router>
        <Routes>
          <Route exact path='/' element={<Home />} />
          <Route exact path='/about' element={<About />} />
          <Route exact path='/game' element={<Game />} />
          <Route path='*' element={<Four04 />} />
        </Routes>
      </Router>
    </div>
  );

}

export default App;