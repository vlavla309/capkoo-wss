package com.capkoo.wss.domain;

import com.capkoo.wss.dto.Message;
import io.vertx.core.http.ServerWebSocket;

import java.util.HashMap;

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


  public ChatRoom addChatClient(ChatClient chatClient) {
    if(! this.isExistClient(chatClient.getSocket().textHandlerID())) {
      this.chatClients.put(chatClient.getSocket().textHandlerID(), chatClient);
    }

    chatClient.setChatRoom(this);

    return this;
  }



  public boolean isExistClient(String id){
    return this.chatClients.containsKey(id);
  }


  public ChatClient removeChatClient(ChatClient client) {
    return client;
  }


  public boolean sendMessage(Message message, ChatClient sender) {
    this.chatClients.forEach((id, chatClient) -> {
      boolean res = chatClient.sendMessage(message);

      if(! res) {
        this.removeChatClient(chatClient);
      }
    });

    return true;
  }

  @Override
  public String toString() {
    return "ChatRoom{" +
      "name='" + name + '\'' +
      ", chatClients=" + chatClients +
      '}';
  }
}
