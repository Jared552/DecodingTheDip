package View;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {

    private IView2Calc calcAdapter;

    public View(IView2Calc adapter){
        this.calcAdapter = adapter;

        initGUI();
    }

    private void initGUI(){

    }

    /**
     * Makes the frame visible.
     */
    public void start() {
        this.setVisible(true);
    }
}
