import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class AddQuestionFormNode {
	private List<TextField> choiceTexts;
	private List<ToggleGroup> choiceGroups;
	private VBox term;
	
	public AddQuestionFormNode() {
		choiceTexts = new ArrayList<TextField>();
		choiceGroups = new ArrayList<ToggleGroup>();
		term = new VBox();
	}
	
	public TextField getMetadata() {
		return null;
	}
	
	public TextField getQuestion() {
		return null;
	}
	
	public TextField getTopic() {
		return null;
	}
	
	public TextField getImage() {
		return null;
	}
	
	public List<TextField> getChoiceTexts(){
		return null;
	}
	
	public VBox getNode() {
		return null;
	}
	
	public List<TextField> getChoiceGroups(){
		return null;
	}
}
