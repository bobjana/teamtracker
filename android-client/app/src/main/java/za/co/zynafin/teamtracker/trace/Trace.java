package za.co.zynafin.teamtracker.trace;


import java.util.Date;
import java.util.Set;

import static za.co.zynafin.teamtracker.Constants.BAY_AREA_LANDMARKS;

public class Trace {

    private Date date;
    private String type;
    private long customerId;

    public Trace() {
    }

    public Trace(String customerName, int transistionType){
        this.date = new Date();
        this.customerId = deriveCustomerId(customerName);
        this.type = transistionType==1?"ENTER":"EXIT";
    }

    private Long deriveCustomerId(String customerName) {
        //Hack to derive LONG customer ids from Names
        Set<String> keys = BAY_AREA_LANDMARKS.keySet();
        int id = 0;
        for (String key : keys){
            id++;
            if (customerName.equals(key)){
                break;
            }
        }
        return new Long(id);
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
}
