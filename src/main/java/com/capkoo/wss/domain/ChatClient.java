package com.capkoo.wss.domain;

import com.capkoo.wss.dto.Message;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.http.WebSocket;

public class ChatClient {
  private String name;
  private ChatRoom chatRoom;
  private String status;
  private ServerWebSocket socket;

  public ChatClient(ServerWebSocket socket) {
    this.socket = socket;
    this.name = socket.textHandlerID();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ServerWebSocket getSocket() {
    return socket;
  }

  public void setSocket(ServerWebSocket socket) {
    this.socket = socket;
  }

  public String getStatus() {
    return status;
  }

  public ChatRoom getChatRoom() {
    return chatRoom;
  }

  public void setChatRoom(ChatRoom chatRoom) {
    this.chatRoom = chatRoom;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public boolean sendMessage(Message message) {
    if(! this.socket.isClosed()) {
      this.socket.writeTextMessage(message.toJson());
      return true;
    }

    return false;
  }

  @Override
  public String toString() {
    return "ChatClient{" +
      "name='" + name + '\'' +
      ", socket=" + socket.textHandlerID() +
      '}';
  }
}
