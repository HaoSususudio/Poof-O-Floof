package model;

public class Photo {
	private String id;
	private String photoId;
	private String fullUrl;
	public Photo() {
		// TODO Auto-generated constructor stub
	}
	public Photo(String id, String photoId, String fullUrl) {
		super();
		this.id = id;
		this.photoId = photoId;
		this.fullUrl = fullUrl;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPhotoId() {
		return photoId;
	}
	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}
	public String getFullUrl() {
		return fullUrl;
	}
	public void setFullUrl(String fullUrl) {
		this.fullUrl = fullUrl;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fullUrl == null) ? 0 : fullUrl.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((photoId == null) ? 0 : photoId.hashCode());
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
		if (fullUrl == null) {
			if (other.fullUrl != null)
				return false;
		} else if (!fullUrl.equals(other.fullUrl))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (photoId == null) {
			if (other.photoId != null)
				return false;
		} else if (!photoId.equals(other.photoId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Photo [id=" + id + ", photoId=" + photoId + ", fullUrl=" + fullUrl + "]";
	}
	
	

}
