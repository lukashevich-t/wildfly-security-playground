package by.gto.test.jackson;

public class AisException extends Exception {
    private int errCode = -1;
    private int httpCode = 500;

    public AisException(String message, int errCode, int httpCode) {
        super(message);
        this.errCode = errCode;
        this.httpCode = httpCode;
    }

    public AisException(String message, int errCode) {
        super(message);
        this.errCode = errCode;
    }

    public int getErrCode() {
        return errCode;
    }

    public int getHttpCode() {
        return httpCode;
    }
}
