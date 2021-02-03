/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.huaweicloud.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Author GuoYl123
 * @Date 2019/12/16
 **/
@RestController
public class ProviderController {

  @Autowired
  RestTemplate restTemplate;

  @GetMapping("/foo")
  public Foo foo(@RequestParam("id") int id) {
    return new Foo("foo", id, null);
  }

  @GetMapping("/hello")
  public String sayHello(@RequestParam("name") String name) {
    return "spring cloud hello world " + name;
  }

  @GetMapping("/int")
  public int intTest() {
    return 123;
  }

  @GetMapping("/invoke")
  public String invoke() {
    return restTemplate
        .getForObject("http://swagger-consumer/consumer/invoke", String.class);
  }

  @GetMapping("/longCall")
  public String longCall() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    Info info = new Info();
    HttpEntity<Info> request = new HttpEntity<>(info, headers);
    String str1 = "spring cloud call java chassis.\n";
    String javaChssisResult = restTemplate
        .postForObject("http://swagger-consumer/consumer/longCall", request, String.class);
    javaChssisResult = javaChssisResult.replace("\"", "");
    javaChssisResult = javaChssisResult.replace("\\n", "\n");
    String str2 = "spring cloud call go chassis.\n" + restTemplate
        .postForObject("http://GoChassis-Demo/longCall", request, String.class);
    return str1 + javaChssisResult + "\n" + str2 + "\n";
  }

  @PostMapping("/callBack")
  public String callBack(@RequestBody Info info) {
    return "spring cloud : " + info.getVar3().getInfo();
  }
}
