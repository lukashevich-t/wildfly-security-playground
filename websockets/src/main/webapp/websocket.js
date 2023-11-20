window.onload = init;
const WS_URL = "ws://localhost:28080/websockets/actions";
let socket;

initSocket();

function initSocket() {
    socket = new WebSocket(WS_URL);
    socket.onmessage = onMessage;
    socket.onopen = event => {
        console.log("onopen");
        console.log(event);
    };
    socket.onclose = event => {
        console.log("onclose");
        console.log(event);
    };
    socket.onerror = event => {
        console.log("onerror");
        console.log(event);
    };
}

setInterval(function () {
    console.log("ping. ready state = " + socket.readyState);
    if (socket.readyState === 1) {
        socket.send(JSON.stringify({action: "ping"}));
        return;
    }
    if (socket.readyState !== 0) {
        console.log("reconnecting to websocket")
        initSocket();
    }
}, 3000);

function onMessage(event) {
    console.log("onMessage")
    console.log(event)
    let device = JSON.parse(event.data);
    if (device.action === "add") {
        printDeviceElement(device);
    }
    if (device.action === "remove") {
        document.getElementById(device.id).remove();
        //device.parentNode.removeChild(device);
    }
    if (device.action === "toggle") {
        let node = document.getElementById(device.id);
        let statusText = node.children[2];
        if (device.status === "On") {
            statusText.innerHTML = "Status: " + device.status + " (<a href=\"#\" OnClick=toggleDevice(" + device.id + ")>Turn off</a>)";
        } else if (device.status === "Off") {
            statusText.innerHTML = "Status: " + device.status + " (<a href=\"#\" OnClick=toggleDevice(" + device.id + ")>Turn on</a>)";
        }
    }
}

function addDevice(name, type, description) {
    let deviceAction = {
        action: "add",
        name: name,
        type: type,
        description: description
    };
    try {
        socket.send(JSON.stringify(deviceAction));
    } catch (e) {
        console.error(e);
        console.error(typeof e);
    }
}

function removeDevice(element) {
    let DeviceAction = {
        action: "remove",
        id: element
    };
    socket.send(JSON.stringify(DeviceAction));
}

function toggleDevice(element) {
    console.log(`toggle device ${element}`);
    let DeviceAction = {
        action: "toggle",
        id: element
    };
    socket.send(JSON.stringify(DeviceAction));
}

function printDeviceElement(device) {
    let content = document.getElementById("content");

    let deviceDiv = document.createElement("div");
    deviceDiv.setAttribute("id", device.id);
    deviceDiv.setAttribute("class", "device " + device.type);
    content.appendChild(deviceDiv);

    let deviceName = document.createElement("span");
    deviceName.setAttribute("class", "deviceName");
    deviceName.innerHTML = device.name;
    deviceDiv.appendChild(deviceName);

    let deviceType = document.createElement("span");
    deviceType.innerHTML = "<b>Type:</b> " + device.type;
    deviceDiv.appendChild(deviceType);

    let deviceStatus = document.createElement("span");
    if (device.status === "On") {
        deviceStatus.innerHTML = "<b>Status:</b> " + device.status + " (<a href=\"#\" OnClick=toggleDevice(" + device.id + ")>Turn off</a>)";
    } else if (device.status === "Off") {
        deviceStatus.innerHTML = "<b>Status:</b> " + device.status + " (<a href=\"#\" OnClick=toggleDevice(" + device.id + ")>Turn on</a>)";
        //deviceDiv.setAttribute("class", "device off");
    }
    deviceDiv.appendChild(deviceStatus);

    let deviceDescription = document.createElement("span");
    deviceDescription.innerHTML = "<b>Comments:</b> " + device.description;
    deviceDiv.appendChild(deviceDescription);

    let removeDevice = document.createElement("span");
    removeDevice.setAttribute("class", "removeDevice");
    removeDevice.innerHTML = "<a href=\"#\" OnClick=removeDevice(" + device.id + ")>Remove device</a>";
    deviceDiv.appendChild(removeDevice);
}

function showForm() {
    document.getElementById("addDeviceForm").style.display = '';
}

function hideForm() {
    document.getElementById("addDeviceForm").style.display = "none";
}

function formSubmit() {
    let form = document.getElementById("addDeviceForm");
    let name = form.elements["device_name"].value;
    let type = form.elements["device_type"].value;
    let description = form.elements["device_description"].value;
    hideForm();
    document.getElementById("addDeviceForm").reset();
    addDevice(name, type, description);
}

function init() {
    hideForm();
}
