package com.ics.comp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class MaybeTest {

  public static Stream<Arguments> optSource() {
    return Stream.of(
      Arguments.of("test"),
      null,
      Arguments.of(Optional.of("test"))
    );
  }

  public static Stream<Arguments> streamSource() {
    return Stream.of(
        Arguments.of(List.of(), List.of()),
        Arguments.of(List.of("test"), List.of("test")),
        Arguments.of(null, List.of())
    );
  }

  @ParameterizedTest
  @MethodSource("optSource")
  public void optTest(Object source) {
    assertEquals(source, Maybe.opt(source).orElse(null));
  }

  @ParameterizedTest
  @MethodSource("streamSource")
  public <E> void streamTest(Collection<E> coll, Collection<E> expected) {
    assertEquals(expected, Maybe.stream(coll).collect(Collectors.toList()));
  }

}
