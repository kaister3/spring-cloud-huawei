package com.huaweicloud.common.util;

import static com.huaweicloud.common.util.ConfigUtil.LOGGER;

import org.junit.Test;

public class JvmUtilTest {
  @Test
  public void testWarning() {
    LOGGER.warn("\"{}\" is not a valid class.", JvmUtil.class, new Throwable());
  }
}
