package model;

public class Photo {
	private int animalId;
	private int photoId;
	private String type;
	private String fullUrl;
	private String url;
	public Photo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Photo(int animalId, int photoId, String type, String fullUrl, String url) {
		super();
		this.animalId = animalId;
		this.photoId = photoId;
		this.type = type;
		this.fullUrl = fullUrl;
		this.url = url;
	}
	public int getAnimalId() {
		return animalId;
	}
	public void setAnimalId(int animalId) {
		this.animalId = animalId;
	}
	public int getPhotoId() {
		return photoId;
	}
	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFullUrl() {
		return fullUrl;
	}
	public void setFullUrl(String fullUrl) {
		this.fullUrl = fullUrl;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + animalId;
		result = prime * result + ((fullUrl == null) ? 0 : fullUrl.hashCode());
		result = prime * result + photoId;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		Photo other = (Photo) obj;
		if (animalId != other.animalId)
			return false;
		if (fullUrl == null) {
			if (other.fullUrl != null)
				return false;
		} else if (!fullUrl.equals(other.fullUrl))
			return false;
		if (photoId != other.photoId)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Photo [animalId=" + animalId + ", photoId=" + photoId + ", type=" + type + ", fullUrl=" + fullUrl
				+ ", url=" + url + "]";
	}
	
	
}
