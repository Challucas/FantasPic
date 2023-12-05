package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import Service.Modele;
import entite.UserImage;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class SampleController {
	
	@FXML
    private Button buttonLogin;
	
	@FXML
    private Button buttonNote;
	
	@FXML
    private TextField nameField;
	
	@FXML
    private TextField noteField;

    @FXML
    private PasswordField passwordField;
    
    @FXML
    private Button buttonLyon;

    @FXML
    private Button buttonTarbes;

    @FXML
    private Button buttonToulouse;
    
    @FXML
    private ImageView pictureCity;
    
    @FXML
    private ListView listView;
    
    @FXML
    private Label msgText;
    
    private Modele modele;
    
    boolean connected = false;
    
    Integer idUser = null;
    
    String nameImage = null;
    
    public void initializeCityWhereConnected(String nameUser) {
    	try {
    		this.modele = new Modele();
    		this.listView.getItems().clear();
    		for (int i = 0; i < this.modele.getListUserImage().size(); i++) {
    			if (this.modele.getListUserImage().get(i).getIdUser().getName().equals(nameUser)) {
    				this.listView.getItems().add(this.modele.getListUserImage().get(i).getIdPicture().getName());
    			}
    		}    		
    	} catch (NullPointerException e) {
	        e.printStackTrace();
	    }
    }



	
    @FXML
	void buttonLogin(ActionEvent event) {
        this.modele = new Modele();
        try {
        	//Je remets a 0 tous les champs à chaque connexion
        	this.listView.getItems().clear();
        	pictureCity.setImage(null);
        	this.noteField.setText("");
        	
        	String nameUser = String.valueOf(nameField.getText());
    		String passwordUser = String.valueOf(passwordField.getText());
    		if(!nameUser.isEmpty() && !passwordUser.isEmpty()) {
    			Map<String, Object> loginResult = modele.login(nameUser, passwordUser);
                
                this.connected = (boolean) loginResult.get("connect");
                this.idUser = (Integer) loginResult.get("idUser");    			
    			if(this.connected) {
    				initializeCityWhereConnected(nameUser);
    				this.msgText.setText("Vous êtes connecté, bonjour "+ nameUser);
    			}
    			else {
    				this.msgText.setText("MDP ou nom d utilisateur incorrect");
    			}
    		}
    		else {
    			this.msgText.setText("Veuillez rentrer un nom d'utilisateur et/ou un MDP");
    		}
			
        }catch (NullPointerException e) {
        	e.printStackTrace();
        }
	}
	
	@FXML
	void buttonNote(ActionEvent event) {
		this.modele = new Modele();
	    try {
	        if (!this.connected) {
	            this.msgText.setText("Vous n'êtes pas connecté. Connectez-vous d'abord.");
	            return;
	        }
	        if (noteField == null) {
	            throw new IllegalArgumentException("Le champ de texte est null.");
	        }

	        String noteText = noteField.getText();
	        if (noteText == null || noteText.trim().isEmpty()) {
	            throw new IllegalArgumentException("Le champ de texte est vide.");
	        }

	        float noteImage = Float.parseFloat(noteText);

	        if (noteImage < 0 || noteImage > 20) {
	            throw new IllegalArgumentException("La note doit être comprise entre 0 et 20.");
	        }
	        
	        if (this.nameImage != null) {
	        	modele.saveNote(noteImage, this.idUser, this.nameImage);
	        	this.msgText.setText("Vous venez d'attribuer la note : "+ noteImage);
	        } else {
	        	this.msgText.setText("Selectionnez une image dans la liste");
	            return;
	        }
	        
	    } catch (NumberFormatException e) {
	        this.msgText.setText("Erreur de format de nombre : " + e.getMessage());
	    } catch (IllegalArgumentException e) {
	        this.msgText.setText(e.getMessage());
	    } catch (Exception e) {
	        this.msgText.setText("Une exception non prévue s'est produite : " + e.getMessage());
	        e.printStackTrace();
	    }
	}

	
	public void SelectImage(MouseEvent event) {
	    this.modele = new Modele();
	    try {
	        ObservableList selectedImage = listView.getSelectionModel().getSelectedItems();
	        if (!selectedImage.isEmpty()) {
	        	nameImage = selectedImage.get(0).toString();
	            javafx.scene.image.Image image = new javafx.scene.image.Image(this.modele.SelectableImage(selectedImage.get(0).toString()));
	            pictureCity.setImage(image);
	            this.noteField.setText(modele.loadNoteForImage(this.idUser, this.nameImage));
	        } else {
	        	this.msgText.setText("Pas de ville, essayez de vous connecter");
	        }
	    } catch (NullPointerException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
			e.printStackTrace();
		}
	}	
}