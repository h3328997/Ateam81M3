import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
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
		Image image = null;// = new Image(this.question.getImage());
		ImageView imageView = new ImageView();
		if (question.getImage() != null) {
			if (image != null) {
				imageView = new ImageView(image);
				imageView.setFitHeight(200);
				imageView.setFitWidth(200);
				imageView.setPreserveRatio(true);
			}
		}

		// Add buttons
		Iterator<Choice> iterator = this.question.getChoices().iterator();
		ArrayList<ToggleButton> bts = new ArrayList<>();
		while(iterator.hasNext()) {
			bts.add(new ToggleButton(iterator.next().getChoice()));
		}

		//adding question and buttons to VBox
		node.getChildren().add(questionLabel);
		if(image != null) {
			node.getChildren().add(imageView);
		}

		// Add buttons to screen
		for (ToggleButton b : bts) {
			b.setToggleGroup(choices);
		}
		node.getChildren().addAll(bts);
	}



	@Override
	public Node node() {
		return null;
	}

}
