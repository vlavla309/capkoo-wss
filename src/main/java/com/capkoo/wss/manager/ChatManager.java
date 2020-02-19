package com.capkoo.wss.manager;

import io.grpc.Server;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.http.WebSocket;

import java.util.HashMap;
import java.util.Map;

public class ChatManager {
  private Map chatRooms;

  public ChatManager() {
    this.chatRooms = new HashMap();
  }

  public Map getChatRooms() {
    return chatRooms;
  }

  public void setChatRooms(Map chatRooms) {
    this.chatRooms = chatRooms;
  }


  public ChatRoom getChatRoomBySocket(ServerWebSocket socket) {
    String roomName = socket.path().split("/", 1)[0];
    return this.getChatRoomByName(roomName);
  }


  public ChatRoom getChatRoomByName(String name) {
    ChatRoom chatRoom = (ChatRoom) this.chatRooms.getOrDefault(name, new ChatRoom(name));
    if(! this.chatRooms.containsKey(name)) {
      this.chatRooms.put(name, chatRoom);
    }
    return chatRoom;
  }


  public void addClient(ServerWebSocket socket) {
    String roomName = socket.path().split("/",1)[0];
    if(roomName == null) {
      roomName = "robby";
    }

    ChatRoom chatRoom = this.getChatRoomByName(roomName);
    chatRoom.addChatClient(socket);

  }


  public void recieveMessage(ServerWebSocket ws, Buffer buffer) {
    getChatRoomBySocket(ws).sendMessage(ws.textHandlerID() + ": " + buffer.toString(), ws);
  }
}
