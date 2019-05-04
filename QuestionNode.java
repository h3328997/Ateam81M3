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

public class QuestionNode implements NodeWrapperADT{
	private VBox node;
	private ToggleGroup choices;
	private Question question;
	
	public QuestionNode(Question question){
		this.node = new VBox(7);
		this.choices = new ToggleGroup();
		this.question = question;
		SetupVBox();
	}
	
	public VBox getNode() {
		return this.node;
	}
	
	public ToggleGroup getChoices() {
		return this.choices;
	}
	
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
		  ToggleButton bt=new ToggleButton(c.getChoice());
		  choices.getToggles().add(bt);
		  node.getChildren().add(bt);
		}
		
		
	}

	@Override
	public Node node() {
		return null;
	}

}
