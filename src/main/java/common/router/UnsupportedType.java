package common.router;

public class UnsupportedType extends IActionResult {
    public <T> UnsupportedType(Class<T> clazz) { super(Status.UNSUPPORTED_TYPE, "Unsupported Type. Must be" + clazz.getSimpleName()); }
}
