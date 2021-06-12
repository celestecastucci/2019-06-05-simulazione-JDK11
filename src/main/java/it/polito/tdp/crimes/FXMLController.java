/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;
import java.time.Year;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.crimes.model.DistrettoAdiacente;
import it.polito.tdp.crimes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxMese"
    private ComboBox<?> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="boxGiorno"
    private ComboBox<?> boxGiorno; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaReteCittadina"
    private Button btnCreaReteCittadina; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaReteCittadina(ActionEvent event) {

    	Integer anno = boxAnno.getValue();
    	if(anno == null) {
    		txtResult.appendText("seleziona un anno");
    		return ;
    	}
    	
    	
    	this.model.creaGrafo(anno);
    	txtResult.appendText("grafo creato!"+"\n");
    	txtResult.appendText(" num vertici "+this.model.numVertici()+"\n");
    	txtResult.appendText(" num archi "+this.model.numArchi()+"\n");
    	
   
    	//SE VOGLIO I VICINI DI TUTTI I VERTICI DEL GRAFO E NON SOLO DI QUELLO PASSATO COME PARAMETRO
    	//richiamo il metodo dei vertici --> ciclo per tutti i vertici 
    		//creo nel model un metodo a cui passo un distretto ( come faccio quando vuole i vicini di un vertice da tendina)
    			// ciclo per tutti gli elementi della classe creata che memorizza vicino+distanza
    				
    	
    	for(Integer d : this.model.getVerticiGrafo()) {
    		List<DistrettoAdiacente> vicini = this.model.getDistrettiAdiacenti(d);
    		//in modo che cosi avrò un elenco di vicini per ogni vertice del grafo
    		txtResult.appendText("\n\nVICINI DEL DISTRETTO: " + d + "\n");
    		for(DistrettoAdiacente v : vicini)
    			txtResult.appendText(v +"\n");
    	}
    	
    }

    @FXML
    void doSimula(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxGiorno != null : "fx:id=\"boxGiorno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaReteCittadina != null : "fx:id=\"btnCreaReteCittadina\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	boxAnno.getItems().addAll(this.model.getAnni());
    	
    }
}
