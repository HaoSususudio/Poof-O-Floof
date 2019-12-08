package model;

public class User {
    private int userId;
    private String currentIp;
    private String currentIpLocation;
    private String displayName;
    
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(int userId, String currentIp, String currentIpLocation, String displayName) {
		super();
		this.userId = userId;
		this.currentIp = currentIp;
		this.currentIpLocation = currentIpLocation;
		this.displayName = displayName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getCurrentIp() {
		return currentIp;
	}

	public void setCurrentIp(String currentIp) {
		this.currentIp = currentIp;
	}

	public String getCurrentIpLocation() {
		return currentIpLocation;
	}

	public void setCurrentIpLocation(String currentIpLocation) {
		this.currentIpLocation = currentIpLocation;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currentIp == null) ? 0 : currentIp.hashCode());
		result = prime * result + ((currentIpLocation == null) ? 0 : currentIpLocation.hashCode());
		result = prime * result + ((displayName == null) ? 0 : displayName.hashCode());
		result = prime * result + userId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (currentIp == null) {
			if (other.currentIp != null)
				return false;
		} else if (!currentIp.equals(other.currentIp))
			return false;
		if (currentIpLocation == null) {
			if (other.currentIpLocation != null)
				return false;
		} else if (!currentIpLocation.equals(other.currentIpLocation))
			return false;
		if (displayName == null) {
			if (other.displayName != null)
				return false;
		} else if (!displayName.equals(other.displayName))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", currentIp=" + currentIp + ", currentIpLocation=" + currentIpLocation
				+ ", displayName=" + displayName + "]";
	}
    
    
}
