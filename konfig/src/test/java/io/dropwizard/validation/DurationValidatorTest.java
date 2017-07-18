package io.dropwizard.validation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;
import javax.validation.Validator;

import org.junit.Test;

import io.dropwizard.util.Duration;

public class DurationValidatorTest {

    @SuppressWarnings("unused")
    public static class Example {
        @MaxDuration(value = 30, unit = TimeUnit.SECONDS)
        private Duration tooBig = Duration.minutes(10);

        @MinDuration(value = 30, unit = TimeUnit.SECONDS)
        private Duration tooSmall = Duration.milliseconds(100);

        @DurationRange(min = 10, max = 30, unit = TimeUnit.MINUTES)
        private Duration outOfRange = Duration.minutes(60);

        @Valid
        private List<@MaxDuration(value = 30, unit = TimeUnit.SECONDS) Duration> maxDurs =
            Arrays.asList(Duration.minutes(10));

        @Valid
        private List<@MinDuration(value = 30, unit = TimeUnit.SECONDS) Duration> minDurs =
            Arrays.asList(Duration.milliseconds(100));

        @Valid
        private List<@DurationRange(min = 10, max = 30, unit = TimeUnit.MINUTES) Duration> rangeDurs =
            Arrays.asList(Duration.minutes(60));

        public void setTooBig(Duration tooBig) {
            this.tooBig = tooBig;
        }
        public void setTooSmall(Duration tooSmall) {
            this.tooSmall = tooSmall;
        }
        public void setOutOfRange(Duration outOfRange) {
            this.outOfRange = outOfRange;
        }
        public void setMaxDurs(List<Duration> maxDurs) {
            this.maxDurs = maxDurs;
        }
        public void setMinDurs(List<Duration> minDurs) {
            this.minDurs = minDurs;
        }
        public void setRangeDurs(List<Duration> rangeDurs) {
            this.rangeDurs = rangeDurs;
        }
    }

    private final Validator validator = BaseValidator.newValidator();

    @Test
    public void returnsASetOfErrorsForAnObject() throws Exception {
        if ("en".equals(Locale.getDefault().getLanguage())) {
            final List<String> errors =
                    ConstraintViolations.format(validator.validate(new Example()));

            assertThat(errors)
                    .containsOnly(
                            "outOfRange must be between 10 MINUTES and 30 MINUTES",
                            "tooBig must be less than or equal to 30 SECONDS",
                            "tooSmall must be greater than or equal to 30 SECONDS",
                            "maxDurs[0].<collection element> must be less than or equal to 30 SECONDS",
                            "minDurs[0].<collection element> must be greater than or equal to 30 SECONDS",
                            "rangeDurs[0].<collection element> must be between 10 MINUTES and 30 MINUTES");
        }
    }

    @Test
    public void returnsAnEmptySetForAValidObject() throws Exception {
        final Example example = new Example();
        example.setTooBig(Duration.seconds(10));
        example.setTooSmall(Duration.seconds(100));
        example.setOutOfRange(Duration.minutes(15));
        example.setMaxDurs(Arrays.asList(Duration.seconds(10)));
        example.setMinDurs(Arrays.asList(Duration.seconds(100)));
        example.setRangeDurs(Arrays.asList(Duration.minutes(15)));

        assertThat(validator.validate(example))
                .isEmpty();
    }
}
