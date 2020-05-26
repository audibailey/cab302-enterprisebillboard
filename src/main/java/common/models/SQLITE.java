package common.models;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This interface that allows the schema builder to automatically generate a schema based on fields.
 *
 * @author Perdana Bailey
 * @author Jamie Martin
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLITE {
    String type() default "";
}
