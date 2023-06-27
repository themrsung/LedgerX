package oasis.ledgerx.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies asset to be non-cash
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE_USE, ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_PARAMETER, ElementType.TYPE})
public @interface NonCash {

}
