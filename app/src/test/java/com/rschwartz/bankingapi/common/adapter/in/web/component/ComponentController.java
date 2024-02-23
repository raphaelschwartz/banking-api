package com.rschwartz.bankingapi.common.adapter.in.web.component;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public abstract class ComponentController {

  public HttpHeaders getHttpHeaders() {

    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    return headers;
  }

}
