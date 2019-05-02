import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

/**
 * Tests the functionality of QuestionDatabase class
 * @author Yifei Fan
 *
 */
public class DatabaseTest {
  
  @Test
  public void test00_loadQuestionsFromJSON() {
    QuestionDatabase test = new QuestionDatabase();

    
      File file=new File("questions_001.json");
      try {
        test.loadQuestionsFromJSON(file);
      } catch (FileNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    
  }
  
  @Test
  public void test01_load_and_save() {
    QuestionDatabase test = new QuestionDatabase();

    
      File file=new File("questions_001.json");
      File file2=new File("questions_002.json");
      File file3=new File("test.json");
      try {
        test.loadQuestionsFromJSON(file);
        test.loadQuestionsFromJSON(file2);
        test.saveQuestionsToJSON(file3);
      } catch (FileNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    
  }
}
