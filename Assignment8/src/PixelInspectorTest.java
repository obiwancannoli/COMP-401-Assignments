package a8;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by dougied on 11/12/2016.
 */
public class PixelInspectorTest {

    public static void main(String[] args) throws IOException {
        Picture p = A8Helper.readFromURL("http://www.cs.unc.edu/~kmp/kmp.jpg");
        PixelInspector pixelInspector = new PixelInspector(p);

        JFrame main_frame = new JFrame();
        main_frame.setTitle("Assignment 8 Pixel Inspector");
        main_frame.setLayout(new BorderLayout());
        main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel center_panel = new JPanel();
        center_panel.setLayout(new BorderLayout());
        center_panel.add(pixelInspector, BorderLayout.CENTER);
        center_panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        main_frame.setContentPane(center_panel);



        main_frame.pack();
        main_frame.setVisible(true);

    }
}
