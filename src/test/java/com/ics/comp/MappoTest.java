package com.ics.comp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class MappoTest {

  public static Stream<Arguments> getAllSource() {
    return Stream.of(
        Arguments.of(
            Map.of(
                "key1", "value1",
                "key2", "value2"),
            List.of("key2"),
            List.of("value2")
        ),
        Arguments.of(
            Map.of(
                "key1", "value1",
                "key2", "value2"),
            List.of("key3"),
            List.of()
        ),
        Arguments.of(null, List.of("value2"), null)
    );
  }

  public static Stream<Arguments> optSource() {
    return Stream.of(
        Arguments.of(
            Map.of(
                "key1", "value1",
                "key2", "value2"),
            "key2",
            "value2"
        ),
        Arguments.of(
            Map.of(
                "key1", "value1",
                "key2", "value2"),
            "key3",
            null
        )
    );
  }

  public static Stream<Arguments> getSource() {
    return Stream.of(
        Arguments.of(
            Map.of(
                "key1", "value1",
                "key2", "value2"),
            "key2",
            "value2"
        ),
        Arguments.of(
            Map.of(
                "key1", "value1",
                "key2", "value2"),
            "key3",
            null
        )
    );
  }

  @ParameterizedTest
  @MethodSource("getAllSource")
  public <K, V> void getAllTest(Map<K, V> map, List<K> keys, List<V> values) {
    try {
      Collection<V> result = Mappo
          .of(map)
          .getAll(keys);
      assertTrue(result.containsAll(values));
      assertEquals(result.size(), values.size());
    } catch (Exception e) {
      assertInstanceOf(NullPointerException.class, e);
    }
  }

  @ParameterizedTest
  @MethodSource("optSource")
  public <K, V> void optTest(Map<K, V> map, K key, V value) {
    assertEquals(value, Mappo.of(map).opt(key).orElse(null));
  }

  @ParameterizedTest
  @MethodSource("getSource")
  public <K, V> void getTest(Map<K, V> map, K key, V value) {
    assertEquals(value, Mappo.of(map).get(key));
  }

}
