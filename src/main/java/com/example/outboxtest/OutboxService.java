package com.example.outboxtest;

public interface OutboxService {

  void sendDto();

  //<T> void send(final String bindingName, final T value);

  void process(String value);
}
