import utils.Constants;

import javax.swing.*;

public class Window extends JFrame {
    public Window () {
        this.setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.add(new MainPanel(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        this.setVisible(true);
    }
}
