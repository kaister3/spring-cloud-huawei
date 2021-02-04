package com.huaweicloud.common.util;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

public class YAMLUtil {
  private static final Yaml SAFE_PARSER = new Yaml(new SafeConstructor());
}
