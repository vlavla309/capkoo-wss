package com.capkoo.wss.manager;

import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.http.WebSocket;

public class ChatClient {
  private String name;
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

  public void sendMessage(String message) {
    this.socket.writeTextMessage(message);
  }

  @Override
  public String toString() {
    return "ChatClient{" +
      "name='" + name + '\'' +
      ", socket=" + socket.textHandlerID() +
      '}';
  }
}
