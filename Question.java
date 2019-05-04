import java.util.List;

/**
 * This class represents a single question
 * 
 * @author Hunter, Yifei, Sammy
 *
 */
public class Question {
  private String metadata;  //Metadata for question
  private String question;  //Question itself
  private String topic;  //Topic for question
  private String image;  //Image associated with the question
  private String answer;  //correct answer for the question
  private List<Choice> choices;  //List of possible choices

  /**
   * Constructor creates new Question
   * @param metadata
   * @param question
   * @param topic
   * @param image
   * @param choices
   * @param answer
   */
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
