package com.capkoo.wss.handler;

import com.capkoo.wss.manager.ChatManager;
import io.vertx.core.http.ServerWebSocket;

public class WsHandler {
  private final static ChatManager chatManager = new ChatManager();

  public static void handle(ServerWebSocket ws) {
    chatManager.welcome(ws);
  }
}
