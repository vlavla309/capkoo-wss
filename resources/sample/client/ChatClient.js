
class ChatClient {
  constructor(config = {}) {
    console.log(config);
    this.config = config;
    this.config.host = config.host;
    this.config.roomName = config.roomName;
    this.config.clientName = config.clientName;
    this.config.onConnect = config.onConnect || function(){};
    this.config.onReceive = config.onReceive || function(){};
    this.config.debug = config.debug || false;
  }

   connect(host) {
    this.config.host = host || this.config.host;

    this.socket = new WebSocket(this.config.host);

    this.socket.onopen = this.config.onConnect;
    this.socket.onmessage = this.receiveMessage;
    this.socket.chatClient = this;
  }


  makeMessage(type, data) {
    let message = {
      'type':type,
      'data':data,
      'sender': this.config.clientName
    }

    return JSON.stringify(message);
  }


  requestJoinChat(roomName, clientName) {
    this.config.roomName = this.config.roomName || roomName;
    this.config.clientName = this.config.clientName || clientName;


    let data = {
      "roomName" : this.config.roomName,
      "clientName" : this.config.clientName
    }

    let message = this.makeMessage('JOIN', data);

    this.socket.send(message);
  }


  responseJoinChat(message) {
    console.log("responseJoinChat()...");
    let data = message.data;
    
    this.config.roomName = data.roomName;
    this.config.clientName = data.clientName;
    this.id = data.id;
  }



  receiveMessage(received) {
    let data = received.data;
    let message = JSON.parse(data);
    // console.log(message);
    
    switch(message.type) {
      case 'JOIN':
        console.log(this);
        this.chatClient.responseJoinChat(message);
        break;
      case 'CLIENT':
        this.chatClient.config.onReceive(message);
        break;  
    }
  }



  sendClientMessage(text) {
    const message = this.makeMessage('CLIENT', {'text':text});
    this.socket.send(message);
  }
}
