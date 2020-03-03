package sample;
import java.io.*;
import java.util.Random;

class RandomWordsGenerator {

    String readWord(){
        String word = "";

        try{
            File file = new File("src/english.txt");

            RandomAccessFile randFile = new RandomAccessFile(file, "r");
            int fileLen = (int)randFile.length();
            Random rand = new Random();
            int randNum = rand.nextInt(fileLen);
            randFile.seek(randNum);
            randFile.readLine();
            word = randFile.readLine();
            while (word.length() < 5) {
                word = randFile.readLine();
            }
        } catch(Exception e){
            System.out.println("Error reading words");
        }
        return word;
    }
}
