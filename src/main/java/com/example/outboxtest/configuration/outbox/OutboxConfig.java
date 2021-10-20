package com.example.outboxtest.configuration.outbox;

import com.example.outboxtest.MyDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gruelbox.transactionoutbox.DefaultInvocationSerializer;
import com.gruelbox.transactionoutbox.DefaultPersistor;
import com.gruelbox.transactionoutbox.Dialect;
import com.gruelbox.transactionoutbox.ExecutorSubmitter;
import com.gruelbox.transactionoutbox.SpringInstantiator;
import com.gruelbox.transactionoutbox.SpringTransactionManager;
import com.gruelbox.transactionoutbox.SpringTransactionOutboxConfiguration;
import com.gruelbox.transactionoutbox.TransactionOutbox;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@Import(SpringTransactionOutboxConfiguration.class)
@EnableScheduling
public class OutboxConfig {

  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private MyInvocationSerializer myInvocationSerializer;
  @Autowired
  private MyInvocationSerializer2 myInvocationSerializer2;

  //@Bean
  //public GsonBuilderCustomizer typeAdapterRegistration() {
  //  return builder -> {
  //    builder.excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC);
  //    //builder.registerTypeAdapter(Class.class, new StringTypeAdapter());
  //  };
  //}

  @Bean
  public TransactionOutbox transactionOutbox(final SpringTransactionManager springTransactionManager,
                                             final SpringInstantiator springInstantiator,
                                             final ThreadPoolTaskExecutor outboxTaskExecutor) {
    return TransactionOutbox.builder()
        .instantiator(springInstantiator)
        .transactionManager(springTransactionManager)
        .persistor(DefaultPersistor.builder()
            .dialect(Dialect.POSTGRESQL_9)
            //.serializer(myInvocationSerializer)
            //.serializer(myInvocationSerializer2)
            .serializer(DefaultInvocationSerializer.builder()
                .serializableTypes(Set.of(MyDto.class))
                .build())
            .build())
        .serializeMdc(false)
        .submitter(ExecutorSubmitter.builder()
            .executor(outboxTaskExecutor)
            .build())
        .build();
  }


}
