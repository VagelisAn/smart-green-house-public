const WebSocket = require('ws');
const server = new WebSocket.Server({
    port: 8081
});
console.log('WebSocket test server running on port 8081');
server.on('connection', ws => {
    console.log('Client connected');
    setInterval(() => {
        const data = {
            type: "TEMPERATURE_ALERT",
            sensor: "DHT22 Bottom",
            value: 40,
            message: "Temperature reached 40°C"
        };
        ws.send(JSON.stringify(data));
    }, 5000);
});