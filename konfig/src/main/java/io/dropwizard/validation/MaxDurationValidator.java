package io.dropwizard.validation;

import java.util.concurrent.TimeUnit;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import io.dropwizard.util.Duration;

/**
 * Check that a {@link Duration} being validated is less than or equal to the
 * minimum value specified.
 */
public class MaxDurationValidator implements ConstraintValidator<MaxDuration, Duration> {

    private long maxQty;
    private TimeUnit maxUnit;

    @Override
    public void initialize(MaxDuration constraintAnnotation) {
        this.maxQty = constraintAnnotation.value();
        this.maxUnit = constraintAnnotation.unit();
    }

    @Override
    public boolean isValid(Duration value, ConstraintValidatorContext context) {
        return (value == null) || (value.toNanoseconds() <= maxUnit.toNanos(maxQty));
    }
}
