import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by Jonathan Arino on 2/3/2017.
 */
public class DFS extends AbstractAction {

    private JButton button_dfs;
    private JFrame window2;
    private static Button[][] array;
    private static String[][] strings;
    private State initial_state;
    private State final_state;
    private LinkedList<Point> final_toggle;

    public DFS(String text, String[][] strings) {
        button_dfs = new JButton();
        this.strings = strings;
        putValue(NAME, text);
        button_dfs.setAction(this);
    }

    public JButton getButton_dfs () {
        return button_dfs;
    }

    public Button[][] create_buttons(){
        array = new Button[5][5];
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                Button button = new Button();
                array[i][j] = button;
            }
        }
        return array;
    }

    public JPanel create_panel(Button[][] buttons) {
        JPanel jpanel = new JPanel();
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                jpanel.add(buttons[i][j].get_button());
            }
        }
        return jpanel;
    }

    public JFrame create_window () {
        window2 = new JFrame("Lights Out : DFS-solved");
        window2.setPreferredSize(new Dimension(500,500));
        window2.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        window2.add(create_panel(create_buttons()));
        window2.setVisible(true);
        window2.setResizable(false);
        window2.pack();
        return window2;
    }

    public void write_file(String string) {
        BufferedWriter bw = null;

        try {
            File file = new File("lightsout.out");

            if(!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            bw.write(string);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(bw!=null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public State DFS(State init_state) {
        //frontier contains all the possible states that is reachable by the program using its current state.
        LinkedList<State> frontier = new LinkedList<>();
        //add the initial state (no turned switches) to the frontier
        frontier.addLast(init_state);

        while(!frontier.isEmpty()) {
            State current = new State(frontier.removeLast());
            //create a new state wherein we remove and use the first element in the frontier
            if(current.goal_test()) return current;
                //test the current state if it is the goal state
            else {
                //for all possible actions in the current board
                for(Point p : current.actions()) {
                    //create a new state for each possible switch to be toggled
                    State current2 = new State(current);
                    /*and add that new state (with the switch turned & the board reacting accordingly) to the end of
                    frontier.*/
                    frontier.addLast(current2.result(p));
                }
            }
        }
        //if the program does not find a win state (which means something is wrong)
        return null;
    }

    //create a string to be written to the file
    public String create_string(LinkedList<Point> p) {
        String[][] strings = new String[5][5];
        String string = "";

        //fill a 2-dimensional array with the string "0"
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                strings[i][j] = "0";
            }
        }

        //for every point in the linked list supplied in the parameter,
        //map that to the 2d array then replace the value to "1"
        for(Point point : p) {
            strings[(int) point.getY()][(int) point.getX()] = "1";
        }

        //append an empty string with the contents of the 2d array
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                string = string + strings[i][j] + " ";
            }
            string+="\r\n";
        }
        return string;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.create_window();

        try {
            initial_state = new State(strings);
        } catch ( Exception ex ) {
            ex.printStackTrace();
        } finally {
            //the DFS method returns the last possible state, which is the win state.
            final_state = DFS(initial_state);
            if(final_state == null) {
                //if the program's output is null, there is something wrong.
                System.out.println("null");
            } else {
                //get the list of toggled switches in the win state.
                final_toggle = final_state.get_toggled();

                //first create the string to be printed then print it to the file.
                write_file(create_string(final_toggle));

                //list all the points used in solving the puzzle in the console.
                for(Point p : final_toggle) {
                    System.out.println(p.toString());
                    array[(int) p.getX()][(int) p.getY()].set_state(0);
                }
            }
        }
    }
}
