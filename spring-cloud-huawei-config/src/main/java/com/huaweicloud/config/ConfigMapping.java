package com.huaweicloud.config;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

/**
 * 对应servicecomb的ConfigMapping 工具类
 */
public class ConfigMapping {
  private static Map<String, Object> configMap;

  private static final Logger LOGGR = LoggerFactory.getLogger(ConfigMapping.class);

  static {
    //
  }

  private ConfigMapping() {
  }

  public static String map(String key) {
    // 疑问：为什么不用toString()
    return (String) configMap.get(key);
  }

  public static Map<String, Object> getMapping() {
    return configMap;
  }

  public static Map<String, Object> getConvertedMap(Map<String, Object> oldMap) {
    Map<String, Object> retMap = new LinkedHashMap<>();
    retMap.putAll(oldMap);
    configMap.entrySet().forEach(entry -> putConfigsToRetMap(retMap, entry, oldMap.get(entry.getKey())));
    return retMap;
  }

  public static Map<String, Object> getConvertedMap(Configuration config) {
    Map<String, Object> retMap = new LinkedHashMap<>();
    configMap.entrySet().forEach(entry -> putConfigsToRetMap(retMap, entry, config.getProperty(entry.getKey())));
    return retMap;
  }

  public static Map<String, Object> getConvertedMap(Environment environment) {
    Map<String, Object> retMap = new LinkedHashMap<>();
    configMap.entrySet().forEach(entry -> putConfigsToRetMap(retMap, entry, environment.getProperty(entry.getKey())));
    return retMap;
  }

  private static void putConfigsToRetMap(Map<String, Object> retMap, Map.Entry<String, Object> entry,
        Object configValue) {
    if (configValue != null) {
      if (entry.getValue() instanceof List) {
        List<String> newKeys = (List<String>) entry.getValue();
        newKeys.forEach(newKey -> retMap.put(newKey, configValue));
        return;
      }
      String newKey = (String) entry.getValue();
      retMap.put(newKey, configValue);
    }
  }
}
