package auth.exception;

import java.io.Serializable;

public class AuthException extends Exception implements Serializable {

    private static final long serialVersionUID = 1L;

    public AuthException() {
        super();
    }
    public AuthException(String msg) {
        super(msg);
    }
    public AuthException(String msg, Exception e)  {
        super(msg, e);
    }

}
