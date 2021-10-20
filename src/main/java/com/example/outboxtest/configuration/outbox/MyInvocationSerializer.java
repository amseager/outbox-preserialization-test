package com.example.outboxtest.configuration.outbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gruelbox.transactionoutbox.Invocation;
import com.gruelbox.transactionoutbox.InvocationSerializer;
import java.io.Reader;
import java.io.Writer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyInvocationSerializer implements InvocationSerializer {

  private final ObjectMapper objectMapper;


  @SneakyThrows
  @Override
  public void serializeInvocation(final Invocation invocation, final Writer writer) {
    objectMapper.writeValue(writer, invocation);
  }

  @SneakyThrows
  @Override
  public Invocation deserializeInvocation(final Reader reader) {
    return objectMapper.readValue(reader, Invocation.class);
  }
}
