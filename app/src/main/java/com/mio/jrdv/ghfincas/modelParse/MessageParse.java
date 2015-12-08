package com.mio.jrdv.ghfincas.modelParse;

/**
 * Created by joseramondelgado on 08/12/15.
 */
public class MessageParse {

    /*

    This class is used to pass the message objects to list adapter.

     */


    private String message;
    private long timestamp;

    public MessageParse() {
    }

    public MessageParse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
