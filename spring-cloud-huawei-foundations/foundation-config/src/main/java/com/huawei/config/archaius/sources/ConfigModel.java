package com.huawei.config.archaius.sources;

import java.net.URL;
import java.util.Map;

/**
 * Config的模型
 */
public class ConfigModel {

  private URL url;

  private int order;

  private Map<String, Object> config;

  public URL getUrl() {
    return url;
  }

  public void setUrl(URL url) {
    this.url = url;
  }

  public int getOrder() {
    return order;
  }

  public void setOrder(int order) {
    this.order = order;
  }

  public Map<String, Object> getConfig() {
    return config;
  }

  public void setConfig(Map<String, Object> config) {
    this.config = config;
  }

  @Override
  public String toString() {
    return url == null ? "" : url.toString();
  }
}
