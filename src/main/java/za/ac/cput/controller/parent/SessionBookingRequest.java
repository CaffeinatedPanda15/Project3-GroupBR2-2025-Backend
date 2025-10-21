package za.ac.cput.controller.parent;

import java.util.Date;
import java.util.List;

public class SessionBookingRequest {
    private int parentId;
    private List<Integer> childIds; // Multiple children can attend
    private Date sessionDate;
    private String sessionStartTime; // Format: "HH:mm:ss"
    private String sessionEndTime;   // Format: "HH:mm:ss"
    private int nannyId;
    private Integer driverId; // Optional - can be null
    private double paymentAmount;

    // Constructors
    public SessionBookingRequest() {}

    public SessionBookingRequest(int parentId, List<Integer> childIds, Date sessionDate, 
                                String sessionStartTime, String sessionEndTime, 
                                int nannyId, Integer driverId, double paymentAmount) {
        this.parentId = parentId;
        this.childIds = childIds;
        this.sessionDate = sessionDate;
        this.sessionStartTime = sessionStartTime;
        this.sessionEndTime = sessionEndTime;
        this.nannyId = nannyId;
        this.driverId = driverId;
        this.paymentAmount = paymentAmount;
    }

    // Getters and Setters
    public int getParentId() { return parentId; }
    public void setParentId(int parentId) { this.parentId = parentId; }

    public List<Integer> getChildIds() { return childIds; }
    public void setChildIds(List<Integer> childIds) { this.childIds = childIds; }

    public Date getSessionDate() { return sessionDate; }
    public void setSessionDate(Date sessionDate) { this.sessionDate = sessionDate; }

    public String getSessionStartTime() { return sessionStartTime; }
    public void setSessionStartTime(String sessionStartTime) { this.sessionStartTime = sessionStartTime; }

    public String getSessionEndTime() { return sessionEndTime; }
    public void setSessionEndTime(String sessionEndTime) { this.sessionEndTime = sessionEndTime; }

    public int getNannyId() { return nannyId; }
    public void setNannyId(int nannyId) { this.nannyId = nannyId; }

    public Integer getDriverId() { return driverId; }
    public void setDriverId(Integer driverId) { this.driverId = driverId; }

    public double getPaymentAmount() { return paymentAmount; }
    public void setPaymentAmount(double paymentAmount) { this.paymentAmount = paymentAmount; }
}
