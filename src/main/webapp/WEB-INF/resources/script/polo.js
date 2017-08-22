var url = 'ws://' + window.location.host + '/websocket/macro';
var sock = new WebSocket(url);

sock.onopen = function () {
    console.log("Opening");
    sayMacro();
};

sock.onmessage = function (e) {
    console.log('Received message: ', e.data);
    setTimeout(function () {
        sayMacro();
    }, 2000)
};

sock.onclose = function () {
    console.log('Closing');
};

function sayMacro() {
    console.log('Sending Marco!');
    sock.send("Marco!");
}