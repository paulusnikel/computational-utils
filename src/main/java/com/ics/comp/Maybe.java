package com.ics.comp;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * A facade of Optional and Stream for null safe operation
 *
 */
public class Maybe {

  /**
   * Alias for {@link Optional#ofNullable(Object)}
   *
   * @param obj nullable object
   * @return Optional of Nullable
   * @param <T> any class
   */
  public static <T> Optional<T> opt(T obj) {
    return Optional.ofNullable(obj);
  }

  /**
   * Stream of nullable collection
   *
   * @param coll nullable collection
   * @return Stream of collection, or empty Stream if collection is null
   * @param <E> type of collection
   */
  public static <E> Stream<E> stream(Collection<E> coll) {
    return Optional.ofNullable(coll)
        .stream()
        .flatMap(Collection::stream);
  }

}
