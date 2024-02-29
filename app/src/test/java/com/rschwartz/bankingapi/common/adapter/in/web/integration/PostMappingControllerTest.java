package com.rschwartz.bankingapi.common.adapter.in.web.integration;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public abstract class PostMappingControllerTest extends ControllerTest {

  public MockHttpServletRequestBuilder post(final String uri) {

    return MockMvcRequestBuilders
        .post(uri)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .characterEncoding(CHARACTER_ENCODING);
  }

  public MockHttpServletRequestBuilder invalidBody(final String uri) {

    return MockMvcRequestBuilders
        .post(uri)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .characterEncoding(CHARACTER_ENCODING)
        .content("{");
  }

  public MockHttpServletRequestBuilder post(final String uri, final Object content) {

    return MockMvcRequestBuilders
        .post(uri)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .characterEncoding(CHARACTER_ENCODING)
        .content(objectToJson(content));
  }

}
