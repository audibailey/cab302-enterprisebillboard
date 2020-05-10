package common.router;

/**
 * An IActionResult for an unsupported type result.
 *
 * @author Jamie Martin
 */
public class UnsupportedType extends IActionResult {
    // A constructor with a class based response for the client.
    public <T> UnsupportedType(Class<T> classType) { super(Status.UNSUPPORTED_TYPE, "Unsupported Type. Must be" + classType.getSimpleName()); }
}
