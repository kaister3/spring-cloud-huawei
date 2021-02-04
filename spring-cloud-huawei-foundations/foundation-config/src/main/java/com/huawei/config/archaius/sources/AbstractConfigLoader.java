package com.huawei.config.archaius.sources;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractConfigLoader {
  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractConfigLoader.class);

  private static final  String ORDER_KEY = "servicecomb-config-order";

  protected final List<ConfigModel> configModels = new ArrayList<>();

  public final List<ConfigModel> getConfigModels() {
    return configModels;
  }

  public void load(String resourceName) throws IOException {
    //
  }

  protected void loadFromClassPath(String resourceName) throws IOException {
    List<URL> urlList = findURL
  }

  protected List<URL> findURLFromClassPath(String resourceName) throws IOException {
    List<URL> urlList = new ArrayList<>();

    ClassLoader loader = JvmUt
  }

  public static ClassLoader findClassLoader()
}
