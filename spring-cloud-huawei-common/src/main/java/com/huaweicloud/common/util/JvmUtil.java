package com.huaweicloud.common.util;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.annotations.VisibleForTesting;

/**
 * partial copied from Java Chassis
 */
public class JvmUtil {
  private static final Logger LOGGER = LoggerFactory.getLogger(JvmUtil.class);

  // available for orcale jdk/openjdk, and maybe others
  @VisibleForTesting
  static final String SUN_JAVA_COMMAND = "sun.java.command";

  private JvmUtil() {
  }

  /**
   *
   * @return main class or null, never throw exception.
   * Note that this method does not ensure that the subMainClass can be returned correctly in some scenes.
   */
  public static Class<?> findMainClassByStackTrace() {
    String mainClass = null;
    StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
    if (stackTrace != null && stackTrace.length > 0) {
      for (StackTraceElement stackTraceElement : stackTrace) {
        if ("main".equals(stackTraceElement.getMethodName())) {
          mainClass = stackTraceElement.getClassName();
          break;
        }
      }
    }
    if (StringUtils.isEmpty(mainClass)) {
      LOGGER.info("Can't found main class by stackTrace.");
      return null;
    }
    try {
      Class<?> cls = Class.forName(mainClass);
      LOGGER.info("Found main class \"{}\" by stackTrace.", mainClass);
      return cls;
    } catch (Throwable e) {
      LOGGER.warn("\"{}\" is not a valid class.", mainClass, e);
      return null;
    }
  }

  /**
   *
   * @return main class or null, never throw exception.
   * Note that this method does not ensure that the subMainClass can be returned correctly in some scenes.
   * like mvn spring-boot:run
   */
  public static Class<?> findMainClass() {
    // Get the mainClass from the call stack
    String mainClass = null;
    // 1.run with java -cp
    // command is main class and args
    // 2.run with java -jar
    // command is jar file name and args
    String command = System.getProperty(SUN_JAVA_COMMAND);
    if (StringUtils.isNotEmpty(command)) {
      String mainClassOrJar = command.trim().split(" ")[0];
      mainClass = readFromJar(mainClassOrJar);
    }
    if (StringUtils.isEmpty(mainClass)) {
      LOGGER.info("Can't found main class by manifest.");
      return null;
    }
    try {
      Class<?> cls = Class.forName(mainClass);
      LOGGER.info("Found main class \"{}\".", mainClass);
      return cls;
    } catch (Throwable e) {
      LOGGER.warn("\"{}\" is not a valid class.", mainClass, e);
      return null;
    }
  }

  private static String readFromJar(String mainClassOrJar) {
    if (!mainClassOrJar.endsWith(".jar")) {
      return mainClassOrJar;
    }

    String manifestUri = "jar:file:" + new File(mainClassOrJar).getAbsolutePath() + "!/" + JarFile.MANIFEST_NAME;

    try {
      URL url = new URL(manifestUri);
      try (InputStream inputStream = url.openStream()) {
        Manifest manifest = new Manifest(inputStream);
        String startClass = manifest.getMainAttributes().getValue("Start-Class");
        if (StringUtils.isNotEmpty(startClass)) {
          return startClass;
        }
        return manifest.getMainAttributes().getValue("Main-Class");
      }
    } catch (Throwable e) {
      LOGGER.warn("Failed to read Main-Class from \"{}\".", manifestUri, e);
      return null;
    }
  }

  public static ClassLoader correctClassLoader(ClassLoader classLoader) {
    Class
  }
}
