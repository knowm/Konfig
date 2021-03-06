package org.knowm.jackson;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.fasterxml.jackson.annotation.JacksonAnnotation;

/**
 * Marker annotation which indicates that the annotated case class should be serialized and
 * deserialized using {@code snake_case} JSON field names instead of {@code camelCase} field names.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotation
public @interface JsonSnakeCase {}
