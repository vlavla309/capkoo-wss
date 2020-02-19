package com.capkoo.wss.handler;

import com.capkoo.wss.manager.ChatManager;
import com.capkoo.wss.manager.ChatRoom;
import io.vertx.core.http.ServerWebSocket;

public class WsHandler {
  private final static ChatManager chatManager = new ChatManager();

  public static void handle(ServerWebSocket ws) {
    chatManager.addClient(ws);
    ws.handler(buffer -> chatManager.recieveMessage(ws, buffer));
  }
}
