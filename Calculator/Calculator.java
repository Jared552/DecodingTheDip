package Calculator;
import Calculator.doc.Documenter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Calculator {

    private ICalc2View viewAdapter;

    private DataScraper scraper;

    private Documenter documenter;

    private String[] stocksToTrack;

    private double STANDARD_DEVIATIONS;

    private double budgetRemaining;

    private Map<String, Double> limits;

    public Calculator(String[] stocks, double sd, ICalc2View adapter){
        this.viewAdapter = adapter;
        this.stocksToTrack = stocks;
        this.STANDARD_DEVIATIONS = sd;
        this.budgetRemaining = 0.0;

        this.limits = new HashMap<>();
        this.scraper = new DataScraper();
        this.documenter = new Documenter();
    }

    private void sellCurrentHoldings(){
        String[] currentHoldingsLines = this.documenter.getCurrentHoldings();

        double budget = Double.parseDouble(currentHoldingsLines[currentHoldingsLines.length-1]);
        String day = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        String yesterdayPerformanceString = "Stock\tBought At\tSold At\tChange\n";
        double totalChange = 0.0;
        this.documenter.document(day);

        //Sells all stocks bought the previous day
        for(int i = 0 ; i < currentHoldingsLines.length-1 ; i++){
            String[] data = currentHoldingsLines[i].split(" ");
            int quant = Integer.parseInt(data[1]);
            double sellPrice = this.scraper.getOpeningPrice(data[0]);
            double buyPrice = Double.parseDouble(data[2]);
            totalChange += (sellPrice - buyPrice) * quant;
            yesterdayPerformanceString += data[0] + "\t" + buyPrice + "\t" + sellPrice + "\t" + (sellPrice - buyPrice) + "\n";
            this.documenter.document("Sold " + quant + " " + data[0] + " at " + sellPrice + " at open");
            budget += quant * sellPrice;
        }
        this.documenter.document("" + budget);
        yesterdayPerformanceString += "\t\t\t" + totalChange;

        this.budgetRemaining = budget;
        this.viewAdapter.setYesterdayText(yesterdayPerformanceString);
    }

    private void createBuyLimits(){

        for(String code : this.stocksToTrack){
            double open = this.scraper.getOpeningPrice(code);
            double[] stats = generateStatistics(code);
            double limit = open - stats[0] - stats[1] * 1.45;
            this.limits.put(code, limit);
        }
    }

    private double[] generateStatistics(String code){
        List<double[]> data = this.scraper.getHistoricalData(code);
        double[] toReturn = new double[2]; //mean and sd
        double sum = 0.0;

        //Calculates mean
        for(int i = 0 ; i < data.size() ; i++){
            double[] dayData = data.get(i);
            sum += dayData[0] - dayData[2];
        }

        toReturn[0] = sum / data.size();
        double sdSum = 0.0;

        //Calculates standard deviation
        for(int i = 0 ; i < data.size() ; i++){
            double[] dayData = data.get(i);
            sdSum += Math.pow((toReturn[0] - (dayData[0] - dayData[2])), 2);
        }
        toReturn[1] = Math.sqrt(sdSum / data.size());

        return toReturn;
    }

    public void start() {

        sellCurrentHoldings();
        createBuyLimits();
        
    }
}
