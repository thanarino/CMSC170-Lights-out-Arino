import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Jonathan Arino on 2/3/2017.
 */
public class State {
    private int[][] states;
    private LinkedList<Point> toggled;

    //constructor for a state object with a State parameter
    public State(State state) {
        //basically copy the contents of the State in the parameter to the new State
        this.states = new int[5][5];

        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                this.states[i][j] = state.states[i][j];
            }
        }

        toggled = new LinkedList<>();
        for(Point p : state.toggled) {
            this.toggled.add(p);
        }
    }

    //create a new state using a 2d String array. Used only once for initial state.
    public State(String[][] init) {
        states = new int[5][5];
        toggled = new LinkedList<>();

        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                states[i][j] = Integer.parseInt(init[i][j]);
            }
        }
    }

    //getter for the toggled attribute of the State object
    public LinkedList<Point> get_toggled() {
        return this.toggled;
    }

    //test a state if it is the win state
    public boolean goal_test() {
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                if(this.states[i][j] == 1) {
                    return false;
                }
            }
        }
        return true;
    }

    //returns a state wherein a switch has been activated
    //also adds a point to a state's toggled list
    public State result(Point p) {
        this.toggled.add(p);

        //invert x and y
        int y = (int) p.getX();
        int x = (int) p.getY();

        //toggle the switch at point p
        if (this.states[x][y] == 1) {
            this.states[x][y] = 0;
        } else {
            this.states[x][y] = 1;
        }

        //toggle the surrounding switches of point p.
        if (x + 1 < 5) {
            if (this.states[x+1][y] == 1) {
                this.states[x+1][y] = 0;
            } else {
                this.states[x+1][y] = 1;
            }
        }
        if (x - 1 > -1) {
            if (this.states[x-1][y] == 1) {
                this.states[x-1][y] = 0;
            } else {
                this.states[x-1][y] = 1;
            }
        }
        if (y - 1 > -1) {
            if (this.states[x][y-1] == 1) {
                this.states[x][y-1] = 0;
            } else {
                this.states[x][y-1] = 1;
            }
        }
        if (y + 1 < 5) {
            if (this.states[x][y+1] == 1) {
                this.states[x][y+1] = 0;
            } else {
                this.states[x][y+1] = 1;
            }
        }
        return this;

    }

    //returns a list of all the toggle-able switches available to a state
    public LinkedList<Point> actions() {
        LinkedList<Point> temp_list = new LinkedList<>();
        ArrayList<Point> to_remove = new ArrayList<>();

        //create a list of all possible points on the board
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                temp_list.add(new Point(i, j));
            }
        }

        //create a list of all the points to be removed
        for (Point p1 : temp_list) {
            for (Point p2 : this.toggled) {
                if (p1.getX() == p2.getX() && p1.getY() == p2.getY()) {
                    to_remove.add(p1);
                }
            }
        }

        //remove all the points in the 2nd list that is also in the 1st list.
        temp_list.removeAll(to_remove);

        return temp_list;
    }

}
