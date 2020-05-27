package client.components.table;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This interface that allows the GUI to display objects using
 * class information automatically using the help of annotations.
 *
 * @author Jamie Martin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DisplayAs {
    String value();
    int index();
    boolean editable() default false;
}
