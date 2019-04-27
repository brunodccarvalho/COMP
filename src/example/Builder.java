package example;

import java.io.InputStream;
import java.util.HashMap;

public class Builder {
  private HashMap<String, String> cache;

  private <T extends String> T unduplicate(T element) {
    return (T) cache.get(element);
  }
}
