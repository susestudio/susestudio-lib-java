package com.suse.studio.client.exception;

/**
 * An error that occurred during a request to SUSE Studio.
 */
public class SUSEStudioException extends Exception {

    /**
     * Serial version uid.
     */
    private static final long serialVersionUID = -7929499514444458368L;

    /**
     * A SUSE Studio error code.
     * 
     * @see <a href="http://susestudio.com/help/api/v2/general.html">SUSE Studio API guide</a>
     */
    private String code;

    /**
     * An explanation of the error.
     */
    private String message;

    /**
     * Standard constructor.
     * 
     * @param code
     * @param message
     */
    public SUSEStudioException(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    /**
     * Alternative constructor expecting a {@link Throwable}.
     *
     * @param cause the exception cause
     */
    public SUSEStudioException(Throwable cause) {
        super(cause);
        this.message = cause.getMessage();
    }

    /**
     * Alternative constructor that takes an additional {@link Throwable} signaling
     * the cause.
     *
     * @param code
     * @param message
     * @param cause
     */
    public SUSEStudioException(String code, String message, Throwable cause) {
        super(cause);
        this.code = code;
        this.message = message;
    }

    /**
     * Returns a SUSE Studio error code.
     * 
     * @see <a href="http://susestudio.com/help/api/v2/general.html">SUSE Studio API guide</a>
     */
    public String getCode() {
        return code;
    }

    /**
     * Returns an explanation of the error.
     */
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        if (getCode() != null) {
            return getMessage() + " (" + getCode() + ")";
        } else {
            return getMessage();
        }
    }
}
