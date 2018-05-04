package org.knowm.valuehandling;

import java.lang.reflect.Type;
import java.util.OptionalLong;

import javax.annotation.Nullable;

import org.hibernate.validator.spi.valuehandling.ValidatedValueUnwrapper;

/**
 * A {@link ValidatedValueUnwrapper} for {@link OptionalLong}.
 *
 * <p>Extracts the value contained by the {@link OptionalLong} for validation, or produces {@code
 * null}.
 */
public class OptionalLongValidatedValueUnwrapper extends ValidatedValueUnwrapper<OptionalLong> {
  @Override
  @Nullable
  public Object handleValidatedValue(final OptionalLong optional) {
    return optional.isPresent() ? optional.getAsLong() : null;
  }

  @Override
  public Type getValidatedValueType(final Type type) {
    return Long.class;
  }
}
