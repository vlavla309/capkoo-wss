package com.capkoo.wss.dto;

import io.vertx.core.json.Json;

public class Message {
  private MessageType type;
  private String sender;
  private String message;


  public Message(MessageType type, String sender, String message) {
    this.type = type;
    this.sender = sender;
    this.message = message;
  }


  public MessageType getType() {
    return type;
  }

  public void setType(MessageType type) {
    this.type = type;
  }

  public String getSender() {
    return sender;
  }

  public void setSender(String sender) {
    this.sender = sender;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }


  public String toJson() {
    return Json.encode(this);
  }


  @Override
  public String toString() {
    return "Message{" +
      "type=" + type +
      ", sender='" + sender + '\'' +
      ", message='" + message + '\'' +
      '}';
  }
}
