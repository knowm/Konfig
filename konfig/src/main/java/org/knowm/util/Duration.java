package org.knowm.util;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class Duration implements Comparable<Duration> {
  private static final Pattern DURATION_PATTERN = Pattern.compile("(\\d+)\\s*(\\S+)");

  private static final Map<String, TimeUnit> SUFFIXES = new HashMap<>();

  static {
    SUFFIXES.put("ns", TimeUnit.NANOSECONDS);
    SUFFIXES.put("nanosecond", TimeUnit.NANOSECONDS);
    SUFFIXES.put("nanoseconds", TimeUnit.NANOSECONDS);
    SUFFIXES.put("us", TimeUnit.MICROSECONDS);
    SUFFIXES.put("microsecond", TimeUnit.MICROSECONDS);
    SUFFIXES.put("microseconds", TimeUnit.MICROSECONDS);
    SUFFIXES.put("ms", TimeUnit.MILLISECONDS);
    SUFFIXES.put("millisecond", TimeUnit.MILLISECONDS);
    SUFFIXES.put("milliseconds", TimeUnit.MILLISECONDS);
    SUFFIXES.put("s", TimeUnit.SECONDS);
    SUFFIXES.put("second", TimeUnit.SECONDS);
    SUFFIXES.put("seconds", TimeUnit.SECONDS);
    SUFFIXES.put("m", TimeUnit.MINUTES);
    SUFFIXES.put("min", TimeUnit.MINUTES);
    SUFFIXES.put("mins", TimeUnit.MINUTES);
    SUFFIXES.put("minute", TimeUnit.MINUTES);
    SUFFIXES.put("minutes", TimeUnit.MINUTES);
    SUFFIXES.put("h", TimeUnit.HOURS);
    SUFFIXES.put("hour", TimeUnit.HOURS);
    SUFFIXES.put("hours", TimeUnit.HOURS);
    SUFFIXES.put("d", TimeUnit.DAYS);
    SUFFIXES.put("day", TimeUnit.DAYS);
    SUFFIXES.put("days", TimeUnit.DAYS);
  }

  public static Duration nanoseconds(long count) {
    return new Duration(count, TimeUnit.NANOSECONDS);
  }

  public static Duration microseconds(long count) {
    return new Duration(count, TimeUnit.MICROSECONDS);
  }

  public static Duration milliseconds(long count) {
    return new Duration(count, TimeUnit.MILLISECONDS);
  }

  public static Duration seconds(long count) {
    return new Duration(count, TimeUnit.SECONDS);
  }

  public static Duration minutes(long count) {
    return new Duration(count, TimeUnit.MINUTES);
  }

  public static Duration hours(long count) {
    return new Duration(count, TimeUnit.HOURS);
  }

  public static Duration days(long count) {
    return new Duration(count, TimeUnit.DAYS);
  }

  @JsonCreator
  public static Duration parse(String duration) {

    final Matcher matcher = DURATION_PATTERN.matcher(duration);
    if (!matcher.matches()) {
      throw new IllegalArgumentException("Invalid duration: " + duration);
    }

    final long count = Long.parseLong(matcher.group(1));
    final TimeUnit unit = SUFFIXES.get(matcher.group(2));
    if (unit == null) {
      throw new IllegalArgumentException("Invalid duration: " + duration + ". Wrong time unit");
    }

    return new Duration(count, unit);
  }

  private final long count;
  private final TimeUnit unit;

  private Duration(long count, TimeUnit unit) {
    this.count = count;
    this.unit = requireNonNull(unit);
  }

  public long getQuantity() {
    return count;
  }

  public TimeUnit getUnit() {
    return unit;
  }

  public long toNanoseconds() {
    return TimeUnit.NANOSECONDS.convert(count, unit);
  }

  public long toMicroseconds() {
    return TimeUnit.MICROSECONDS.convert(count, unit);
  }

  public long toMilliseconds() {
    return TimeUnit.MILLISECONDS.convert(count, unit);
  }

  public long toSeconds() {
    return TimeUnit.SECONDS.convert(count, unit);
  }

  public long toMinutes() {
    return TimeUnit.MINUTES.convert(count, unit);
  }

  public long toHours() {
    return TimeUnit.HOURS.convert(count, unit);
  }

  public long toDays() {
    return TimeUnit.DAYS.convert(count, unit);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if ((obj == null) || (getClass() != obj.getClass())) {
      return false;
    }
    final Duration duration = (Duration) obj;
    return (count == duration.count) && (unit == duration.unit);

  }

  @Override
  public int hashCode() {
    return (31 * (int) (count ^ (count >>> 32))) + unit.hashCode();
  }

  @Override
  @JsonValue
  public String toString() {
    String units = unit.toString().toLowerCase(Locale.ENGLISH);
    if (count == 1) {
      units = units.substring(0, units.length() - 1);
    }
    return Long.toString(count) + ' ' + units;
  }

  @Override
  public int compareTo(Duration other) {
    if (unit == other.unit) {
      return Long.compare(count, other.count);
    }

    return Long.compare(toNanoseconds(), other.toNanoseconds());
  }
}
