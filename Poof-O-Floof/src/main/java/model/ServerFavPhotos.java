package model;

/**
 * model of <b>Photo</b> to be served to the front end favorites page.
 * 
 * @author Tim
 *
 */
public class ServerFavPhotos {
	private String small;
	private String medium;
	private String big;
	public ServerFavPhotos() {
		super();
	}
	public ServerFavPhotos(String small, String medium, String big) {
		super();
		this.small = small;
		this.medium = medium;
		this.big = big;
	}
	public String getSmall() {
		return small;
	}
	public void setSmall(String small) {
		this.small = small;
	}
	public String getMedium() {
		return medium;
	}
	public void setMedium(String medium) {
		this.medium = medium;
	}
	public String getBig() {
		return big;
	}
	public void setBig(String big) {
		this.big = big;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((big == null) ? 0 : big.hashCode());
		result = prime * result + ((medium == null) ? 0 : medium.hashCode());
		result = prime * result + ((small == null) ? 0 : small.hashCode());
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
		ServerFavPhotos other = (ServerFavPhotos) obj;
		if (big == null) {
			if (other.big != null)
				return false;
		} else if (!big.equals(other.big))
			return false;
		if (medium == null) {
			if (other.medium != null)
				return false;
		} else if (!medium.equals(other.medium))
			return false;
		if (small == null) {
			if (other.small != null)
				return false;
		} else if (!small.equals(other.small))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ServerFavPhotos [small=" + small + ", medium=" + medium + ", big=" + big + "]";
	}
	
	
}
