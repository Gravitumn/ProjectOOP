import logo from './logo.svg';
import './App.css';
import React, {Component} from "react";
import { withRouter } from 'react-router-dom'
import { useHistory } from "react-router-dom";



function App() {
  let history = useHistory();

  function handleClick() {
    history.push("/run");
  }

  return (
    <button type="button" onClick={handleClick}>
       
       <p><Link to='/run'>Go home</Link></p>

    </button>
  );

}

export default App;
