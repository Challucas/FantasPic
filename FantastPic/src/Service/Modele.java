package Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import entite.Picture;
import entite.User;
import entite.UserImage;

public class Modele {

	ArrayList<Picture> listCity = new ArrayList<>();
	ArrayList<User> listUsers = new ArrayList<>();
	ArrayList<UserImage> listUserImage = new ArrayList<>();
    private ArrayList<String> historic = new ArrayList<>();
	ArrayList<String> newHistoric = new ArrayList<>();


	public Modele() {
		
		this.listCity = CreatePicture();
		this.listUsers = CreateUser();
		this.listUserImage = CreateUserImage();
	}
	
	public ArrayList<Picture> getListCity() {
		return listCity;
	}

	public void setListCity(ArrayList<Picture> listCity) {
		this.listCity = listCity;
	}

	public ArrayList<User> getListUsers() {
		return listUsers;
	}

	public void setListUsers(ArrayList<User> listUsers) {
		this.listUsers = listUsers;
	}

	public ArrayList<UserImage> getListUserImage() {
		return listUserImage;
	}

	public void setListUserImage(ArrayList<UserImage> listUserImage) {
		this.listUserImage = listUserImage;
	}
	
	@SuppressWarnings("null")
	public ArrayList<Picture> CreatePicture(){
		
		Picture tarbes = new Picture(1, "Assets/Tarbes.jpg", "Tarbes");
		this.listCity.add(tarbes);
		
		Picture toulouse = new Picture(2, "Assets/Toulouse.jpg", "Toulouse");
		this.listCity.add(toulouse);
		
		Picture lyon = new Picture(3, "Assets/Lyon.jpg", "Lyon");
		this.listCity.add(lyon);
		
		return this.listCity;
	}
	
	@SuppressWarnings("null")
	public ArrayList<User> CreateUser() {

		User lucas = new User(1, "Lucas", "lucas");
		this.listUsers.add(lucas);
		
		User clement = new User(2, "Kamyar", "kamyar");
		this.listUsers.add(clement);
		
		User raphael = new User(3, "Raf", "raf");
		this.listUsers.add(raphael);
	 
		return this.listUsers;
	}
	
	public Map<String, Object> login(String name, String password) {
	    ArrayList<User> listUsers = this.listUsers;	    
	    Map<String, Object> loginMap = new HashMap<>();
	    
	    boolean connect = false;
	    Integer idUser = null;

	    for (User user : listUsers) {
	        if (user.getName().equals(name) && user.getPassword().equals(password)) {
	            idUser = user.getId();
	            connect = true;
	        }
	    }
	    loginMap.put("connect", connect);
	    loginMap.put("idUser", idUser);
	    return loginMap;
	}
	
	public ArrayList<UserImage> CreateUserImage() {
		
		//Appel mes méthodes pour créer/récuperer les listes
		ArrayList<User> listUsers = this.listUsers;
		ArrayList<Picture> listCity = this.listCity;
		
		//Récupère les objets de ma liste listCity
		Picture tarbes = listCity.stream().filter(city -> "Tarbes" == city.getName()).findFirst().orElse(null);
		Picture toulouse = listCity.stream().filter(city -> "Toulouse" == city.getName()).findFirst().orElse(null);
		Picture lyon = listCity.stream().filter(city -> "Lyon" == city.getName()).findFirst().orElse(null);
		
		//Récupère les objets de ma liste listUsers
		User lucas = listUsers.stream().filter(user -> "Lucas" == user.getName()).findFirst().orElse(null);
		User clement = listUsers.stream().filter(user -> "Kamyar" == user.getName()).findFirst().orElse(null);
		User raphael = listUsers.stream().filter(user -> "Raf" == user.getName()).findFirst().orElse(null);
		
		UserImage userImage1 = new UserImage(lucas, tarbes, null);
		this.listUserImage.add(userImage1);
		UserImage userImage2 = new UserImage(raphael, tarbes, null);
		this.listUserImage.add(userImage2);
		UserImage userImage3 = new UserImage(clement, toulouse, null);
		this.listUserImage.add(userImage3);
		UserImage userImage4 = new UserImage(lucas, lyon, null);
		this.listUserImage.add(userImage4);
		
		
		return this.listUserImage;
	}
	
	public String SelectableImage(String ville) {
		for(int i = 0 ; i < this.listCity.size(); i++)
        {
        	if(this.listCity.get(i).getName().equals(ville)) {
        		return this.listCity.get(i).getUrl();
        	}
        }
		return null;
	}
	
	public void saveNote(Float noteImage, Integer idUser, String nameImage) {
		File file = new File("src/historyNote.txt");
		String history = idUser + " ; " + nameImage + " ; " + noteImage;
        boolean noteExists = false;
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                	if (line.startsWith(idUser + " ; " + nameImage + " ; ")) {
                        newHistoric.add(history);
                        noteExists = true;
                    } else {
                        newHistoric.add(line);
                    }
                }
            } catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        if (!noteExists) {
            newHistoric.add(history);
        }
        historic = newHistoric;
        try (PrintWriter printWriter = new PrintWriter(file)) {
            for (String line : historic) {
                printWriter.println(line);
            }
        } catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	 public String loadNoteForImage(Integer idUser, String nameImage) throws IOException {
	        File file = new File("src/historyNote.txt");

	        if (file.exists()) {
	            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	                String lineRead;
	                while ((lineRead = reader.readLine()) != null) {
	                    String[] parts = lineRead.split(";");
	                    if (parts.length == 3 && parts[0].trim().equals(idUser.toString().trim()) && parts[1].trim().equals(nameImage.trim())) {
	                        return parts[2].trim();
	                    }
	                }
	            }
	        }
			return null;
	    }
	 
	
}
