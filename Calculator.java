import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Calculator extends Application implements EventHandler<ActionEvent> {
    static TextField numArea = new TextField();
    static LinkedList<String> infix = new LinkedList<String>();
    ShuntingYard o = new ShuntingYard();
    Arithmetic a = new Arithmetic();
    //array of buttons to hold each button
    private static Button[] buttons = {new Button("7"), new Button("8"), new Button("9"), new Button("/"),
            new Button("4"), new Button("5"), new Button("6"), new Button("*"),
            new Button("1"), new Button("2"), new Button("3"), new Button("-"), new Button("0"),
            new Button("="), new Button("."), new Button("+"), new Button("(-)"),
            new Button("C"), new Button("CE"), new Button("("), new Button(")"), new Button("^")};

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        /**@throws ArithmeticException for Division by zero*/
        for (Button i : buttons) {
            //each button will be handled in event handler once pressed
            i.setOnAction(e -> {
                handle(e);
            });

        }
        //adding buttons to gui
        HBox txtBox = new HBox(numArea);
        txtBox.setAlignment(Pos.CENTER);
        HBox rowOne = new HBox(buttons[0], buttons[1], buttons[2], buttons[3]);
        rowOne.setAlignment(Pos.CENTER);
        HBox rowTwo = new HBox(buttons[4], buttons[5], buttons[6], buttons[7]);
        rowTwo.setAlignment(Pos.CENTER);
        HBox rowThree = new HBox(buttons[8], buttons[9], buttons[10], buttons[11], buttons[12]);
        rowThree.setAlignment(Pos.CENTER);
        HBox rowFour = new HBox(buttons[13], buttons[14], buttons[15], buttons[16]);
        rowFour.setAlignment(Pos.CENTER);
        HBox rowFive = new HBox(buttons[17], buttons[18], buttons[19], buttons[20], buttons[21]);
        rowFive.setAlignment(Pos.CENTER);


        //displaying gui
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.resize(400, 400);
        pane.add(txtBox, 0, 0);
        pane.add(rowOne, 0, 1);
        pane.add(rowTwo, 0, 2);
        pane.add(rowThree, 0, 3);
        pane.add(rowFour, 0, 4);
        pane.add(rowFive, 0, 5);
        Scene scene = new Scene(pane, 400, 400);
        primaryStage.setTitle("Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void handle(ActionEvent e) {
        Object tmp = e.getSource();
        if (tmp.equals(buttons[18])) {
            //deletes all elements of LinkedList
            infix.clear();
            display(infix);
        } else if (tmp.equals(buttons[13])) {
            /**evaluates upon pressing equals sign
             * @throws general exception for unsupported operations such as 1 +* 2
             * and other errors*/
            try {
                a.evalPostFix();
                display(a.resultList);
            } catch (Exception e2) {
                e2.printStackTrace();

            }
        } else if (tmp.equals(buttons[17])) {
            //deletes last element of LinkedList
            infix.removeLast();
            display(infix);
        } else if (tmp.equals(buttons[16])) {
            /**adds marker to later convert next number to negative*/
            infix.add("_");
        } else {
            //adds button pressed to array and displays
            infix.add(buttonMap.get(tmp));
            display(infix);
            /**if button pressed is an operand, adds a space to seperate operands
             * (including multi-digit and decimals) from operators and numbers*/
            if (!o.isDigit(buttonMap.get(tmp)) && !buttonMap.get(tmp).equals(".")) {
                infix.add(infix.size() - 1, " ");
                infix.add(infix.size(), " ");
            }
        }
    }

    private static Map<Button, String> buttonMap = new HashMap<Button, String>() {{
        /**@param Button - button[i] of buttons array
         * @param String - adds Button label to list once button is pressed*/
        put(buttons[0], "7");
        put(buttons[1], "8");
        put(buttons[2], "9");
        put(buttons[3], "/");
        put(buttons[4], "4");
        put(buttons[5], "5");
        put(buttons[6], "6");
        put(buttons[7], "*");
        put(buttons[8], "1");
        put(buttons[9], "2");
        put(buttons[10], "3");
        put(buttons[11], "-");
        put(buttons[12], "0");
        put(buttons[13], "=");
        put(buttons[14], ".");
        put(buttons[15], "+");
        put(buttons[16], "(-)");
        put(buttons[17], "C");
        put(buttons[18], "CE");
        put(buttons[19], "(");
        put(buttons[20], ")");
        put(buttons[21], "^");
    }};

    static <E> void display(List<E> list) {
        /**displays on calculator screen*/
        String s = " ";
        for (E e : list) {
            s += e + " ";

        }
        numArea.setText(s);
    }
}