package common;

/**
 * This enum determines the type of response.
 *
 * @author Perdana Bailey
 */
public enum Status {
    SUCCESS, // Generic Response
    FAILED, // Generic Response

    CREATED,

    BAD_REQUEST,
    UNSUPPORTED_TYPE,
    UNAUTHORIZED,
    INTERNAL_SERVER_ERROR,
    FORBIDDEN,
    METHOD_NOT_ALLOWED,
    NOT_FOUND,
}
