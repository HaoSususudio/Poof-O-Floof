package model;

public class UserCreation {
	private String currentIp;
	private String currentIpLocation;
	private String displayName;
	private String email;
	private String password;
	
	public UserCreation() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public UserCreation(String currentIp, String currentIpLocation, String displayName, String email, String password) {
		super();
		this.currentIp = currentIp;
		this.currentIpLocation = currentIpLocation;
		this.displayName = displayName;
		this.email = email;
		this.password = password;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currentIp == null) ? 0 : currentIp.hashCode());
		result = prime * result + ((currentIpLocation == null) ? 0 : currentIpLocation.hashCode());
		result = prime * result + ((displayName == null) ? 0 : displayName.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
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
		UserCreation other = (UserCreation) obj;
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
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "UserCreation [currentIp=" + currentIp + ", currentIpLocation=" + currentIpLocation + ", displayName="
				+ displayName + ", email=" + email + ", password=" + password + "]";
	}
	
	
}
