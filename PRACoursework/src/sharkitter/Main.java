package sharkitter;

import sharkitter.view.MenuFrame;
import sharkitter.view.SearchFrame;

import javax.swing.*;
import java.io.File;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new MenuFrame();
        frame.setVisible(true);

        SearchFrame sf = new SearchFrame();
        sf.setVisible(true);
    }
}
