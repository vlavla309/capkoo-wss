package com.capkoo.wss.dto;

import io.vertx.core.json.Json;

import java.util.Map;

public class Message {
  private MessageType type;
  private String sender;
  private Map data;


  public Message() {}

  public Message(MessageType type, String sender, Map data) {
    this.type = type;
    this.sender = sender;
    this.data = data;
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

  public Map getData() {
    return data;
  }

  public void setData(Map data) {
    this.data = data;
  }


  public String toJson() {
    return Json.encode(this);
  }


  @Override
  public String toString() {
    return "Message{" +
      "type=" + type +
      ", sender='" + sender + '\'' +
      ", data='" + data + '\'' +
      '}';
  }
}
