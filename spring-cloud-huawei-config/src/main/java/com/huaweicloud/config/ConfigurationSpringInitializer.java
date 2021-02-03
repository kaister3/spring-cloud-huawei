package com.huaweicloud.config;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;


/*
 * as of 5.2; use org.springframework.context.support.PropertySourcesPlaceholderConfigurer
 * instead which is more flexible through taking advantage of the Environment and PropertySource mechanisms.
 */
public class ConfigurationSpringInitializer extends PropertySourcesPlaceholderConfigurer {
  private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationSpringInitializer.class);

  public static final String CONFIG_NAME = "config";

  public static final String MICROSERVICE_PROPERTY_SOURCE_NAME = "microservice.yaml";

  public static final String MAPPING_PROPERTY_SOURCE_NAME = "mapping.yaml";

  // 构造器
  public ConfigurationSpringInitializer() {
    setOrder(Ordered.LOWEST_PRECEDENCE / 2);
    setIgnoreUnresolvablePlaceholders(true);
  }

  @Override
  public void setEnvironment(Environment environment) {
    ///
  }

  private void syncFromSpring(Environment environment) {
    String environmentName = generateNameForEnvironment(environment);
    LOGGER.info("Environment received, will get configurations from [{}].", environmentName);


  }

  /**
   * try to get a name for identifying the environment.
   * @param environment the target which the name is generated for.
   * @return the generated name for the environment.
   */
  private String generateNameForEnvironment(Environment environment) {
    String environmentName = environment.getProperty("spring.config.name");
    if (StringUtils.isNotEmpty(environmentName)) {
      return environmentName;
    }

    environmentName = environment.getProperty("spring.application.name");
    if (StringUtils.isNotEmpty(environmentName)) {
      return environmentName;
    }

    return environment.getClass().getName() + "@" + environment.hashCode();
  }

  private static void addMappingToSpring(Environment environment) {
    if (!(environment instanceof ConfigurableEnvironment)) {
      return;
    }

    MutablePropertySources propertySources = ((ConfigurableEnvironment) environment).getPropertySources();
    if (propertySources.contains(MAPPING_PROPERTY_SOURCE_NAME)) {
      // 已读取mapping.yaml
      return;
    }

    Map<String, Object> mappings =
  }
}
