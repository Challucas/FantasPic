package entite;

public class UserImage {
	
	
	private User idUser;
	private Picture idPicture;
	private Integer note;
	
	public UserImage(User idUser, Picture idPicture, Integer note) {
		super();
		this.idUser = idUser;
		this.idPicture = idPicture;
		this.note = note;
	}
	
	public User getIdUser() {
		return idUser;
	}
	public void setIdUser(User idUser) {
		this.idUser = idUser;
	}
	public Picture getIdPicture() {
		return idPicture;
	}
	public void setIdPicture(Picture idPicture) {
		this.idPicture = idPicture;
	}
	public Integer getNote() {
		return note;
	}
	public void setNote(Integer note) {
		this.note = note;
	}
	
	
	
	
	
}
