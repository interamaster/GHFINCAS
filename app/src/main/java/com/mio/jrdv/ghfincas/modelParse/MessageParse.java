package com.mio.jrdv.ghfincas.modelParse;

/**
 * Created by joseramondelgado on 08/12/15.
 */
public class MessageParse {

    /*

    This class is used to pass the message objects to list adapter.
    //añadido el id!! para SQL


     */

    private int id;

    private String message;
    private long timestamp;

    public MessageParse() {
    }

    public MessageParse(int id ,String message, long timestamp) {
        this.id=id;
        this.message = message;
        this.timestamp = timestamp;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
