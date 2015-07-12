package za.co.zynafin.teamtracker.trace;


import java.util.Date;


public class Trace {

    private Date date;
    private String type;
    private long customerId;

    public Trace() {
    }

    public Trace(long customerId,  String type, Date date){
        this.customerId = customerId;
        this.type = type;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "{" +
                "date=" + date +
                ", type='" + type + '\'' +
                ", customerId=" + customerId +
                '}';
    }
}
