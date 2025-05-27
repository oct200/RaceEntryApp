import React, { useEffect } from 'react';
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';

const socketUrl = 'http://localhost:8080/ws';

function WebSocketClient({ onMessage }) {
  useEffect(() => {
    const socket = new SockJS(socketUrl);
    const stompClient = new Client({
      webSocketFactory: () => socket,
      onConnect: () => {
        console.log("Connected to WebSocket");
        stompClient.subscribe('/topic/updates', (message) => {
          onMessage(message.body); // send to parent
        });
      },
      debug: (str) => console.log(str),
    });

    stompClient.activate();

    return () => {
      stompClient.deactivate();
    };
  }, [onMessage]);

  return null; // no visual element needed
}

export default WebSocketClient;
