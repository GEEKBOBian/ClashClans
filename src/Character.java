import java.awt.*;

/**
 * Created by chales on 11/6/2017.
 */
public class Character {

    //VARIABLE DECLARATION SECTION
    //Here's where you state which variables you are going to use.
    public String name;                //holds the name of the hero
    public int xpos;                //the x position
    public int ypos;                //the y position
    public int dx;                    //the speed of the hero in the x direction
    public int dy;                    //the speed of the hero in the y direction
    public int width;
    public int height;
    public boolean isAlive;            //a boolean to denote if the hero is alive or dead.//a boolean to denote if the hero is alive or dead.
    public Rectangle rec;
    public boolean isCrashing;

    public void bounce() {//bounce off east wall
        if (xpos > 900) {
            dx = -dx;
        }
        if (xpos < 0) {//bounce off west wall
            dx = -dx;
        }
        if (ypos > 600) {//bounce off south wall
            dy = -dy;
        }
        if (ypos < 0) {//bounce off north wall
            dy = -dy;
        }
        xpos = xpos + dx;
        ypos = ypos + dy;

        rec = new Rectangle(xpos, ypos, width, height);
    }

    public void wrap() {
        //hits east wall
        if (xpos > 1000) {
            xpos = 0;
        }
        if (xpos < 0) {
            xpos = 1000;
        }
        if (ypos > 700) {
            ypos = 0;
        }
        if (ypos < 0) {
            ypos = 700;
        }
        xpos = xpos + dx;
        ypos = ypos + dy;

        rec = new Rectangle(xpos, ypos, width, height);
    }

    // METHOD DEFINITION SECTION

    // Constructor Definition
    // A constructor builds the object when called and sets variable values.


    //This is a SECOND constructor that takes 3 parameters.  This allows us to specify the hero's name and position when we build it.
    // if you put in a String, an int and an int the program will use this constructor instead of the one above.
    public Character(int pXpos, int pYpos) {
        xpos = pXpos;
        ypos = pYpos;
        dx =1;
        dy =1;
        width = 60;
        height = 60;
        isAlive = true;
        rec = new Rectangle(xpos, ypos, width, height);


    } // constructor


    //The move method.  Everytime this is run (or "called") the hero's x position and y position change by dx and dy
    public void move() {
        xpos = xpos + dx;
        ypos = ypos + dy;

    }
}






