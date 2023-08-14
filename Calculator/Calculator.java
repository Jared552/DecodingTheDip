package Calculator;
import Calculator.doc.Documenter;

public class Calculator {

    private ICalc2View viewAdapter;

    private DataScraper scraper;

    private Documenter documenter;

    public Calculator(ICalc2View adapter){
        this.viewAdapter = adapter;

        scraper = new DataScraper();

        documenter = new Documenter();
    }

    public void start() {

    }
}
