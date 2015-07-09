package za.co.zynafin.teamtracker.core;

public class BusinessException extends RuntimeException{

    private final int errorCode;

    public BusinessException(int errorCode, String debugMessage){
        super(debugMessage);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
