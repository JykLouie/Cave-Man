package Core;

import TileEngine.TERenderer;
import TileEngine.TETile;
import javafx.scene.text.Font;
import CaveMan.RandomWorld;
import CaveMan.StdDraw;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.Object;
import java.util.Random;


public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    public static final String allInterger = "1234567890";
    public Random ran = new Random();

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        StdDraw.setCanvasSize(800,800);
        StdDraw.clear();
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.white);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0,HEIGHT);
        StdDraw.text(this.WIDTH/2,this.HEIGHT-HEIGHT/3,"CaveMan");
        StdDraw.text(this.WIDTH/2,this.HEIGHT/2,"New Game (N)");
        StdDraw.text(this.WIDTH/2,this.HEIGHT/2 - 3 ,"Load Game (L)");
        StdDraw.text(this.WIDTH/2,this.HEIGHT/2 - 6,"Quit Game (Q)");
        StdDraw.show();
        boolean validKey = false;
        while(!validKey){
            String input = solicitNCharsInput(1);
            if(input.equals("n")){
                validKey = true;
                StdDraw.clear();
                StdDraw.clear(Color.black);
                StdDraw.setPenColor(Color.white);
                StdDraw.setXscale(0, WIDTH);
                StdDraw.setYscale(0,HEIGHT);
                StdDraw.text(this.WIDTH/2,this.HEIGHT-HEIGHT/3,"Please enter a random seed(An integer)");
                StdDraw.show();
                StdDraw.pause(3000);
                //Bug needs to be fixed: User may enter an non integer value which will cause error.
                String strSeed = solicitNCharsInput2(1);
                while(!allInterger.contains(strSeed)){
                    StdDraw.text(this.WIDTH/2,this.HEIGHT-HEIGHT/3,"Please enter a random seed(An integer)");
                    StdDraw.show();
                    StdDraw.pause(3000);
                    strSeed = solicitNCharsInput2(1);
                }
                int seed = Integer.valueOf(strSeed) + ran.nextInt(20);
                RandomWorld worldmap = new RandomWorld(seed);
                worldmap.NewGameprocedures();
            }
            if(input.equalsIgnoreCase("l")){
                try{
                    validKey = true;
                    RandomWorld world = LoadGame();
                    world.LoadGameprocedures();
                }catch(Exception exception){
                    System.out.println(exception.toString());
                    StdDraw.clear();
                    StdDraw.clear(Color.black);
                    StdDraw.setPenColor(Color.white);
                    StdDraw.setXscale(0, WIDTH);
                    StdDraw.setYscale(0,HEIGHT);
                    StdDraw.text(this.WIDTH/2,this.HEIGHT-HEIGHT/3,"There is no saved game, press n if you want to start a New Game");
                    String n = solicitNCharsInput(1);
                    while(!n.equals("n")){
                        n = solicitNCharsInput(1);
                    }
                    StdDraw.clear();
                    StdDraw.clear(Color.black);
                    StdDraw.setPenColor(Color.white);
                    StdDraw.setXscale(0, WIDTH);
                    StdDraw.setYscale(0,HEIGHT);
                    StdDraw.text(this.WIDTH/2,this.HEIGHT-HEIGHT/3,"Please enter a random seed(An integer)");
                    StdDraw.show();
                    StdDraw.pause(3000);
                    //Bug needs to be fixed: User may enter an non integer value which will cause error.
                    String strSeed = solicitNCharsInput2(1);
                    while(!allInterger.contains(strSeed)){
                        StdDraw.text(this.WIDTH/2,this.HEIGHT-HEIGHT/3,"Please enter a random seed(An integer)");
                        StdDraw.show();
                        StdDraw.pause(3000);
                        strSeed = solicitNCharsInput2(1);
                    }
                    int seed = Integer.valueOf(strSeed) + ran.nextInt(20);
                    RandomWorld worldmap = new RandomWorld(seed);
                    worldmap.NewGameprocedures();
                }
                
            }
            if(input.equalsIgnoreCase("q")){
                validKey = true;
                StdDraw.clear();
                StdDraw.clear(Color.black);
                StdDraw.setPenColor(Color.white);
                StdDraw.setXscale(0, WIDTH);
                StdDraw.setYscale(0,HEIGHT);
                StdDraw.text(this.WIDTH/2,this.HEIGHT-HEIGHT/3,"Game quited, Please close this window");
            }
            continue;
        }
        
    }

    public String solicitNCharsInput(int n) {
        //Read n letters of player input
        String inputString = "";
        while(inputString.length() < n){
            if(!StdDraw.hasNextKeyTyped()){
                continue;
            }
            char str = StdDraw.nextKeyTyped();
            inputString = inputString + str;
        }
        return inputString;
    }

    public String solicitNCharsInput2(int n) {
        //Read n letters of player input, and draw it on the canvas
        String inputString = "";
        drawFrame(inputString);
        while(inputString.length() < n){
            if(!StdDraw.hasNextKeyTyped()){
                continue;
            }
            char str = StdDraw.nextKeyTyped();
            inputString = inputString + str;
            drawFrame(inputString);
            StdDraw.pause(250);
        }
        return inputString;
    }

    public void drawFrame(String s) {
        StdDraw.clear();
        StdDraw.clear(Color.black);
        StdDraw.setFont();
        StdDraw.setPenColor(Color.white);
        StdDraw.text(this.WIDTH/2,this.HEIGHT/2,s);
        StdDraw.show();
        }

    public RandomWorld LoadGame(){
            RandomWorld world = null;
        try {
            FileInputStream fileIn = new FileInputStream("C:\\Users\\Ludwig\\Desktop\\Cave-Man\\Archive\\data.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            world = (RandomWorld) in.readObject();
            in.close();
            fileIn.close();
          } catch (IOException i) {
            i.printStackTrace();
          } catch (ClassNotFoundException c) {
            System.out.println("class not found");
            c.printStackTrace();
          }
            return world;
        }

    public TETile[][] playWithInputString(String input) {
        // return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        Long seed = new Long(input); 
        RandomWorld worldmap = new RandomWorld(seed);
        TETile[][] finalWorldFrame = worldmap.world;
        return finalWorldFrame;
    }

    public static void main(String args[]){
        Game game = new Game();
        game.playWithKeyboard();
    }
}
