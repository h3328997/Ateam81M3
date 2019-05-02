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
		Image image = new Image(this.question.getImage());
		ImageView imageView = new ImageView();
		if(image != null) {
			imageView = new ImageView(image);
			imageView.setFitHeight(200);
			imageView.setFitWidth(200);
			imageView.setPreserveRatio(true);
		}
		//Set the text and Togglegroup for each button
		Iterator<Choice> iterator = this.question.getChoices().iterator();
		ToggleButton tb1 = new ToggleButton(iterator.next().getChoice());
		tb1.setToggleGroup(choices);
		ToggleButton tb2 = new ToggleButton(iterator.next().getChoice());
		tb2.setToggleGroup(choices);
		ToggleButton tb3 = new ToggleButton(iterator.next().getChoice());
		tb3.setToggleGroup(choices);
		ToggleButton tb4 = new ToggleButton(iterator.next().getChoice());
		tb4.setToggleGroup(choices);
		ToggleButton tb5 = new ToggleButton(iterator.next().getChoice());
		tb5.setToggleGroup(choices);
		
		//adding question and buttons to VBox
		node.getChildren().add(questionLabel);
		if(image != null) {
			node.getChildren().add(imageView);
		}
		node.getChildren().add(tb1);
		node.getChildren().add(tb2);
		node.getChildren().add(tb3);
		node.getChildren().add(tb4);
		node.getChildren().add(tb5);
	}

	@Override
	public Node node() {
		return null;
	}

}
