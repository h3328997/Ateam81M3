import java.util.List;

/**
 * This class represents a single question
 * 
 * @author Yifei Fan
 *
 */
public class Question {
  private String metadata;
  private String question;
  private String topic;
  private String image;
  private String answer;
  private List<Choice> choices;

  public Question(String metadata, String question, String topic, String image,
      List<Choice> choices, String answer) {
    this.metadata = metadata;
    this.question = question;
    this.topic = topic;
    this.image = image;
    this.choices = choices;
    this.answer = answer;
  }

  /**
   * get methods of all fields
   */
  
  public String getQuestion() {
    return question;
  }

  public List<Choice> getChoices() {
    return choices;
  }

  public String getAnswer() {
    return answer;
  }

  public String getMetadata() {
    return metadata;
  }

  public String getTopic() {
    return topic;
  }

  public String getImage() {
    return image;
  }
}
