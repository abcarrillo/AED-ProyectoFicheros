package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

public class MainController implements Initializable{
	
	JavaController javaController = new JavaController();
	XMLController xmlController = new XMLController();
	RandomController randomController = new RandomController();
	
	@FXML
    private AnchorPane view;

    @FXML
    private Tab tabJava;

    @FXML
    private Tab tabXML;

    @FXML
    private Tab tabRAF;

    
    public MainController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
		loader.setController(this);
		loader.load();
	}
    
    public AnchorPane getView() {
    	return this.view;
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		this.tabJava.setContent(javaController.getView());
		this.tabXML.setContent(xmlController.getView());
		this.tabRAF.setContent(randomController.getView());
	}

}
