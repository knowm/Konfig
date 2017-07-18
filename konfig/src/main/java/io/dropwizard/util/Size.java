package io.dropwizard.util;

import static java.util.Objects.requireNonNull;

import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class Size implements Comparable<Size> {
  private static final Pattern SIZE_PATTERN = Pattern.compile("(\\d+)\\s*(\\S+)");

  private static final Map<String, SizeUnit> SUFFIXES = new TreeMap();

  static {
    SUFFIXES.put("B", SizeUnit.BYTES);
    SUFFIXES.put("byte", SizeUnit.BYTES);
    SUFFIXES.put("bytes", SizeUnit.BYTES);
    SUFFIXES.put("K", SizeUnit.KILOBYTES);
    SUFFIXES.put("KB", SizeUnit.KILOBYTES);
    SUFFIXES.put("KiB", SizeUnit.KILOBYTES);
    SUFFIXES.put("kilobyte", SizeUnit.KILOBYTES);
    SUFFIXES.put("kilobytes", SizeUnit.KILOBYTES);
    SUFFIXES.put("M", SizeUnit.MEGABYTES);
    SUFFIXES.put("MB", SizeUnit.MEGABYTES);
    SUFFIXES.put("MiB", SizeUnit.MEGABYTES);
    SUFFIXES.put("megabyte", SizeUnit.MEGABYTES);
    SUFFIXES.put("megabytes", SizeUnit.MEGABYTES);
    SUFFIXES.put("G", SizeUnit.GIGABYTES);
    SUFFIXES.put("GB", SizeUnit.GIGABYTES);
    SUFFIXES.put("GiB", SizeUnit.GIGABYTES);
    SUFFIXES.put("gigabyte", SizeUnit.GIGABYTES);
    SUFFIXES.put("gigabytes", SizeUnit.GIGABYTES);
    SUFFIXES.put("T", SizeUnit.TERABYTES);
    SUFFIXES.put("TB", SizeUnit.TERABYTES);
    SUFFIXES.put("TiB", SizeUnit.TERABYTES);
    SUFFIXES.put("terabyte", SizeUnit.TERABYTES);
    SUFFIXES.put("terabytes", SizeUnit.TERABYTES);
  }

  public static Size bytes(long count) {
    return new Size(count, SizeUnit.BYTES);
  }

  public static Size kilobytes(long count) {
    return new Size(count, SizeUnit.KILOBYTES);
  }

  public static Size megabytes(long count) {
    return new Size(count, SizeUnit.MEGABYTES);
  }

  public static Size gigabytes(long count) {
    return new Size(count, SizeUnit.GIGABYTES);
  }

  public static Size terabytes(long count) {
    return new Size(count, SizeUnit.TERABYTES);
  }

  @JsonCreator
  public static Size parse(String size) {
    final Matcher matcher = SIZE_PATTERN.matcher(size);
    if (!matcher.matches()) {
      throw new IllegalArgumentException("Invalid size: " + size);
    }
    final long count = Long.parseLong(matcher.group(1));
    final SizeUnit unit = SUFFIXES.get(matcher.group(2));
    if (unit == null) {
      throw new IllegalArgumentException("Invalid size: " + size + ". Wrong size unit");
    }

    return new Size(count, unit);
  }

  private final long count;
  private final SizeUnit unit;

  private Size(long count, SizeUnit unit) {
    this.count = count;
    this.unit = requireNonNull(unit);
  }

  public long getQuantity() {
    return count;
  }

  public SizeUnit getUnit() {
    return unit;
  }

  public long toBytes() {
    return SizeUnit.BYTES.convert(count, unit);
  }

  public long toKilobytes() {
    return SizeUnit.KILOBYTES.convert(count, unit);
  }

  public long toMegabytes() {
    return SizeUnit.MEGABYTES.convert(count, unit);
  }

  public long toGigabytes() {
    return SizeUnit.GIGABYTES.convert(count, unit);
  }

  public long toTerabytes() {
    return SizeUnit.TERABYTES.convert(count, unit);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if ((obj == null) || (getClass() != obj.getClass())) {
      return false;
    }
    final Size size = (Size) obj;
    return (count == size.count) && (unit == size.unit);
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
  public int compareTo(Size other) {
    if (unit == other.unit) {
      return Long.compare(count, other.count);
    }

    return Long.compare(toBytes(), other.toBytes());
  }
}
