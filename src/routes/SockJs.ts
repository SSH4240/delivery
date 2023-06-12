import * as SockJs from "sockjs-client/dist/sockjs";
import {Client} from "@stomp/stompjs";
import {WebSocket} from "ws";
import {URL} from "../env";
import { browser } from '$app/environment';

let TOKEN;
if(browser){
    TOKEN = sessionStorage.getItem('accessToken');
}
export const client = new Client({
    brokerURL: "",
    debug: function (str) {
        console.log(str);
    },
    connectHeaders: {
        'Authorization': "Bearer " + `${TOKEN}`
    },
    reconnectDelay: 5000,
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000
});


// Fallback code
if (typeof WebSocket !== "function") {
    client.webSocketFactory = function () {
        // @ts-ignore
        return new SockJs(`${URL}/webSocket`);
    };
}

client.onStompError = function (frame) {
    console.log("Broker reported error: " + frame.headers["message"]);
    console.log("Additional details: " + frame.body);
};

// export const startSocket = () => {
    client.activate();
// }

// startSocket();
