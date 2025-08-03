import logo from './logo.svg';
import './App.css';
import WebSocketClient from './webSocketClient';

import React, { useState } from 'react';

export const url = "http://localhost:8080/motociclete/curse";

export async function getAll(){
    const response = await fetch('http://localhost:8080/motociclete/curse');
    if (!response.ok) throw new Error('Network response was not ok');
    const respJson = await response.json();
    return respJson;
}

export async function insertCursa(nrParticipanti, capMotor) {
  try {
    const dataToSend = JSON.stringify({
      numarParticipanti: parseInt(nrParticipanti),
      capMotor: parseInt(capMotor),
    });
    console.log("Sending data:", dataToSend);
      const response = await fetch(url, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: dataToSend
      });
      if(!response.ok)
        throw new Error(response.statusText);
      const data = await response.json();
      return 'Insert successful: ' + JSON.stringify(data);
    } catch (error) {
      return 'Insert error: ' + error.message;
    }
}

export async function updateCursa(id,nrParticipanti,capMotor) {
  try {
      const response = await fetch(`${url}/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          id: parseInt(id),
          numarParticipanti: parseInt(nrParticipanti),
          capMotor: parseInt(capMotor),
        }),
      });

      const result = await response.text();
      return 'Update: ' + result;
    } catch (error) {
      return 'Update error: ' + error.message;
    }
  };


export async function deleteCursa(id) {
  try {
      const response = await fetch(`${url}/${id}`, {
        method: 'DELETE',
      });
      if(!response.ok)
        throw new Error(response.statusText);
      const result = await response.text();
      return 'Delete: ' + result;
  }catch (error) {
      return 'Delete error: ' + error.message;    
  } 
};


function App() {
  // State for each textbox
  const [input1, setInput1] = useState('');
  const [input2, setInput2] = useState('');
  const [input3, setInput3] = useState('');

  // State for the label
  const [labelText, setLabelText] = useState('This is the label');

  // Example button actions
  const handleButtonInsert = async () => {
    const raspuns = await insertCursa(input2,input3);
    setLabelText(raspuns);
  };

  const handleButtonUpdate = async () => {
    const raspuns = await updateCursa(input1,input2,input3);
    setLabelText(raspuns);
  };

  const handleButtonDelete = async () => {
    const raspuns = await deleteCursa(input1);
    setLabelText(raspuns);
  };

  const handleButtonGetAll = async () => {
    try {
    const data = await getAll();
    setLabelText(JSON.stringify(data));
  } catch (error) {
    setLabelText("Fetch error: " + error.message);
  }
  };

  

  return (
    <div style={{ padding: '20px', fontFamily: 'Arial' }}>

    <WebSocketClient onMessage={(msg) => {
      console.log('WebSocket update:', msg);
      alert(`Live update: ${msg}`);
    }} />

      <input
        type="text"
        value={input1}
        onChange={(e) => setInput1(e.target.value)}
        placeholder="ID"
      /><br /><br />

      <input
        type="text"
        value={input2}
        onChange={(e) => setInput2(e.target.value)}
        placeholder="Numar participanti"
      /><br /><br />

      <input
        type="text"
        value={input3}
        onChange={(e) => setInput3(e.target.value)}
        placeholder="Capacitate motor"
      /><br /><br />

      <button onClick={handleButtonInsert}>Insert</button>
      <button onClick={handleButtonUpdate}>Update</button>
      <button onClick={handleButtonDelete}>Delete</button>
      <button onClick={handleButtonGetAll}>Show All</button>
      
      <h2>{labelText}</h2>
    </div>
  );
}
export default App;
