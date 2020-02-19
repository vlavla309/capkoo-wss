package com.capkoo.wss.manager;

import io.vertx.core.http.ServerWebSocket;

import java.util.HashMap;
import java.util.HashSet;

public class ChatRoom {
  private String name;
  private HashMap<String, ChatClient> chatClients;

  public ChatRoom(String name) {
    this.name = name;
    this.chatClients = new HashMap<>();
  }

  public HashMap<String, ChatClient> getChatClients() {
    return chatClients;
  }

  public void setChatClients(HashMap chatClients) {
    this.chatClients = chatClients;
  }


  public ChatRoom addChatClient(ServerWebSocket socket) {
    if(! this.isExistClient(socket.textHandlerID())) {
      this.chatClients.put(socket.textHandlerID(), new ChatClient(socket));
    }

    return this;
  }


  public boolean isExistClient(String id){
    return this.chatClients.containsKey(id);
  }


  public ChatClient getClientByHandlerId(String id) {
    return this.chatClients.get(id);
  }


  public ChatClient removeChatClient(ChatClient client) {
    return client;
  }


  public void sendMessage(String message, ServerWebSocket senderSocket) {
    this.chatClients.forEach((id, chatClient) -> {
        chatClient.sendMessage(message);
    });
  }

  @Override
  public String toString() {
    return "ChatRoom{" +
      "name='" + name + '\'' +
      ", chatClients=" + chatClients +
      '}';
  }
}
