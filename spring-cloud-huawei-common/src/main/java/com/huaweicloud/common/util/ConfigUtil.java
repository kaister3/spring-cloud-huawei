package com.huaweicloud.common.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import com.netflix.config.ConcurrentCompositeConfiguration;
import com.netflix.config.DynamicPropertyFactory;

public class ConfigUtil {
  public static final Logger LOGGER = LoggerFactory.getLogger(ConfigUtil.class);

  private static final String IS_PRINT_URL = "servicecomb.config.log.verbose";

  private static Map<String, Object> localConfig = new HashMap<>();

  private static final Map<String, Map<String, Object>> EXTRA_CONFIG_MAP = new LinkedHashMap<>();

  private ConfigUtil() {
  }

  public static void setConfig(Map<String, Object> config) {
    localConfig = config;
  }

  public static void addConfig(String key, Object value) {
    localConfig.put(key, value);
  }

  public static Object getProperty(String key) {
    Object config = DynamicPropertyFactory.getBackingConfigurationSource();
    return getProperty(config, key);
  }

  public static Object getProperty(Object config, String key) {
    if (null != config && Configuration.class.isInstance(config)) {
      Configuration configuration = (Configuration) config;
      return configuration.getProperty(key);
    }
    return null;
  }

  public static List<String> getStringList(@NonNull Configuration config, @NonNull String key) {
    return config.getList(key).stream()
        .map(v -> Objects.toString(v, null))
        .collect(Collectors.toList());
  }

  public static ConcurrentCompositeConfiguration createLocalConfig() {

  }
}
