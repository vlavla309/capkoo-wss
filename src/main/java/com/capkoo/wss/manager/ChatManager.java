package com.capkoo.wss.manager;

import com.capkoo.wss.domain.ChatClient;
import com.capkoo.wss.domain.ChatRoom;
import com.capkoo.wss.dto.Message;
import com.capkoo.wss.dto.MessageType;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ChatManager {
  private Map chatClients;
  private Map chatRooms;

  public ChatManager() {
    this.chatRooms = new HashMap();
    this.chatClients = new HashMap();
  }


  public Map getChatRooms() {
    return chatRooms;
  }


  public void setChatRooms(Map chatRooms) {
    this.chatRooms = chatRooms;
  }


  public ChatRoom getChatRoomByName(String name) {
    ChatRoom chatRoom = (ChatRoom) this.chatRooms.getOrDefault(name, new ChatRoom(name));
    if(! this.chatRooms.containsKey(name)) {
      this.chatRooms.put(name, chatRoom);
    }

    return chatRoom;
  }


  public ChatClient getClient(ServerWebSocket socket) {
    ChatClient client =  (ChatClient) this.chatClients.getOrDefault(socket.textHandlerID(), new ChatClient(socket));

    if(! this.chatClients.containsKey(socket.textHandlerID())) {
      this.chatClients.put(socket.textHandlerID(), client);
    }

    return client;
  }


  public void welcome (ServerWebSocket socket) {
    ChatClient client = getClient(socket);

    Message welcomeMessage = new Message();
    welcomeMessage.setType(MessageType.WELCOME);
    welcomeMessage.setSender("SYSTEM");

    client.sendMessage(welcomeMessage);

    socket.handler(buffer -> this.recieveMessage(socket, buffer));
  }


  public void recieveMessage(ServerWebSocket ws, Buffer buffer) {
    ChatClient client = this.getClient(ws);
    Message message = this.messageParser(buffer);
    System.out.println(message);

    switch (message.getType()) {
      case CLIENT:
        this.sendMessageToRoom(client, message);
        break;
      case JOIN:
        this.joinClient(client, message);
        break;
      case SYSTEM:
        break;
    }
  }


  public void joinClient(ChatClient client, Message message) {
    try {
      Map data = message.getData();

      client.setName((String) data.get("clientName"));
      ChatRoom chatRoom = this.getChatRoomByName((String) data.get("roomName"));
      chatRoom.addChatClient(client);
      message.getData().put("id", client.getSocket().textHandlerID());

      System.out.println(chatRoom);

      client.sendMessage(message);
    }
    catch (Exception e) {
      System.err.println(e.toString());
    }
  }


  public void sendMessageToRoom(ChatClient client, Message message){
    try {
      System.out.println("sendMessageToRoom()...");

      client.getChatRoom().sendMessage(message, client);
    }
    catch (Exception e){
      System.err.println(e);
    }

  }


  public Message messageParser(Buffer buffer) {
    Map map = new HashMap();
    Message message = new Message();
    try {
      map = Json.decodeValue(buffer, Map.class);
      message.setType( MessageType.valueOf((String) map.get("type")));
      message.setData((Map) map.get("data"));
      message.setSender((String) map.get("sender"));
    }
    catch (Exception e) {
      System.err.println(e.toString());
    }

    return message;
  }
}
