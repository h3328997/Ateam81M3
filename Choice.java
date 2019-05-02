/**
 * This class represents a single choice of a question
 * 
 * @author Yifei Fan
 *
 */
public class Choice {
  private boolean isCorrect;
  private String choice;

  public Choice(boolean isCorrect, String choice) {
    this.choice = choice;
    this.isCorrect = isCorrect;
  }

  /**
   * get choice text
   * @return
   */
  public String getChoice() {
    return choice;
  }

  /**
   * @return true if it is the correct answer
   */
  public boolean getIsCorrect() {
    return isCorrect;
  }

}
