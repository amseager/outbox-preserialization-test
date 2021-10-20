package com.example.outboxtest;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Data;

@Data
public class MyDto {

  private UUID operationId;
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX", timezone = "UTC")
  //private String date;
  private OffsetDateTime date;
  //private LocalDateTime date;
  //private Instant date;

}
