package model;

public class AnimalFull {
	private int animalId;
	private String animalType;
	private String species;
	private String breedPrimary;
	private String breedSecondary;
	private String breedMixed;
	private String colorPrimary;
	private String colorSecondary;
	private String colorTertiary;
	private int age;
	private String gender;
	private double size;
	private String coat;
	private String status;
	private String url;
	
	public AnimalFull() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AnimalFull(int animalId, String animalType, String species, String breedPrimary, String breedSecondary,
			String breedMixed, String colorPrimary, String colorSecondary, String colorTertiary, int age, String gender,
			double size, String coat, String status, String url) {
		super();
		this.animalId = animalId;
		this.animalType = animalType;
		this.species = species;
		this.breedPrimary = breedPrimary;
		this.breedSecondary = breedSecondary;
		this.breedMixed = breedMixed;
		this.colorPrimary = colorPrimary;
		this.colorSecondary = colorSecondary;
		this.colorTertiary = colorTertiary;
		this.age = age;
		this.gender = gender;
		this.size = size;
		this.coat = coat;
		this.status = status;
		this.url = url;
	}

	public int getAnimalId() {
		return animalId;
	}

	public void setAnimalId(int animalId) {
		this.animalId = animalId;
	}

	public String getAnimalType() {
		return animalType;
	}

	public void setAnimalType(String animalType) {
		this.animalType = animalType;
	}

	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	public String getBreedPrimary() {
		return breedPrimary;
	}

	public void setBreedPrimary(String breedPrimary) {
		this.breedPrimary = breedPrimary;
	}

	public String getBreedSecondary() {
		return breedSecondary;
	}

	public void setBreedSecondary(String breedSecondary) {
		this.breedSecondary = breedSecondary;
	}

	public String getBreedMixed() {
		return breedMixed;
	}

	public void setBreedMixed(String breedMixed) {
		this.breedMixed = breedMixed;
	}

	public String getColorPrimary() {
		return colorPrimary;
	}

	public void setColorPrimary(String colorPrimary) {
		this.colorPrimary = colorPrimary;
	}

	public String getColorSecondary() {
		return colorSecondary;
	}

	public void setColorSecondary(String colorSecondary) {
		this.colorSecondary = colorSecondary;
	}

	public String getColorTertiary() {
		return colorTertiary;
	}

	public void setColorTertiary(String colorTertiary) {
		this.colorTertiary = colorTertiary;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public String getCoat() {
		return coat;
	}

	public void setCoat(String coat) {
		this.coat = coat;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
		result = prime * result + age;
		result = prime * result + animalId;
		result = prime * result + ((animalType == null) ? 0 : animalType.hashCode());
		result = prime * result + ((breedMixed == null) ? 0 : breedMixed.hashCode());
		result = prime * result + ((breedPrimary == null) ? 0 : breedPrimary.hashCode());
		result = prime * result + ((breedSecondary == null) ? 0 : breedSecondary.hashCode());
		result = prime * result + ((coat == null) ? 0 : coat.hashCode());
		result = prime * result + ((colorPrimary == null) ? 0 : colorPrimary.hashCode());
		result = prime * result + ((colorSecondary == null) ? 0 : colorSecondary.hashCode());
		result = prime * result + ((colorTertiary == null) ? 0 : colorTertiary.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		long temp;
		temp = Double.doubleToLongBits(size);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((species == null) ? 0 : species.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		AnimalFull other = (AnimalFull) obj;
		if (age != other.age)
			return false;
		if (animalId != other.animalId)
			return false;
		if (animalType == null) {
			if (other.animalType != null)
				return false;
		} else if (!animalType.equals(other.animalType))
			return false;
		if (breedMixed == null) {
			if (other.breedMixed != null)
				return false;
		} else if (!breedMixed.equals(other.breedMixed))
			return false;
		if (breedPrimary == null) {
			if (other.breedPrimary != null)
				return false;
		} else if (!breedPrimary.equals(other.breedPrimary))
			return false;
		if (breedSecondary == null) {
			if (other.breedSecondary != null)
				return false;
		} else if (!breedSecondary.equals(other.breedSecondary))
			return false;
		if (coat == null) {
			if (other.coat != null)
				return false;
		} else if (!coat.equals(other.coat))
			return false;
		if (colorPrimary == null) {
			if (other.colorPrimary != null)
				return false;
		} else if (!colorPrimary.equals(other.colorPrimary))
			return false;
		if (colorSecondary == null) {
			if (other.colorSecondary != null)
				return false;
		} else if (!colorSecondary.equals(other.colorSecondary))
			return false;
		if (colorTertiary == null) {
			if (other.colorTertiary != null)
				return false;
		} else if (!colorTertiary.equals(other.colorTertiary))
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (Double.doubleToLongBits(size) != Double.doubleToLongBits(other.size))
			return false;
		if (species == null) {
			if (other.species != null)
				return false;
		} else if (!species.equals(other.species))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
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
		return "AnimalFull [animalId=" + animalId + ", animalType=" + animalType + ", species=" + species
				+ ", breedPrimary=" + breedPrimary + ", breedSecondary=" + breedSecondary + ", breedMixed=" + breedMixed
				+ ", colorPrimary=" + colorPrimary + ", colorSecondary=" + colorSecondary + ", colorTertiary="
				+ colorTertiary + ", age=" + age + ", gender=" + gender + ", size=" + size + ", coat=" + coat
				+ ", status=" + status + ", url=" + url + "]";
	}
}
