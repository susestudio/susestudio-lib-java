package com.suse.studio.client.exception;

import java.io.IOException;

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
     * @see http://susestudio.com/help/api/v2/general.html
     */
    private String code;

    /**
     * An explanation of the error.
     */
    private String message;

    /**
     * An I/O exception if this exception was caused by an I/O error condition.
     */
    private IOException cause;

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
     * Alternate constructor for I/O errors.
     * 
     * @param cause
     */
    public SUSEStudioException(IOException cause) {
        this(null, cause.getMessage());
        this.cause = cause;
    }

    /**
     * Returns a SUSE Studio error code.
     * 
     * @see http://susestudio.com/help/api/v2/general.html
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

    /**
     * Returns the causing IOException, if this exception was caused by an I/O error condition.
     */
    public IOException getCause() {
        return cause;
    }

    @Override
    public String toString() {
        if (getCode() != null) {
            return "SUSE Studio error \"" + getCode() + "\": " + getMessage();
        } else {
            return "I/O error:" + getMessage();
        }
    }
}
