package kr.co.kh.model;

public class EventApplicationRequest {
    private Long userId;
    private Long eventId;

    // Getter & Setter
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getEventId() {
        return eventId;
    }
    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}