package View;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {

    private IView2Calc calcAdapter;

    private JTextArea todayPerformanceText;

    private JTextArea yesterdayPerformanceText;

    public View(IView2Calc adapter){
        this.calcAdapter = adapter;

        initGUI();
    }

    private void initGUI(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane(). setLayout(new GridLayout(1, 1));
        setBounds(100, 100, 450, 300);

        JTabbedPane tabs = new JTabbedPane();
        todayPerformanceText = new JTextArea();
        tabs.addTab("Today's Performance", todayPerformanceText);

        yesterdayPerformanceText = new JTextArea();
        tabs.addTab("Yesterday's Performance", yesterdayPerformanceText);

        add(tabs);
        setVisible(true);
    }

    public void setYesterdayText(String performanceString){
        this.yesterdayPerformanceText.setText(performanceString);
    }

    /**
     * Makes the frame visible.
     */
    public void start() {
        this.setVisible(true);
    }
}
