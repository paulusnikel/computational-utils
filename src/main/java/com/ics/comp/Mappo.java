package com.ics.comp;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Wrapper for {@link Map}
 *
 * @param <K> Key of Map
 * @param <V> Value of Map
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Mappo<K, V> {

  @NonNull
  private Map<K, V> map;

  /**
   * Return {@link Mappo from map}
   *
   * @param _map the map, non nullable
   * @return Mappo
   * @param <K> Map key type
   * @param <V> Map value type
   */
  public static <K, V> Mappo<K, V> of(Map<K, V> _map) {
    return new Mappo<>(_map);
  }

  /**
   * @return map from wrapper
   */
  public Map<K, V> map() {
    return this.map;
  }

  /**
   * Optional from value
   *
   * @param key key of map
   * @return Optional of value
   */
  public Optional<V> opt(K key) {
    return Optional.ofNullable(map.get(key));
  }

  /**
   * Value of map
   *
   * @param key key of map
   * @return value from map, nullable
   */
  public V get(K key) {
    return this.map.get(key);
  }

  /**
   * @param keys collection of key
   * @return values from keys
   */
  public Collection<V> getAll(Collection<K> keys) {
    return map.entrySet()
        .stream()
        .filter(e -> keys.contains(e.getKey()))
        .map(Entry::getValue)
        .collect(Collectors.toList());
  }

}
