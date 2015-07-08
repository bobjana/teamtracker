package za.co.zynafin.teamtracker;

public class TeamTrackerException extends RuntimeException{

    private final int errorCode;

    public TeamTrackerException(int errorCode, String debugMessage){
        super(debugMessage);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
