package com.rschwartz.bankingapi.common.adapter.in.web.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public abstract class GetMappingControllerTest extends ControllerTest {

  public MockHttpServletRequestBuilder get(final String uri) {

    return MockMvcRequestBuilders
        .get(uri)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .characterEncoding(CHARACTER_ENCODING);
  }

  public MockHttpServletRequestBuilder get(final String uri, final Object params) {

    return MockMvcRequestBuilders
        .get(uri)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .characterEncoding(CHARACTER_ENCODING)
        .queryParams(objectToQueryParams(params));
  }

  private MultiValueMap<String, String> objectToQueryParams(final Object params) {

    final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();

    final Map<String, String> maps = new ObjectMapper()
        .convertValue(params, new TypeReference<>() {
        });

    queryParams.setAll(maps);

    return queryParams;
  }

}
