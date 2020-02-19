package com.capkoo.wss;

import com.capkoo.wss.handler.WsHandler;
import com.capkoo.wss.manager.ChatManager;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.WebSocket;

public class MainVerticle extends AbstractVerticle {
  private ChatManager chatManager;

  @Override
  public void start() throws Exception {

    HttpServer server = vertx.createHttpServer();
    server.webSocketHandler(WsHandler::handle);
    server.listen(8080);
  }
}
