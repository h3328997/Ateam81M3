/*
*
* Assignment: Quiz Generator GUI
* Authors: ATeam 81 - Hunter Ward, Sammy Gomez, Yifei Fan
* Due: 4/25
* Known Bugs: Sometimes the separators disappear on main stage after opening and closing window to add new questions.
*
*/

import javax.swing.JFileChooser;
import java.util.Optional;
import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class Main extends Application {
    QuestionDatabase qDB = new QuestionDatabase();
    private List<Question> quiz;
    private int correct;

    private void resetCombo(ComboBox cb, QuestionDatabase qdb) {
        cb.getItems().removeAll(cb.getItems());
        cb.getItems().addAll(qdb.getTopics());
    }

    public void changeDBN(Label info, QuestionDatabase qDB) {
        int numQ = qDB.getNumQuestions();
        info.setText(numQ + " Question(s) in Database");
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        // Main Scene Layout Declarations
        BorderPane indexPane = new BorderPane();
        HBox select = new HBox();
        VBox mainPane = new VBox();




        // Main Scene Control Declarations
        Button startQuizBtn = new Button("Start Quiz");
        Button selectJSONBtn = new Button("Upload JSON File");
        Button addQuestionBtn = new Button("Add Single Question");
        Button saveToJSON = new Button("Save DB to File");
        ComboBox<String> topicSelection = new ComboBox<String>();
        TextField questionNum = new TextField();
        Label title = new Label("Quiz Generator");
        Label addQuestion = new Label("Add Question(s)");
        Label selectTopic = new Label("Select Topic");
        Label dbInfo = new Label("No Questions in Database");
        Separator sep = new Separator();
        Separator sep1 = new Separator();
        Separator sep2 = new Separator();
        Label topicInfo = new Label("No Topic Selected");
        Label selectLabel = new Label("Enter number of questions to play: ");

        // addQuestion Scene Control Declarations
        Button submitQuestion = new Button("Submit");

        // Controls action
        
        // JSON Button
        selectJSONBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                // Create file picker
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                int result = fileChooser.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    System.out.println("File chosen at: " + selectedFile.getAbsolutePath());

                    try {
                        qDB.loadQuestionsFromJSON(selectedFile);
                        resetCombo(topicSelection, qDB);
                        changeDBN(dbInfo, qDB);
                    } catch(Exception e) {
                        System.out.println("File Upload Failure");
                    }
                }

            }
        });

        // Add Question Button
        addQuestionBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // Create new window
                VBox addQuestionLayout = new VBox();
                Scene addQuestionScene = new Scene(addQuestionLayout, 350, 455);

                Stage addQuestionWindow = new Stage();
                addQuestionWindow.setTitle("Add a Question");
                addQuestionWindow.setScene(addQuestionScene);

                addQuestionWindow.setX(primaryStage.getX());
                addQuestionWindow.setY(primaryStage.getY() + 5);

                Label addQTitle = new Label("Add a Question");
                Label topicLb = new Label("Enter Topic:");
                Separator sep3 = new Separator();
                TextField questionTopic = new TextField();
                Separator sep4 = new Separator();
                Label questionLb = new Label("Enter Question:");
                TextField questionText = new TextField();
                Separator sep5 = new Separator();
                Label ansLb = new Label("Enter Answers:");
                TextField answerRight = new TextField("Enter CORRECT answer here");
                TextField answerWrong1 = new TextField("Enter WRONG answer here");
                TextField answerWrong2 = new TextField("Enter WRONG answer here");
                TextField answerWrong3 = new TextField("Enter WRONG answer here");
                TextField answerWrong4 = new TextField("Enter WRONG answer here");
                Separator sep6 = new Separator();


                // Add a question submit button

                submitQuestion.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setContentText("Question Submitted");
                        alert.showAndWait();
                        List<Choice> choices = new LinkedList<>();
                        choices.add(new Choice(true, answerRight.getText()));
                        choices.add(new Choice(false, answerWrong1.getText()));
                        choices.add(new Choice(false, answerWrong2.getText()));
                        choices.add(new Choice(false, answerWrong3.getText()));
                        choices.add(new Choice(false, answerWrong4.getText()));
                        Question newQuestion = new Question("", questionText.getText(), questionTopic.getText(), "", choices, answerRight.getText());
                        qDB.addQuestion(newQuestion.getTopic(), newQuestion);
                        resetCombo(topicSelection, qDB);
                        changeDBN(dbInfo, qDB);
                        addQuestionWindow.close();
                    }
                });


                addQuestionLayout.getChildren().addAll(addQTitle, sep2, topicLb, questionTopic, sep4, questionLb, questionText, sep5, ansLb, answerRight, answerWrong1, answerWrong2, answerWrong3, answerWrong4, submitQuestion);
                addQuestionLayout.setAlignment(Pos.CENTER);
                addQuestionLayout.setSpacing(12);
                addQuestionLayout.setPadding(new Insets(20));
                addQuestionScene.getStylesheets().add("styles.css");
                addQTitle.getStyleClass().add("title");
                answerRight.getStyleClass().add("righttextfield");
                answerWrong1.getStyleClass().add("wrongtextfield");
                answerWrong2.getStyleClass().add("wrongtextfield");
                answerWrong3.getStyleClass().add("wrongtextfield");
                answerWrong4.getStyleClass().add("wrongtextfield");

                addQuestionWindow.show();

            }
        });

        saveToJSON.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File file = new File("questions.json");
                try {
                    file.createNewFile();
                } catch (Exception e) {
                    System.out.println("File not created");
                }
                try {
                    qDB.saveQuestionsToJSON(file);
                } catch (Exception e) {
                    System.out.println("File Error");
                }
            }
        });

        // Question # textfield
        questionNum.setPrefColumnCount(1);



        // Topics ComboBox
        topicSelection.getItems().addAll(qDB.getTopics());
        topicSelection.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                topicInfo.setText("Topic: " + topicSelection.getValue() + " has " + qDB.getQuestions(topicSelection.getValue()).size() + " question(s)");
            }
        });

        // Start Quiz Button
        startQuizBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                quiz=new ArrayList<Question>();
                String topic = topicSelection.getValue();
                int numQ = Integer.parseInt(questionNum.getText());
                List<Question> questions = qDB.getQuestions(topic);
                if (numQ>questions.size())
                    numQ=questions.size();
                Random rn = new Random();                while (numQ>0) {
                    int idx=rn.nextInt(numQ);
                    Question q=questions.get(idx);
                    quiz.add(q);
                    questions.remove(idx);
                    numQ--;
                }

                Stage quizWindow = new Stage();
                quizScene(0,quizWindow);


            }
        });


        // Layout
        indexPane.setCenter(mainPane);
        indexPane.setTop(title);
        indexPane.setBottom(startQuizBtn);
        indexPane.setAlignment(title, Pos.CENTER);
        indexPane.setAlignment(startQuizBtn, Pos.CENTER);
        select.getChildren().addAll(selectLabel, questionNum);
        select.setAlignment(Pos.CENTER);
        indexPane.setPadding(new Insets(20));
        mainPane.getChildren().addAll(sep1, addQuestion, addQuestionBtn, selectJSONBtn, saveToJSON, sep, selectTopic, topicSelection, sep2, topicInfo, dbInfo, select);
        mainPane.setAlignment(Pos.CENTER);


        mainPane.setSpacing(20);

        // Main Stage Config
        primaryStage.setTitle("ATEAM 81 Quiz Generator");
        Scene primaryScene = new Scene(indexPane, 350, 495);
        primaryScene.getStylesheets().add("styles.css");
        startQuizBtn.getStyleClass().add("startbutton");
        title.getStyleClass().add("title");
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

    private void quizScene(int idx, Stage quizWindow) {
      if (quiz.size()<=idx) {
        end(quizWindow);
        return;
      }
      Question getQ=quiz.get(idx);
      QuestionNode currentQ = new QuestionNode(getQ);
      VBox vbox=new VBox(15);
      Button b = new Button("Confirm");
      EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
        public void handle(ActionEvent e) 
        { 
          VBox vbox2=new VBox(9);
          Question question=currentQ.getQuestion();
          Label questionLabel = new Label(question.getQuestion());
          vbox2.getChildren().add(questionLabel);
          //Accompanying image to question
          if (!question.getImage().equals("none")) {
            Image image = new Image(question.getImage());
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(200);
            imageView.setFitWidth(200);
            imageView.setPreserveRatio(true);
            vbox2.getChildren().add(imageView);
          }
          Button c = new Button("Got it!");
          EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) {
              quizScene(idx+1,quizWindow);
            }
          };
          c.setOnAction(event2);
          if (currentQ.getChoices().getSelectedToggle()==null) {
            
            Label answerLabel = new Label("You did not answer this question, the correct answer is: "+question.getAnswer());
            vbox2.getChildren().add(answerLabel);
            vbox2.getChildren().add(c);
            Scene questionScene = new Scene(vbox2, 400, 400);
            quizWindow.setScene(questionScene);
            quizWindow.show();
          }
          else if (((RadioButton) currentQ.getChoices().getSelectedToggle()).getText()
          .equals(currentQ.getQuestion().getAnswer())) {
            correct++;
            Label answerLabel = new Label("Your answer is correct: "+question.getAnswer());
            vbox2.getChildren().add(answerLabel);
            vbox2.getChildren().add(c);
            Scene questionScene = new Scene(vbox2, 400, 400);
            quizWindow.setScene(questionScene);
            quizWindow.show();
          }
          else {
            Label answerLabel = new Label("Your answer is incorrect. Your answer: "
          +((RadioButton) currentQ.getChoices().getSelectedToggle()).getText()+", the correct answer is "
          +question.getAnswer());
            vbox2.getChildren().add(answerLabel);
            vbox2.getChildren().add(c);
            Scene questionScene = new Scene(vbox2, 400, 400);
            quizWindow.setScene(questionScene);
            quizWindow.show();
          }
          
          
        } 
      }; 
      b.setOnAction(event); 
      Label status = new Label("Question#: "+(idx+1)+" of "+ quiz.size()+" Questions");
      vbox.getChildren().add(status);
      vbox.getChildren().add(currentQ.getNode());
      vbox.getChildren().add(b);
      Scene questionScene = new Scene(vbox, 400, 400);
      quizWindow.setScene(questionScene);
      quizWindow.show();

    }

    private void end(Stage quizWindow) {
      VBox vbox=new VBox(15);
      Label status = new Label("Quiz result: #correct "+correct+" out of "+quiz.size()+". Your Score: "+(float)correct/quiz.size()*100);
      vbox.getChildren().add(status);
      Scene questionScene = new Scene(vbox, 400, 400);
      quizWindow.setScene(questionScene);
      quizWindow.show();
      quiz.clear();
      correct=0;
    }


    @Override
    public void stop() throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exiting Program");
        alert.setHeaderText("Do you want to save the database?");
        alert.setContentText("Chose your option");

        ButtonType buttony = new ButtonType("Yes");
        ButtonType buttonn = new ButtonType("No");

        alert.getButtonTypes().setAll(buttony, buttonn);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttony) {
            File file = new File("questions.json");
            try {
                file.createNewFile();
            } catch (Exception e) {
                System.out.println("File not created");
            }
            try {
                qDB.saveQuestionsToJSON(file);
            } catch (Exception e) {
                System.out.println("File Error");
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}