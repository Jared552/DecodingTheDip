package Controller;

import Calculator.*;
import View.*;

import java.awt.*;

public class Controller {

    /**
     * The calculator for the stock prices
     */
    private Calculator calc;

    /**
     * The GUI view of the stock purchases
     */
    private View view;

    /**
     * The list of stock ticker symbols that we will track
     */
    private String[] stocksToTrack = new String[]{"V", "AZN", "AAPL", "AVGO", "MSFT", "AMZN", "GOOG", "JNJ", "XOM", "JPM", "PG", "NVDA", "HD", "CVX", "MA", "LLY", "TSLA", "PFE", "ABBV", "MRK", "META", "PEP", "KO", "BAC", "TMO", "WMT", "COST", "CSCO", "MCD", "ABT", "DHR", "ACN", "VZ", "NEE", "DIS", "WFC", "LIN", "ADBE", "PM", "NKE", "T", "NFLX", "IBM", "LOW", "GS"};

    /**
     * The number of standard deviations below the average to set the buy limit
     */
    private final double STANDARD_DEVIATIONS = 1.45;

    /**
     * Initialize the entire system
     * @param args - the standard java arguments
     */
    public static void main (String[] args){
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Controller controller = new Controller();   // instantiate the system
                    controller.start();  // start the system
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Creates a default Controller and all other parts
     */
    public Controller(){

        this.calc = new Calculator(stocksToTrack, STANDARD_DEVIATIONS, new ICalc2View() {

            @Override
            public void setYesterdayText(String yesterdayPerformanceText) {
                view.setYesterdayText(yesterdayPerformanceText);
            }
        });

        this.view = new View(new IView2Calc() {

        });
    }

    /**
     * Starts the Controller and all other parts
     */
    private void start(){
        this.view.start();
        this.calc.start();
    }
}