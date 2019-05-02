import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * This class contains all questions and maintain a list of all topics. Reads questions from .json
 * files and allows add questions to a .json file
 * 
 * @author Yifei Fan
 *
 */
public class QuestionDatabase {
  private Map<String, List<Question>> topics;

  public QuestionDatabase() {
    topics = new TreeMap<String, List<Question>>();
  }

  /**
   * add a question to the database
   * 
   * @param topic
   * @param question
   */
  public void addQuestion(String topic, Question question) {
    if (!topics.containsKey(topic))
      topics.put(topic, new ArrayList<Question>());
    List<Question> questions = topics.get(topic);
    questions.add(question);
    topics.put(topic, questions);
  }

  /**
   * @return number of questions in the database
   */
  public int getNumQuestions() {
    int num = 0;
    for (String topic : topics.keySet()) {
      num = num + topics.get(topic).size();
    }
    return num;
  }

  /**
   * save all questions to a given file
   * 
   * @param file
   * @throws FileNotFoundException
   */
  public void saveQuestionsToJSON(File file) throws FileNotFoundException {
    JSONObject all = new JSONObject();
    JSONArray ja = new JSONArray();
    for (String topic : topics.keySet()) {
      for (Question q : topics.get(topic)) {
        JSONObject current = new JSONObject();
        current.put("meta-data", q.getMetadata());
        current.put("questionText", q.getQuestion());
        current.put("topic", q.getTopic());
        current.put("image", q.getImage());
        JSONArray ja2 = new JSONArray();
        for (Choice c : q.getChoices()) {
          Map<String, String> m = new LinkedHashMap<String, String>();
          String isCorrect = "F";
          if (c.getIsCorrect())
            isCorrect = "T";
          m.put("isCorrect", isCorrect);
          m.put("choice", c.getChoice());
          ja2.add(m);
        }
        current.put("choiceArray", ja2);
        ja.add(current);
      }
    }
    all.put("questionArray", ja);

    PrintWriter pw = new PrintWriter(file);
    pw.write(all.toJSONString());

    pw.flush();
    pw.close();

  }

  /**
   * get a list of questions under a given topic
   * 
   * @param topic
   * @return
   */
  public List<Question> getQuestions(String topic) {
    return topics.get(topic);
  }

  /**
   * load questions from a existing file and save them to database
   * 
   * @param file
   * @throws FileNotFoundException
   * @throws IOException
   * @throws ParseException
   */
  public void loadQuestionsFromJSON(File file)
      throws FileNotFoundException, IOException, ParseException {
    Object obj = new JSONParser().parse(new FileReader(file));
    JSONObject jo = (JSONObject) obj;
    JSONArray ja = (JSONArray) jo.get("questionArray");
    Iterator itr = ja.iterator();
    while (itr.hasNext()) {
      JSONObject q = (JSONObject) itr.next();
      String metadata = (String) q.get("meta-data");
      String questionText = (String) q.get("questionText");
      String topic = (String) q.get("topic");
      String image = (String) q.get("image");
      // System.out.println(image);
      JSONArray choiceArray = (JSONArray) q.get("choiceArray");
      List<Choice> choices = new ArrayList<Choice>();
      Iterator itr1 = choiceArray.iterator();
      String answer = null;
      while (itr1.hasNext()) {
        JSONObject c = (JSONObject) itr1.next();
        String s = (String) c.get("isCorrect");
        boolean isCorrect = false;
        if (s.equals("T")) {
          isCorrect = true;
          answer = (String) c.get("choice");
        }
        String choice = (String) c.get("choice");
        choices.add(new Choice(isCorrect, choice));
      }
      // System.out.println(answer+"1");
      addQuestion(topic, new Question(metadata, questionText, topic, image, choices, answer));
    }

  }

  /**
   * get a ObservableSet of all topics
   * 
   * @return
   */
  public ObservableSet<String> getTopics() {
    ObservableSet<String> list1 = FXCollections.observableSet(topics.keySet());
    return list1;
  }

}
