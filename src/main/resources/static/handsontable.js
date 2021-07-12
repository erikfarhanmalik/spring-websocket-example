const urlParams = new URLSearchParams(window.location.search);
const id = urlParams.get('id');
const senderKey = Math.random().toString(36).substring(10);
const container = document.getElementById('example');
var stompClient = null;

const hot = new Handsontable(container, {
    rowHeaders: true,
    colHeaders: true,
    height: 'auto',
    licenseKey: 'non-commercial-and-evaluation', // for non-commercial use only
    afterChange: function (change, source) {
        if (source === 'loadData' || source === 'doNotTriggerAfterChange') {
            return;
        }
        syncChanges(change);
    }
});

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/setCells/'+id, function (setCellsMessage) {
            let message = JSON.parse(setCellsMessage.body);
            console.log('Receiving data');
            console.log(setCellsMessage);
            if(message.sender !== senderKey){
                message.changes.forEach(change=>{
                    hot.setDataAtCell(change[0], change[1], change[3], 'doNotTriggerAfterChange');
                });
            }
        });
    });
}


function syncChanges(changes) {
    console.log('Sending message');
    let data = {
        "id": id,
        "sender": senderKey,
        "changes": changes
    }
    console.log(data);
    stompClient.send("/app/setCells/"+id, {}, JSON.stringify(data));
}

connect();