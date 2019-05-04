import java.util.Iterator;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 * Sets up a VBox to display when showing a question and the possible answer choices
 * @author Hunter, Yifei, Sammy
 *
 */
public class QuestionNode implements NodeWrapperADT{
	private VBox node;  //Holds VBox that will display to user
	private ToggleGroup choices;  //The choices that will be displayed
	private Question question;  //The question that will be displayed
	
	/**
	 * Constructor automatically sets up VBox field
	 * @param question
	 */
	public QuestionNode(Question question){
		this.node = new VBox(7);
		this.choices = new ToggleGroup();
		this.question = question;
		SetupVBox();
	}
	
	/**
	 * Getter for Node field
	 * @return
	 */
	public VBox getNode() {
		return this.node;
	}
	
	/**
	 * Getter for question Field
	 * @return
	 */
	public Question getQuestion() {
      return this.question;
    }
	/**
	 * Getter for choices field
	 * @return
	 */
	public ToggleGroup getChoices() {
		return this.choices;
	}
	
	/**
	 * Sets up the VBox. Automatically called in the constructor
	 */
	public void SetupVBox() {
		//label for the question
		Label questionLabel = new Label(this.question.getQuestion());
		//Accompanying image to question
		if (!question.getImage().equals("none")) {
		  Image image = new Image(this.question.getImage());
		  ImageView imageView = new ImageView(image);
          imageView.setFitHeight(200);
          imageView.setFitWidth(200);
          imageView.setPreserveRatio(true);
          node.getChildren().add(imageView);
		}
		node.getChildren().add(questionLabel);
		//Set the text and Togglegroup for each button
		for (Choice c:this.question.getChoices()) {
		  RadioButton  bt=new RadioButton (c.getChoice());
		  choices.getToggles().add(bt);
		  node.getChildren().add(bt);
		}
		
		
	}
	
	/**
	 * Returns a node
	 */
	@Override
	public Node node() {
		return null;
	}

}
