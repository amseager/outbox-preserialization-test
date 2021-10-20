package com.example.outboxtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gruelbox.transactionoutbox.TransactionOutbox;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OutboxServiceImpl implements OutboxService {

  @Autowired
  private TransactionOutbox transactionOutbox;
  @Autowired
  private ObjectMapper objectMapper;

  @Override
  @Transactional
  public void sendDto() {
    MyDto myDto = new MyDto();
    myDto.setOperationId(UUID.randomUUID());
    myDto.setDate(OffsetDateTime.now());

    transactionOutbox.with()
        .uniqueRequestId(UUID.randomUUID().toString())
        .schedule(OutboxService.class)
        .process(serialize(myDto));
  }

  public void process(final String value) {
    MyDto myDto = deserialize(value, MyDto.class);
    System.out.println();
  }

  @SneakyThrows
  private <T> String serialize(final T value) {
    return objectMapper.writeValueAsString(value);
  }

  @SneakyThrows
  private <T> T deserialize(final String value, final Class<T> type) {
    return objectMapper.readValue(value, type);
  }
}
