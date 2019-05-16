package org.tjut.xsl.app;

public class ServerException extends Exception {
    private Integer code;

    public ServerException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
