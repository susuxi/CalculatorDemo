/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculatordemo;

import java.util.Stack;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author TESLA_CN
 */
public class CalculatorDemo extends Application {

    private final BorderPane root = new BorderPane();
    private final GridPane basicKeyBoard = new GridPane();
    private final VBox display = new VBox();
    private final VBox boxForRB = new VBox(5);
    
    private final TextArea expression = new TextArea();
    private final TextField answer = new TextField();

    private final Button[] btNumber = new Button[10];
    private final Button btDot = new Button(".");
    private final Button btAC = new Button("AC");
    private final Button btPreviousAns = new Button("ANS");
    private final Button btAdd = new Button("+");
    private final Button btSubtract = new Button("-");
    private final Button btMultiply = new Button("¡Á");
    private final Button btDivide = new Button("¡Â");
    private final Button btEquals = new Button("=");

    private final Button btPrevious = new Button("<-");
    private final Button btNext = new Button("->");
    private final HBox previousNext = new HBox();

    private final RadioButton rbBasic = new RadioButton("Basic");
    private final RadioButton rbSenior = new RadioButton("Senior");
    private final ToggleGroup tg = new ToggleGroup();
    private final CalculatorOperation calculatorOperation = new CalculatorOperation();

    private final Stack<String> previous = new Stack<>();
    private final Stack<String> next = new Stack<>();

    @Override
    public void start(Stage primaryStage) {

        basicKeyBoard.setAlignment(Pos.CENTER);
        basicKeyBoard.setHgap(5);
        basicKeyBoard.setVgap(5);
        basicKeyBoard.setPadding(new Insets(5));

        display.getChildren().addAll(expression, answer);

        root.setTop(display);
        root.setCenter(basicKeyBoard);

        initializeBasicFunction();
        initializeSeniorFunction();

        Scene scene = new Scene(root);
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        setBasicAction();
        setSeniorAction();

        rbBasic.setOnAction(e -> {
            if (rbBasic.isSelected()) {
                primaryStage.close();
                root.setBottom(null);
                primaryStage.show();
            }
        });
        rbSenior.setOnAction(e -> {
            if (rbSenior.isSelected()) {
                primaryStage.close();
                root.setBottom(seniorKeyBoard);
                primaryStage.show();
            }
        });

    }

    private void initializeBasicFunction() {
        rbBasic.setSelected(true);
        rbBasic.setToggleGroup(tg);
        rbSenior.setToggleGroup(tg);
        boxForRB.getChildren().addAll(rbBasic, rbSenior);

        btDot.setPrefSize(Config.BUTTON_WIDTH, Config.BUTTON_HEIGHT);
        btAC.setPrefSize(Config.BUTTON_WIDTH, Config.BUTTON_HEIGHT);
        btPreviousAns.setPrefSize(Config.BUTTON_WIDTH, Config.BUTTON_HEIGHT);
        btAdd.setPrefSize(Config.BUTTON_WIDTH, Config.BUTTON_HEIGHT);
        btSubtract.setPrefSize(Config.BUTTON_WIDTH, Config.BUTTON_HEIGHT);
        btMultiply.setPrefSize(Config.BUTTON_WIDTH, Config.BUTTON_HEIGHT);
        btDivide.setPrefSize(Config.BUTTON_WIDTH, Config.BUTTON_HEIGHT);
        btEquals.setPrefSize(Config.BUTTON_WIDTH, Config.BUTTON_HEIGHT);
        for (int i = 0; i < btNumber.length; i++) {
            btNumber[i] = new Button(Integer.toString(i));
            btNumber[i].setPrefSize(Config.BUTTON_WIDTH, Config.BUTTON_HEIGHT);
        }

        btPrevious.setPrefSize(Config.BUTTON_WIDTH / 2, Config.BUTTON_HEIGHT);
        btNext.setPrefSize(Config.BUTTON_WIDTH / 2, Config.BUTTON_HEIGHT);
        previousNext.getChildren().addAll(btPrevious, btNext);

        basicKeyBoard.addRow(0, btNumber[7], btNumber[8], btNumber[9], previousNext, btAC);
        basicKeyBoard.addRow(1, btNumber[4], btNumber[5], btNumber[6], btMultiply, btDivide);
        basicKeyBoard.addRow(2, btNumber[1], btNumber[2], btNumber[3], btAdd, btSubtract);
        basicKeyBoard.addRow(3, btNumber[0], btDot, btPreviousAns, btEquals, boxForRB);

        expression.setPrefWidth(basicKeyBoard.getWidth());
        expression.setPrefRowCount(3);
        expression.setWrapText(true);
        expression.setEditable(false);
        expression.setPromptText("Type here");
        answer.setPrefWidth(basicKeyBoard.getWidth());
        answer.setAlignment(Pos.CENTER_RIGHT);
        answer.setEditable(false);
        answer.setPromptText("The answer will be displayed to here");
    }

    private void setBasicAction() {
        for (int i = 0; i < btNumber.length; i++) {
            final int temp = i;
            btNumber[i].setOnAction(e -> {
                clearNext();
                recordPreviousAction();
                expression.setText(expression.getText() + temp);
            });
        }
        btAdd.setOnAction(e -> {
            clearNext();
            recordPreviousAction();
            expression.setText(expression.getText() + Config.PLUS);
        });
        btSubtract.setOnAction(e -> {
            clearNext();
            recordPreviousAction();
            expression.setText(expression.getText() + Config.SUBSTRAC);
        });
        btMultiply.setOnAction(e -> {
            clearNext();
            recordPreviousAction();
            expression.setText(expression.getText() + Config.MULTIPLY);
        });
        btDivide.setOnAction(e -> {
            clearNext();
            recordPreviousAction();
            expression.setText(expression.getText() + Config.DIVIDE);
        });
        btDot.setOnAction(e -> {
            clearNext();
            recordPreviousAction();
            expression.setText(expression.getText() + '.');
        });
        btEquals.setOnAction(e -> {
            answer.setText(calculatorOperation.getAnswer(expression.getText()));
        });

        btPrevious.setOnAction(e -> {
            returnPrevious();
        });
        btNext.setOnAction(e -> {
            returnNext();
        });
        btAC.setOnAction(e -> {
            expression.setText("");
            answer.setText("");
            previous.removeAllElements();
            clearNext();
        });

        expression.setOnKeyPressed(e -> {
//            System.out.println(e.getCode() + " :: " + e.getCharacter() + " :: " + e.getText());
//            expression.setText(e.getCode() + "\n" + e.getCharacter() + "\n" + e.getText());
            switch (e.getText()) {
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                case "6":
                case "7":
                case "8":
                case "9":
                case "0":
                case ".":
                    expression.setText(expression.getText() + e.getText());
                    break;

                case "+":
                    expression.setText(expression.getText() + Config.PLUS);
                    break;
                case "-":
                    expression.setText(expression.getText() + Config.SUBSTRAC);
                    break;
                case "*":
                    expression.setText(expression.getText() + Config.MULTIPLY);
                    break;
                case "/":
                    expression.setText(expression.getText() + Config.DIVIDE);
                    break;
                case "=":
                    answer.setText(calculatorOperation.getAnswer(expression.getText()));
                    break;

                case "(":
                    expression.setText(expression.getText() + Config.LEFT_BRACKET);
                    break;
                case ")":
                    expression.setText(expression.getText() + Config.RIGHT_BRACKET);
                    break;

            }
            if (!"=".equals(e.getText())) {
                clearNext();
                recordPreviousAction();
            }
        });
    }

    private boolean allowNext = false;

    private void recordPreviousAction() {
        previous.push(expression.getText());
    }

    private void returnPrevious() {
        if (previous.isEmpty()) {
            return;
        }
        allowNext = true;
        next.push(expression.getText());
        expression.setText(previous.pop());
    }

    private void returnNext() {
        if (next.isEmpty() || allowNext == false) {
            return;
        }
        recordPreviousAction();
        expression.setText(next.pop());
    }

    private void clearNext() {
        allowNext = false;
        if (next.isEmpty() == false) {
            next.removeAllElements();
        }
    }

    private final GridPane seniorKeyBoard = new GridPane();

    private final Button btLeftBracket = new Button("(");
    private final Button btRightBracket = new Button(")");
    private final Button btQuadratic = new Button("^2");
    private final Button btPower = new Button("^");
    private final Button btSin = new Button("sin");
    private final Button btCos = new Button("cos");
    private final Button btTan = new Button("tan");
    private final Button btAbs = new Button("Abs");
    private final Button btLog = new Button("log[()()]");
    private final Button btLn = new Button("ln()");
    private final Button btRadical = new Button("¡Ì()");
    private final Button btPi = new Button("¦Ð");

    private void initializeSeniorFunction() {

        seniorKeyBoard.setVgap(5);
        seniorKeyBoard.setHgap(5);
        seniorKeyBoard.setPadding(new Insets(5));
        seniorKeyBoard.setAlignment(Pos.CENTER);

        btLeftBracket.setPrefSize(Config.BUTTON_WIDTH, Config.BUTTON_HEIGHT);
        btRightBracket.setPrefSize(Config.BUTTON_WIDTH, Config.BUTTON_HEIGHT);
        btQuadratic.setPrefSize(Config.BUTTON_WIDTH, Config.BUTTON_HEIGHT);
        btPower.setPrefSize(Config.BUTTON_WIDTH, Config.BUTTON_HEIGHT);
        btSin.setPrefSize(Config.BUTTON_WIDTH, Config.BUTTON_HEIGHT);
        btCos.setPrefSize(Config.BUTTON_WIDTH, Config.BUTTON_HEIGHT);
        btTan.setPrefSize(Config.BUTTON_WIDTH, Config.BUTTON_HEIGHT);
        btAbs.setPrefSize(Config.BUTTON_WIDTH, Config.BUTTON_HEIGHT);
        btLog.setPrefSize(Config.BUTTON_WIDTH, Config.BUTTON_HEIGHT);
        btLn.setPrefSize(Config.BUTTON_WIDTH, Config.BUTTON_HEIGHT);
        btRadical.setPrefSize(Config.BUTTON_WIDTH, Config.BUTTON_HEIGHT);
        btPi.setPrefSize(Config.BUTTON_WIDTH, Config.BUTTON_HEIGHT);

        seniorKeyBoard.addRow(0, btAbs, btLeftBracket, btRightBracket);
        seniorKeyBoard.addRow(1, btQuadratic, btPower, btRadical, btLog, btLn);
        seniorKeyBoard.addRow(2, btSin, btCos, btTan, btPi);
    }

    private void setSeniorAction() {
        btLeftBracket.setOnAction(e -> {
            clearNext();
            recordPreviousAction();
            expression.setText(expression.getText() + Config.LEFT_BRACKET);
        });
        btRightBracket.setOnAction(e -> {
            clearNext();
            recordPreviousAction();
            expression.setText(expression.getText() + Config.RIGHT_BRACKET);
        });
        btQuadratic.setOnAction(e -> {
            clearNext();

        });
        btPower.setOnAction(e -> {
            clearNext();

        });
        btSin.setOnAction(e -> {
            clearNext();
            recordPreviousAction();
            expression.setText(expression.getText() + Config.SIN);
        });
        btCos.setOnAction(e -> {
            clearNext();
            recordPreviousAction();
            expression.setText(expression.getText() + Config.COS);
        });
        btTan.setOnAction(e -> {
            clearNext();
            recordPreviousAction();
            expression.setText(expression.getText() + Config.TAN);
        });
        btAbs.setOnAction(e -> {
            clearNext();
            recordPreviousAction();
            expression.setText(expression.getText() + Config.ABS);
        });
        btLog.setOnAction(e -> {
            clearNext();

        });
        btLn.setOnAction(e -> {
            clearNext();

        });
        btRadical.setOnAction(e -> {
            clearNext();

        });
        btPi.setOnAction(e -> {
            clearNext();
            recordPreviousAction();
            expression.setText(expression.getText() + Config.PI);
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
