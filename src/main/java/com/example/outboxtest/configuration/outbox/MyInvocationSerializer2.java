package com.example.outboxtest.configuration.outbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gruelbox.transactionoutbox.DefaultInvocationSerializer;
import com.gruelbox.transactionoutbox.Invocation;
import com.gruelbox.transactionoutbox.InvocationSerializer;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.Set;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class MyInvocationSerializer2 implements InvocationSerializer {

  private Gson gson;

  @Autowired
  public MyInvocationSerializer2(final Gson gson) {
    this.gson = gson;
  }

  //@Builder
  //MyInvocationSerializer2(Set<Class<?>> serializableTypes, Integer version) {
  //  this.gson =
  //      new GsonBuilder()
  //          //.registerTypeAdapter(
  //          //    Invocation.class,
  //          //    new InvocationJsonSerializer(
  //          //        serializableTypes == null ? Set.of() : serializableTypes,
  //          //        version == null ? 2 : version))
  //          .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC)
  //          .create();
  //}

  @Override
  public void serializeInvocation(Invocation invocation, Writer writer) {
    try {
      gson.toJson(invocation, writer);
    } catch (Exception e) {
      throw new IllegalArgumentException("Cannot serialize " + invocation, e);
    }
  }

  @Override
  public Invocation deserializeInvocation(Reader reader) {
    return gson.fromJson(reader, Invocation.class);
  }
}
