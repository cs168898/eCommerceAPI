import { useEffect, useRef } from "react";
import { Client } from "@stomp/stompjs";
import type { IMessage } from "@stomp/stompjs";

export function useWebSocket(
    token: string | null,
    handleMessage: (msg: string) => void
){
    const clientRef = useRef<Client | null>(null);

    useEffect(() => {
        // check if the token exists
        if(!token) {
            alert('The token is expired or does not exist!');
            return;
        };

        const client = new Client({
            brokerURL: "ws://localhost:8080/gs-guide-websocket",
            connectHeaders: {
                Authorization: "Bearer " + token,
            },
            onConnect: () => {
                // once connected, immediately subscribe to the endpoints
                client.subscribe("/topic/greetings", (msg: IMessage) => {
                    handleMessage(JSON.parse(msg.body).content);
                });
            },
            debug: (str) => console.log(str),
        });

        // intiate the connection
        client.activate();
        clientRef.current = client;

        return() => {
            client.deactivate();
        };


    }, [token, handleMessage]);

    return clientRef;
}