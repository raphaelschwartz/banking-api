package com.rschwartz.bankingapi.common.adapter.in.web.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

abstract class ControllerTest {

  protected static final String CHARACTER_ENCODING = "utf-8";
  private static final String STRING_EMPTY = "";

  protected String objectToJson(final Object content) {

    final ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    try {

      return mapper.writeValueAsString(content);

    } catch (final JsonProcessingException ex) {

      ex.printStackTrace();

      return STRING_EMPTY;
    }

  }

}
