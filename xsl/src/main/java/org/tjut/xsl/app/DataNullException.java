package org.tjut.xsl.app;

public class DataNullException extends Exception {
    private Integer code;

    public DataNullException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
