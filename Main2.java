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
import javax.swing.filechooser.FileSystemView;
import java.awt.event.WindowAdapter;
import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("UPLOAD JSON TEST");
                alert.setContentText("Should open file menu");

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

                alert.showAndWait();
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
                Stage quizWindow = new Stage();
                String topic = topicSelection.getValue();
                int numQ = Integer.parseInt(questionNum.getText());
                List<Question> questions = qDB.getQuestions(topic);

                Random rn = new Random();
                for(int i = 0; i < numQ; i++) {
                    Question getQ = questions.get(rn.nextInt(questions.size()));

                    QuestionNode currentQ = new QuestionNode(getQ);
                    Scene questionScene = new Scene(currentQ.getNode(), 400, 400);
                    quizWindow.setScene(questionScene);
                    quizWindow.show();
                }

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