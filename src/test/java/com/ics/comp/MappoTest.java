package com.ics.comp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
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

  @Test
  public void mapTest() {
    Map<String, String> map = Map.of("key1", "value1");
    assertEquals(map, Mappo.of(map).map());
  }

  @Test
  public void computeTest() {
    Map<String, String> map = new HashMap<>();
    map.put("key1", "value1");

    BiFunction<String, String, String> remap = (k, v) -> v == null ? "null"  : v + " and... done";

    Mappo<String, String> mappo = Mappo.of(map)
        .compute("key1", remap)
        .compute("key2", remap);

    assertTrue(mappo.opt("key1")
        .filter("value1 and... done"::equals)
        .isPresent()
    );
    assertTrue(mappo.opt("key2")
        .filter("null"::equals)
        .isPresent()
    );
  }

  @Test
  public void computeIfPresentTest() {
    Map<String, String> map = new HashMap<>();
    map.put("key1", "value1");

    BiFunction<String, String, String> remap = (k, v) -> v + " and... done";

    Mappo<String, String> mappo = Mappo.of(map)
        .computeIfPresent("key1", remap)
        .computeIfPresent("key2", remap);

    assertTrue(mappo.opt("key1")
        .filter("value1 and... done"::equals)
            .isPresent()
    );
    assertTrue(mappo.opt("key2")
        .isEmpty()
    );
  }

  @Test
  public void computeIfAbsentTest() {
    Map<String, String> map = new HashMap<>();
    map.put("key1", "value1");

    Function<String, String> remap = (k) -> "null";

    Mappo<String, String> mappo = Mappo.of(map)
        .computeIfAbsent("key1", remap)
        .computeIfAbsent("key2", remap);

    assertTrue(mappo.opt("key1")
        .filter("value1"::equals)
        .isPresent()
    );
    assertTrue(mappo.opt("key2")
        .filter("null"::equals)
        .isPresent()
    );
  }

  @Test
  public void ofTest() {
    String optValue = Mappo.<String, String>of()
        .put("key1", "value1")
        .put("key2", "value2")
        .opt("key1")
        .orElse(null);
    assertEquals("value1", optValue);
  }

}
