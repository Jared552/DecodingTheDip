package Calculator.doc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Documenter {
    //C:\Users\jared\IdeaProjects\DecodingTheDip\Calculator\doc

    public static String[] getCurrentHoldings(){
        String fileName = new File("").getAbsolutePath() + "\\Calculator\\doc\\MinBuyingCurrentHoldings.txt";
        String contents = "";

        try {
            contents = Files.readString(Paths.get(fileName));
        } catch (IOException e){
            System.out.println("No file");
        }

        String[] lines = contents.split("\n");
        return lines;
    }

    public static void document(String toDocument){
        System.out.println(toDocument);
        try {
            String fileName = new File("").getAbsolutePath() + "\\Calculator\\doc\\MinBuyingDocumentation.txt";
            FileWriter toWrite = new FileWriter(fileName, true);
            toWrite.append(toDocument + "\n");
            toWrite.close();
        } catch (IOException e){
            System.out.println("No file");
        }
    }

    public static void updateHoldings(List<String> codes, List<Integer> numHolding, List<Double> boughtPrices, double budget){
        resetCurrentHoldings();

        for(int i = 0 ; i < codes.size() ; i++){
            updateCurrentHoldings(codes.get(i) + " " + numHolding.get(i) + " " + boughtPrices.get(i) + "\n");
        }
        updateCurrentHoldings(budget + "");
    }

    public static void updateCurrentHoldings(String toDocument){
        try {
            String fileName = new File("").getAbsolutePath() + "\\Calculator\\doc\\MinBuyingCurrentHoldings.txt";
            FileWriter toWrite = new FileWriter(fileName, true);
            toWrite.append(toDocument);
            toWrite.close();
        } catch (IOException e){
            System.out.println("No file");
        }
    }

    public static void resetCurrentHoldings(){
        try {
            String fileName = new File("").getAbsolutePath() + "\\Calculator\\doc\\MinBuyingCurrentHoldings.txt";
            FileWriter toWrite = new FileWriter(fileName, false);
            PrintWriter pw = new PrintWriter(toWrite, false);
            pw.flush();
            pw.close();
        } catch (IOException e){
            System.out.println("No file");
        }
    }
}
