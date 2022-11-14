package CaveMan;

import TileEngine.TERenderer;
import TileEngine.TETile;
import TileEngine.Tileset;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import java.util.Random;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import Core.Game;


public class RandomWorld implements java.io.Serializable{
    protected int width;
    protected int height;
    protected int recNum;
    public TETile[][] world;
    public long SEED;
    public Random RANDOM;
    public int[] playerPosition;
    public boolean gameWin = false;
    public int flowerCount = 0;

    public RandomWorld(long seed){
        width = 100;
        height = 55;
        world = new TETile[width][height];
        recNum = 0;
        SEED = seed;
        RANDOM = new Random(SEED);
        int[] position = new int[2];
        playerPosition = position;
        gameWin = false;
        flowerCount = 0;
    }

    public void backGround(){
        //Fill the world with NOTHING block as background.
        for(int x = 0; x < this.width; x++){
            for(int y = 0; y < this.height; y++){
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    public int[] randomRecGenerator(){
        //Generate an int array of length 2, which accordingly represents the width and heigth of an rectangle.
        int[] random = new int[3];
        int num1;
        int num2;
        do{
            num1 = RANDOM.nextInt(10);
            num2 = RANDOM.nextInt(10);
        }while(num1 < 5 && num2 < 5);
        random[0] = num1;
        random[1] = num2;
        random[2] = 1;
        return random;
    }

    public int[] randomHallwayGenerator(){
        int[] random = new int[3];
        int number = RANDOM.nextInt(10);
        int num1;
        int num2;
        random[2] = 2; 
        if(number < 5){
            do{
                num1 = RANDOM.nextInt(20);
                num2 = 5;
            }while(num1 < 10);
            random[0] = num1;
            random[1] = num2;
        }else{
            do{
                num1 = 5;
                num2 = RANDOM.nextInt(20);
            }while(num2 < 10);
            random[0] = num1;
            random[1] = num2;
        }
        return random;
    }

    public int[] positionGeneratorForPlayer(){
        int roll = RANDOM.nextInt(4);
        int i = 0;
        int j = 0;
        if(roll == 0){
            for(int x = 20; x < this.width-20; x++){
                for(int y = 20; y < this.height-20; y++){
                    if(world[x][y].equals(Tileset.FLOOR)){
                        i = x;
                        j = y;
                }
                }
            }
        }
        if(roll == 1){
            for(int x = this.width-20; x > 20; x--){
                for(int y = 20; y < this.height-20; y++){
                    if(world[x][y].equals(Tileset.FLOOR)){
                        i = x;
                        j = y;
                }
                }
            }
        }
        if(roll == 2){
            for(int x = this.width-20; x > 20; x--){
                for(int y = this.height-20; y > 20; y--){
                    if(world[x][y].equals(Tileset.FLOOR)){
                        i = x;
                        j = y;
                }
                }
            }
        }
        if(roll == 3){
            for(int x = 20; x < this.width-20; x++){
                for(int y = this.height-20; y > 20; y--){
                    if(world[x][y].equals(Tileset.FLOOR)){
                        i = x;
                        j = y;
                }
                }
            }
        }
        return positionArrGenerator(i, j);
    }
    
    public int[] positionGeneratorVer2(int[] Rec){
        int[] position = new int[2];
        if(this.recNum < 100){
            position[0] = RANDOM.nextInt(width-10);
            position[1] = RANDOM.nextInt(height-10);
            return position;
        }else{
            boolean positionFOUND = false;
            while(!positionFOUND){
                int roll = RANDOM.nextInt(4);
                if(roll == 0){
                    for(int x = 20; x < this.width-20; x++){
                        for(int y = 20; y < this.height-20; y++){
                        position[0] = x;
                        position[1] = y;
                        int Roll = RANDOM.nextInt(10);
                        if(world[x][y].equals(Tileset.FLOOR) && isWall(position) && (Roll == 1 || Roll == 2)){
                            positionFOUND = true;
                            return position;
                        }
                        }
                    }
                }
                if(roll == 1){
                    for(int x = this.width-20; x > 20; x--){
                        for(int y = 20; y < this.height-20; y++){
                            position[0] = x;
                            position[1] = y;
                            int Roll = RANDOM.nextInt(10);
                            if(world[x][y].equals(Tileset.FLOOR) && isWall(position) && (Roll == 1 || Roll == 2)){
                                positionFOUND = true;
                                return position;
                            }
                        }
                    }
                }
                if(roll == 2){
                    for(int x = 20; x < this.width - 20; x--){
                        for(int y = this.height-20; y > 20; y--){
                            position[0] = x;
                            position[1] = y;
                            int Roll = RANDOM.nextInt(10);
                            if(world[x][y].equals(Tileset.FLOOR) && isWall(position) && (Roll == 1 || Roll == 2)){
                                positionFOUND = true;
                                return position;
                            }
                        }
                    }
                }
                if(roll == 3){
                    for(int x = this.width-20; x > 20; x--){
                    for(int y = this.height-20; y > 20; y--){
                        position[0] = x;
                        position[1] = y;
                        int Roll = RANDOM.nextInt(10);
                        if(world[x][y].equals(Tileset.FLOOR) && isWall(position) && (Roll == 1 || Roll == 2)){
                            positionFOUND = true;
                            return position;
                        }
                    }
                    }
                }
            }
        }
        return position;
    }

    public int[] positionGeneratorVer3(int[] Rec){
        int[] position = new int[2];
        if(this.recNum == 0){
            int ROLL = RANDOM.nextInt(4);
            if(ROLL == 0){
                return positionArrGenerator(10, 10);
            }
            if(ROLL == 1){
                return positionArrGenerator(this.width-10, 10);
            }
            if(ROLL == 2){
                return positionArrGenerator(10, this.height-10);
            }
            if(ROLL == 3){
                return positionArrGenerator(this.width-10, this.height-10);
            }
        }else{
            int ROLL2 = RANDOM.nextInt(2);
            if(ROLL2 == 0){
                for(int x = this.width-1; x > 0; x--){
                    for(int y = this.height-1; y > 0; y--){
                        if(world[x][y].equals(Tileset.FLOOR)){
                            position = positionArrGenerator(x-2, y);
                            return position;
                        }
                    }
                }
            }else{
                for(int y = this.height-1; y > 0; y--){
                    for(int x = this.width-1; x > 0; x--){
                        if(world[x][y].equals(Tileset.FLOOR)){
                            position = positionArrGenerator(x, y-2);
                            return position;
                        }
                    }
                }
                }
            }
        return position;    
        }
      
    public void drawTheGround(int numberOfRec){
        try{
           while(this.recNum < numberOfRec){
            int[] recData = randomRecGenerator();
            int[] hallwayData = randomHallwayGenerator();
            int[] posData = positionGeneratorVer2(recData);
            this.recNum++;
            int[] posData2 = positionGeneratorVer2(hallwayData);
            this.recNum++;
            for(int i = 0; i < recData[0]; i++){
                for(int j = 0; j < recData[1]; j++){
                    world[posData[0]+i][posData[1]+j] = Tileset.FLOOR;
                }
            }
            for(int i = 0; i < hallwayData[0]; i++){
                for(int j = 0; j < hallwayData[1]; j++){
                    world[posData2[0]+i][posData2[1]+j] = Tileset.FLOOR;
                }
            }
        }
        }catch(ArrayIndexOutOfBoundsException exception){
            drawTheGround(numberOfRec);
        }
        }
        
    public boolean isWall(int[] position){
        try{
            int xPos = position[0];
        int yPos = position[1];
        boolean isWall = false;
        if(world[xPos-1][yPos].equals(Tileset.NOTHING)){
            return true;
        }
        if(world[xPos+1][yPos].equals(Tileset.NOTHING)){
            return true;
        }
        if(world[xPos][yPos-1].equals(Tileset.NOTHING)){
            return true;
        }
        if(world[xPos][yPos+1].equals(Tileset.NOTHING)){
            return true;
        }
        if(world[xPos+1][yPos+1].equals(Tileset.NOTHING)){
            return true;
        }
        if(world[xPos-1][yPos-1].equals(Tileset.NOTHING)){
            return true;
        }
        if(world[xPos+1][yPos-1].equals(Tileset.NOTHING)){
            return true;
        }
        if(world[xPos-1][yPos+1].equals(Tileset.NOTHING)){
            return true;
        }
        return isWall;
        }catch(IndexOutOfBoundsException exception){
            return true;
        }
        
    }

    public void addWall(){
        for(int i = 0; i < this.width; i++){
            for(int j = 0; j < this. height; j++){
                int[] position = new int[2];
                position[0] = i;
                position[1] = j;
                if(world[i][j].equals(Tileset.FLOOR) && isWall(position)){
                    world[i][j] = Tileset.WALL;
                }
            }
        }
    }
    
    public int[] positionArrGenerator(int int1, int int2){
        int[] intArr = new int[2];
        intArr[0] = int1;
        intArr[1] = int2;
        return intArr;
    }

    public boolean isInvalidblock(int xPos, int yPos){
        try{
            boolean isInvalid = true;
            if(world[xPos-1][yPos].equals(Tileset.FLOOR)){
                return false;
            }
            if(world[xPos-1][yPos-1].equals(Tileset.FLOOR)){
                return false;
            }
            if(world[xPos][yPos-1].equals(Tileset.FLOOR)){
                return false;
            }
            if(world[xPos+1][yPos-1].equals(Tileset.FLOOR)){
                return false;
            }
            if(world[xPos+1][yPos].equals(Tileset.FLOOR)){
                return false;
            }
            if(world[xPos+1][yPos+1].equals(Tileset.FLOOR)){
                return false;
            }
            if(world[xPos][yPos+1].equals(Tileset.FLOOR)){
                return false;
            }
            if(world[xPos-1][yPos+1].equals(Tileset.FLOOR)){
                return false;
            }
            return isInvalid;
        }catch(IndexOutOfBoundsException exception){
            return false;
        }
    }

    public void clearInvalidBlock(){
        for(int x = 0; x < this.width; x++){
            for(int y = 0; y < this.height; y++){
                if(world[x][y].equals(Tileset.WALL) && isInvalidblock(x, y)){
                    this.world[x][y] = Tileset.NOTHING;
                }
            }
        }
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        String inputString = "";
        while (inputString.length() < n) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            inputString += String.valueOf(key);
        }
        return inputString;
    }

    public void setUpPlayer(){
        int[] position = positionGeneratorForPlayer();
        world[position[0]][position[1]] = Tileset.PLAYER;
        playerPosition = position;
    }
    
    public void updatePlayerPosition(String command){
        if(command.equals("w")){
            world[playerPosition[0]][playerPosition[1]] = Tileset.FLOOR;
            playerPosition[1] = playerPosition[1] + 1;
            world[playerPosition[0]][playerPosition[1]] = Tileset.PLAYER;
        }
        if(command.equals("a")){
            world[playerPosition[0]][playerPosition[1]] = Tileset.FLOOR;
            playerPosition[0] = playerPosition[0] - 1;
            world[playerPosition[0]][playerPosition[1]] = Tileset.PLAYER;
        }
        if(command.equals("s")){
            world[playerPosition[0]][playerPosition[1]] = Tileset.FLOOR;
            playerPosition[1] = playerPosition[1] - 1;
            world[playerPosition[0]][playerPosition[1]] = Tileset.PLAYER;
        }
        if(command.equals("d")){
            world[playerPosition[0]][playerPosition[1]] = Tileset.FLOOR;
            playerPosition[0] = playerPosition[0] + 1;
            world[playerPosition[0]][playerPosition[1]] = Tileset.PLAYER;
        }
    }

    public boolean OperatingSystem(){
            String command = solicitNCharsInput(1);
            if(command.equalsIgnoreCase("w") && world[playerPosition[0]][playerPosition[1]+1].equals(Tileset.FLOOR)){
                updatePlayerPosition("w");
            }else if(command.equals("w") && world[playerPosition[0]][playerPosition[1]+1].equals(Tileset.FLOWER)){
                updatePlayerPosition("w");
                flowerCount++;
            }
            if(command.equalsIgnoreCase("a")  && world[playerPosition[0]-1][playerPosition[1]].equals(Tileset.FLOOR)){
                updatePlayerPosition("a");
            }else if(command.equals("a")  && world[playerPosition[0]-1][playerPosition[1]].equals(Tileset.FLOWER)){
                updatePlayerPosition("a");
                flowerCount++;
            }
            if(command.equalsIgnoreCase("s") && world[playerPosition[0]][playerPosition[1]-1].equals(Tileset.FLOOR)){
                updatePlayerPosition("s");
            }else if(command.equals("s") && world[playerPosition[0]][playerPosition[1]-1].equals(Tileset.FLOWER)){
                updatePlayerPosition("s");
                flowerCount++;
            }
            if(command.equalsIgnoreCase("d") && world[playerPosition[0]+1][playerPosition[1]].equals(Tileset.FLOOR)){
                updatePlayerPosition("d");
            }else if(command.equals("d") && world[playerPosition[0]+1][playerPosition[1]].equals(Tileset.FLOWER)){
                updatePlayerPosition("d");
                flowerCount++;
            }
            if(command.equalsIgnoreCase("q")){
                return false;
            }
            return true;
        }
    
    public void setUpFlower(){
        int count = 0;
        while(count < 20){
            int[] position = scatterIntoTheMapVer2();
            world[position[0]][position[1]] = Tileset.FLOWER;
            count++;
        }
    }

    public int[] scatterIntoTheMap(){
        int[] position = new int[2];
        int Roll = RANDOM.nextInt(3);
        if(Roll == 0){
            for(int x = 0; x < this.width; x++){
            for(int y = 0; y < this.height; y++){
                if(RANDOM.nextInt(200) == 1 && world[x][y].equals(Tileset.FLOOR)){
                    position[0] = x;
                    position[1] = y;
                    break;
                }
            }
        }
        }
        if(Roll == 1){
            for(int x = this.width-1; x > 0; x--){
            for(int y = 0; y < this.height; y++){
                if(RANDOM.nextInt(200) == 1 && world[x][y].equals(Tileset.FLOOR)){
                    position[0] = x;
                    position[1] = y;
                    break;
                }
            }
        }
        }
        if(Roll == 2){
            for(int x = this.width-1; x > 0; x--){
            for(int y = this.height-1; y > 0; y--){
                if(RANDOM.nextInt(200) == 1 && world[x][y].equals(Tileset.FLOOR)){
                    position[0] = x;
                    position[1] = y;
                    break;
                }
            }
        }
        }
        if(Roll == 3){
            for(int x = 0; x < this.width; x++){
            for(int y = this.height-1; y > 0; y--){
                if(RANDOM.nextInt(200) == 1 && world[x][y].equals(Tileset.FLOOR)){
                    position[0] = x;
                    position[1] = y;
                    break;
                }
            }
        }
        }
        return position;
    }

    public int[] scatterIntoTheMapVer2(){
        while(true){
            int x = RANDOM.nextInt(width);
            int y = RANDOM.nextInt(height);
            if(world[x][y].equals(Tileset.FLOOR)){
               return positionArrGenerator(x, y);
            }
        }
    }

    public void GameUI(){
        StdDraw.setPenColor(Color.white);
        StdDraw.setXscale(0,this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.textLeft(0, 54,"FlowerCount: " + flowerCount);
        StdDraw.show();
    }
    
    public void quitGame(){
        saveGame();
        StdDraw.clear();
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.white);
        StdDraw.setPenRadius(4);
        StdDraw.text(this.width/2,this.height/2,"You have quit the game, now you can close the window");
        StdDraw.show();
    }

    public void saveGame(){
        RandomWorld savedWorld = this;
        try {
            FileOutputStream fileOut =
            new FileOutputStream("C:\\Users\\Ludwig\\Desktop\\Cave-Man\\Archive\\data.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(savedWorld);
            out.close();
            fileOut.close();
         } catch (IOException i) {
            i.printStackTrace();
         }
      }

    public void NewGameprocedures(){
        int WIDTH = 100;
        int HEIGHT = 55;
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        this.backGround();
        int number = this.RANDOM.nextInt(300);
        this.drawTheGround(number);
        this.addWall();
        this.clearInvalidBlock();
        this.setUpPlayer();
        this.setUpFlower();
        ter.renderFrame(this.world);
        boolean contin = true;
        while(!gameWin && contin){
            this.GameUI();
            contin = this.OperatingSystem();
            ter.renderFrame(this.world);
        }
        if(!contin){
            this.quitGame();
        }
    }

    public void LoadGameprocedures(){
        int WIDTH = 100;
        int HEIGHT = 55;
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(this.world);
        boolean contin = true;
        while(!gameWin && contin){
            this.GameUI();
            contin = this.OperatingSystem();
            ter.renderFrame(this.world);
        }
        if(!contin){
            this.quitGame();
        }
    }
    public static void main(String args[]){
        
    }
}
