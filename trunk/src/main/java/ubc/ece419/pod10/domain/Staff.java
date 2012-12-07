package ubc.ece419.pod10.domain;


public class Staff {

	String place;
	
	Long userId, id, placeId;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long staffId) {
		this.id = staffId;
	}
	public Long getPlaceId() {
		return placeId;
	}
	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
}