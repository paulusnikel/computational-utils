package com.ics.comp;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Mappo<K, V> {

  @NonNull
  private Map<K, V> map;

  public static <K, V> Mappo<K, V> of(Map<K, V> _map) {
    return new Mappo<>(_map);
  }

  public Collection<V> getAll(Collection<K> keys) {
    return map.entrySet()
        .stream()
        .filter(e -> keys.contains(e.getKey()))
        .map(Entry::getValue)
        .collect(Collectors.toList());
  }

}
