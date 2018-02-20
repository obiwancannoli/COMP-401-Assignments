package a8;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by dougied on 11/19/2016.
 */
public class FramePuzzleTest {

    public static void main(String[] args) throws IOException {
        Picture p = A8Helper.readFromURL("http://www.cs.unc.edu/~kmp/kmp.jpg");
        FramePuzzle framePuzzle = new FramePuzzle(p);

        JFrame main_frame = new JFrame();
        main_frame.setTitle("Assignment 8 Frame Puzzle");
        main_frame.setLayout(new BorderLayout());
        main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel main_panel = new JPanel(new BorderLayout());
        main_panel.add(framePuzzle, BorderLayout.CENTER);
        main_frame.setContentPane(main_panel);

        main_frame.pack();
        main_frame.setVisible(true);
    }

}
