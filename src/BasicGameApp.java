//Basic Game Application
//Version 2
// Basic Object, Image, Movement
// character moves to the right.
// Threaded

//K. Chun 8/2018

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;


//*******************************************************************************
// Class Definition Section

public class BasicGameApp implements Runnable, KeyListener {
    public int HitCountArcherQ = 100;
    public int HitCountChar3 = 5;
    //Variable Definition Section
    //Declare the variables used in the program
    //You can set their initial values too

    //Sets the width and height of the program window
    final int WIDTH = 1000;
    final int HEIGHT = 700;

    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;

    public BufferStrategy bufferStrategy;
    public Image infernoTPic;
    public Image archerQPic;
    public Image healerPic;
    public Image backgroundPic;
    public boolean gameStart = false;

    //Declare the objects used in the program
    //These are things that are made up of more than one variable type
    private Character infernoT;
    private Character archerQ;
    private Character healer;

    Character [] infernoTArray = new Character[40];


    // Main method definition
    // This is the code that runs first and automatically
    public static void main(String[] args) {
        BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
        new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method
    }


    // Constructor Method
    // This has the same name as the class
    // This section is the setup portion of the program
    // Initialize your variables and construct your program objects here.
    public BasicGameApp() {

        setUpGraphics();

        //variable and objects
        //create (construct) the objects needed for the game and load up
        infernoTPic = Toolkit.getDefaultToolkit().getImage("inferno-tower-clash-of-clans.png"); //load the picture
        archerQPic = Toolkit.getDefaultToolkit().getImage("download.jpeg");
        healerPic = Toolkit.getDefaultToolkit().getImage("download (1).jpeg");
        backgroundPic = Toolkit.getDefaultToolkit().getImage("clash-of-clans-bases-2.jpg");
        infernoT = new Character((int)(Math.random()* 1000), (int)(Math.random()* 700));
        archerQ = new Character((int)(Math.random()* 1000), (int)(Math.random()* 700));
        archerQ.dx = 4;
        archerQ.dy = 7;
        healer = new Character((int)(Math.random()* 1000),(int)(Math.random()* 700));
        healer.dx = 10;
        healer.dy =2;
        infernoT.dx = 1;
        infernoT.dy = 1;

        for(int x = 0; x < infernoTArray.length; x++){
            infernoTArray[x] = new Character((int)(Math.random()* 900), (int)(Math.random()*600));
        }


    }// BasicGameApp()

    public void moveThings() {

//calling collisions
        //sets how each character moves
        collisions();
        infernoT.bounce();
        archerQ.control();
        healer.bounce();

        //this is how my inferno array moves. Wrap
        for(int y=0; y < infernoTArray.length; y++){
            infernoTArray[y].wrap();
        }

    }
//this is how I created the collisions each character has with each other in the game
    public void collisions() {
        if (infernoT.rec.intersects(archerQ.rec) && infernoT.isCrashing == false) {
            System.out.println("explosion");
            //interaction between infernoT and archerQ
            //they will change directions and archerQ will lose health
            infernoT.isCrashing = true;
            infernoT.dx = -infernoT.dx;
            infernoT.dy = -infernoT.dy;
            archerQ.dx = -archerQ.dx;
            archerQ.dy = -archerQ.dy;
            HitCountArcherQ = HitCountArcherQ - 1;
            if (HitCountArcherQ < 1) {
                archerQ.isAlive = false;
            }

        }
        //collision for tower and archerQ
            for(int b = 0; b < infernoTArray.length; b++){
                if(archerQ.rec.intersects(infernoTArray[b].rec)){
                    System.out.println("crashout");
                    HitCountArcherQ = HitCountArcherQ - 1;
                }
            }

        //checking to see if they are not intersecting
        if (!infernoT.rec.intersects(archerQ.rec)) {
            infernoT.isCrashing = false;
        }
        if (archerQ.rec.intersects(healer.rec) && archerQ.isCrashing == false) {
            System.out.println("explosion");
            //interaction between archerQ and healer
            //archerQ gains health
            archerQ.isCrashing = true;
            HitCountArcherQ= HitCountArcherQ +10;

        }
        //checking to see if they are not intersecting
        if (!archerQ.rec.intersects(healer.rec)&&healer.isAlive&& archerQ.isAlive) {
            archerQ.isCrashing = false;
        }
        if (infernoT.rec.intersects(healer.rec) && healer.isCrashing == false  &&healer.isAlive&& infernoT.isAlive){
            //interaction between healer and infernoT
            //healer loses health
            HitCountChar3 = HitCountChar3 -1;
            healer.isCrashing = true;
            infernoT.dx = -infernoT.dx;
            infernoT.dy = -infernoT.dy;
            healer.dx = -healer.dx;
            healer.dy = -healer.dy;
            System.out.println("crash");
        }
        //checking to see if they are not intersecting
        if (!infernoT.rec.intersects(healer.rec)) {
            healer.isCrashing = false;
        }
        //seeing if archerQ has health
        if(HitCountArcherQ <1){
            archerQ.isAlive = false;
        }
        //seeing if healer has health
        if(HitCountChar3 <1) {
            healer.isAlive = false;
        }


        //make collisions between characters
        //	if infernoT collides archerQ;
        //	archerQ isAlive = false;
    }
//*******************************************************************************
//User Method Section
//
// put your code to do things here.

    // main thread
    // this is the code that plays the game after you set things up
    public void run() {

        //for the moment we will loop things forever.
        while (true) {

            moveThings();  //move all the game objects
            render();  // paint the graphics
            pause(20); // sleep for 10 ms
        }
    }




    //Pauses or sleeps the computer for the amount specified in milliseconds
    public void pause(int time ){
        //sleep
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
    }

    //Graphics setup method
    private void setUpGraphics() {
        frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        canvas.addKeyListener(this);

        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");

    }





    //paints things on the screen using bufferStrategy
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);
        if (gameStart == true) {



            if (archerQ.isAlive == false) {
                g.drawString("GAME OVER", 450, 300);
            } else {


                g.drawImage(backgroundPic, 0, 0, WIDTH, HEIGHT, null);
                //draw the image of the characters infernoT, archerQ, and healer
                g.drawImage(infernoTPic, infernoT.xpos, infernoT.ypos, infernoT.width, infernoT.height, null);


                if (archerQ.isAlive == true) {
                    g.setColor(Color.WHITE);
                    //creates hitpoints for archerQ
                    g.fillRect(archerQ.xpos - 25, archerQ.ypos - 35, 75, 20);
                    g.setColor(Color.BLACK);

                    g.drawString("hit points " + HitCountArcherQ, archerQ.xpos - 20, archerQ.ypos - 20);
                    g.drawImage(archerQPic, archerQ.xpos, archerQ.ypos, archerQ.width, archerQ.height, null);
                }


                if (healer.isAlive == true) {
                    //creates hitpoints for healer
                    g.setColor(Color.WHITE);
                    g.fillRect(healer.xpos - 25, healer.ypos - 35, 75, 20);
                    g.setColor(Color.BLACK);

                    g.drawString("hit points " + HitCountChar3, healer.xpos - 20, healer.ypos - 20);
                    g.drawImage(healerPic, healer.xpos, healer.ypos, healer.width, healer.height, null);
                }
                for (int l = 0; l < infernoTArray.length; l++) {
                    g.drawImage(infernoTPic, infernoTArray[l].xpos, infernoTArray[l].ypos, infernoT.width, infernoT.height, null);
                }
//		g.drawImage(healerPic, healer.xpos, healer.ypos, healer.width, healer.height, null);
            }
        }
        else{
            g.drawString("Press Y to Start", 450, 300);

        }
        g.dispose();
        bufferStrategy.show();

}
//adding keys so you can move archerQ
    @Override
    public void keyTyped(KeyEvent e) {//dont use is bad

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("swag");
        System.out.println(e.getKeyChar());
        System.out.println(e.getKeyCode());
        //up 38
        //down 40
        //left 37
        //right 39

        if(e.getKeyCode() == 89) {
            gameStart = true;
        }

        if(e.getKeyCode() == 38) {
            System.out.println("going up");
            archerQ.up = true;

        }
        if(e.getKeyCode() == 39){
            System.out.println("going right");
            archerQ.right = true;

        }
        if(e.getKeyCode() == 40){
            System.out.println("going down");
            archerQ.down = true;
        }
        if(e.getKeyCode() == 37){
            System.out.println("going left");
            archerQ.left = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {


        if(e.getKeyCode()==38){
            archerQ.up = false;

        }
        if(e.getKeyCode()==39) {
            archerQ.right = false;
        }
        if(e.getKeyCode()==40){
            archerQ.down = false;

        }
        if(e.getKeyCode()==37){
            archerQ.left = false;

        }
    }

}