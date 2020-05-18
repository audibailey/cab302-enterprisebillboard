package common.router;

/**
 * This enum determines the type of response.
 *
 * @author Perdana Bailey
 */
public enum Status {
    BAD_REQUEST,
    CREATED,

    FAILED, // Generic Response

    FORBIDDEN,
    INTERNAL_SERVER_ERROR,
    METHOD_NOT_ALLOWED,
    NOT_FOUND,
    SUCCESS,
    UNAUTHORIZED,
    UNSUPPORTED_TYPE,
}
