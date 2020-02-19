class ChatClient {
  constructor(config) {
    this.config.host = config.host;
    this.config.roomName = config.roomName;
    this.config.clientName = config.clientName;
    this.config.onConnect = config.onConnect;
    this.config.debug = config.debug || false;
  }

  get host() {
    this.host
  }

  connect() {
    this.socket = new WebSocket(this.host);
    this.sockec.onopen = onConnect;
  }


  makeMessage(type, data) {
    message.type = type;
    message.data = data;

    return JSON.stringify(message);
  }


  joinRoom(roomName, clientName) {
    this.roomName = this.roomName || roomName;
    this.clientName = this.clientName || clientName;

    data.roomName = this.roomName;
    data.clientName = this.clientName;

    let message = this.makeMessage('JOIN', data);

    this.socket.send(message);
  }



  sendClientMessage(text) {
    message = this.makeMessage('client', text);
    this.socket.send(message);
  }
}
