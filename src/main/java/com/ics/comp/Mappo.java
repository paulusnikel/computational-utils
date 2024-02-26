package com.ics.comp;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
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
   * Compute map wrapped in this class, see {@link Map#compute(Object, BiFunction)}
   * 
   * @param key key of map
   * @param remappingFunction function remapping the value of map entries
   * @return new value associated with remapping function
   */
  public Mappo<K, V> compute(K key, BiFunction<K, V, V> remappingFunction) {
    map.compute(key, remappingFunction);
    return this;
  }

  /**
   * Compute map wrapped in this class for value with present key,
   * value with absent key will be ignored,
   * see {@link Map#computeIfPresent(Object, BiFunction)}
   *
   * @param key key of map
   * @param remappingFunction function remapping the value of map entries
   * @return new value associated with remapping function
   */
  public Mappo<K, V> computeIfPresent(K key, BiFunction<K, V, V> remappingFunction) {
    map.computeIfPresent(key, remappingFunction);
    return this;
  }

  /**
   * Compute map wrapped in this class for value with absent key,
   * value with present key will be ignored,
   * see {@link Map#computeIfPresent(Object, BiFunction)}
   *
   * @param key key of map
   * @param remappingFunction function remapping the value of map entries
   * @return new value associated with remapping function
   */
  public Mappo<K, V> computeIfAbsent(K key, Function<K, V> remappingFunction) {
    map.computeIfAbsent(key, remappingFunction);
    return this;
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
