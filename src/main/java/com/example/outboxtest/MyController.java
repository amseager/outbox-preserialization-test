package com.example.outboxtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {


  @Autowired
  private OutboxService outboxService;

  @GetMapping
  public String test() {

    outboxService.sendDto();

    return "asd";
  }
}
