package com.suse.studio.client.net;

/**
 * An error that occured after a request to SUSE Studio.
 */
public class StudioException extends Exception {

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
     * Standard constuctor.
     * 
     * @param code
     * @param message
     */
    public StudioException(String code, String message) {
        super();
        this.code = code;
        this.message = message;
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

    @Override
    public String toString() {
        return "SUSE Studio error \"" + getCode() + "\": " + getMessage();
    }
}
