package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.beans.value.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.util.Duration;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;


public class Main extends Application {
    @FXML
    private Button begin;
    @FXML
    private Button restart;
    @FXML
    private Button check;
    @FXML
    private Button save;
    @FXML
    private Button read;
    @FXML
    private TextField name;
    @FXML
    private RadioButton number;
    @FXML
    private RadioButton color;
    @FXML
    private Label time;
    @FXML
    private Label choice;
    @FXML
    private TextField guess;
    @FXML
    private Label hint;
    @FXML
    ListView<String> view1 = new ListView<>();
    @FXML
    ListView<String> view2 = new ListView<>();
    private int gogo=120;
    private int num[]=new int[10];
    private String col="RGBOYCPVLNAI";
    private String ans="";
    private int de;
    private int score=0;
    private int A,B;
    Timeline t;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("build.fxml"));
        primaryStage.setTitle("Guess(non repeat)");
        primaryStage.setScene(new Scene(root, 520, 600));
        primaryStage.show();
    }

    public void initialize() {
        name.setText("Guest");
        hint.setText("");
        guess.setDisable(true);
        restart.setDisable(true);
        check.setDisable(true);
        for(int i=0;i<10;i++){
            num[i]=i;
        }
        ToggleGroup group = new ToggleGroup();
        number.setToggleGroup(group);
        color.setToggleGroup(group);
        begin.setOnAction(e -> control(1));
        restart.setOnAction(e -> control(2));
        check.setOnAction(e -> control(3));
        save.setOnAction(e -> control(4));
        read.setOnAction(e -> control(5));
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

            @Override
            public void changed(ObservableValue<? extends Toggle> observable,
                                Toggle oldValue, Toggle newValue) {

            }
        });
        KeyFrame kf = new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gogo--;
                if(gogo>0){
                    time.setText("Time:"+gogo+"s");
                }
                if(gogo==0){
                    time.setText("Time:"+gogo+"s");
                    hint.setText("Time's up!");
                    check.setDisable(true);
                    guess.setDisable(true);
                }
            }
        });
        t = new Timeline(kf);
        t.setCycleCount(Timeline.INDEFINITE);
    }

    public void control(int i) {
        switch (i) {
            case 1:
                if(color.isSelected()){
                    choice.setText("Guess four colors:");
                    de=1;
                    ans=Building(de);
                    t.play();
                    begin.setDisable(true);
                    color.setDisable(true);
                    number.setDisable(true);
                    name.setDisable(true);
                    guess.setDisable(false);
                    restart.setDisable(false);
                    check.setDisable(false);
                }
                else if(number.isSelected()){
                    choice.setText("Guess four numbers:");
                    de=2;
                    ans=Building(de);
                    t.play();
                    begin.setDisable(true);
                    color.setDisable(true);
                    number.setDisable(true);
                    name.setDisable(true);
                    guess.setDisable(false);
                    restart.setDisable(false);
                    check.setDisable(false);
                }
                else{
                    choice.setText("Please choose one!!");
                }
                break;
            case 2:
                check.setDisable(false);
                save.setDisable(false);
                guess.setDisable(false);
                view1.getItems().clear();
                hint.setText("GUESS!!!!!!!");
                ans=Building(de);
                gogo=121;
                score=0;
                choice.setText("Guess again:");
                break;
            case 3:
                score++;
                A=0;
                B=0;
                for(int k=0;k<4;k++) {
                    if((guess.getText().charAt(k))==(ans.charAt(k))){
                        A++;
                    }
                    for(int j=0;j<4;j++){
                        if(k!=j){
                            if((guess.getText().charAt(k))==(ans.charAt(j))){
                                B++;
                            }
                        }
                    }
                }
                hint.setText(A+"A"+B+"B");
                if(A==4){
                    check.setDisable(true);
                    gogo=0;
                }
                view1.getItems().add(guess.getText()+", "+hint.getText());
                break;
            case 4:
                check.setDisable(true);
                gogo=0;
                if(A!=4){
                    score=-1;
                }
                FileWriter fw = null;
                try{
                    fw = new FileWriter("result.txt",true);
                    fw.write(name.getText()+","+ans+","+score+"\r\n");
                    fw.flush();
                    fw.close();
                }
                catch (IOException e1) {
                    e1.printStackTrace();
                }
                save.setDisable(true);
                break;
            case 5:
                view2.getItems().clear();
                String hello;
                FileReader fr = null;
                try {
                    fr = new FileReader("Result.txt");
                    BufferedReader br = new BufferedReader(fr);
                    while ((hello = br.readLine()) != null) {
                        view2.getItems().add(hello);
                    }
                    fr.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                break;
        }
    }
    public String Building(int i){
        Random ran=new Random();
        String create="";
        if(i==1) {
            int[] a = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            int count = 4;
            while (count > 0) {
                int hi = ran.nextInt(12);
                while (a[hi] != 0) {
                    hi = ran.nextInt(12);
                }
                a[hi]++;
                create = create + col.charAt(hi);
                count--;
            }
        }
        else{
                int[] b={0,0,0,0,0,0,0,0,0,0};
                int count=4;
                while(count>0) {
                    int hi = ran.nextInt(10);
                    while (b[hi] != 0) {
                        hi = ran.nextInt(10);
                    }
                    b[hi]++;
                    create = create + Integer.toString(hi);
                    count--;
            }
        }
        return create;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
