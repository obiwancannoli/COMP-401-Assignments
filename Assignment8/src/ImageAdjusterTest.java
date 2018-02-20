package a8;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


/**
 * Created by dougied on 11/14/2016.
 */
public class ImageAdjusterTest {
    public static void main(String[] args) throws IOException {
        Picture p = A8Helper.readFromURL("http://www.cs.unc.edu/~kmp/kmp.jpg");
        ImageAdjuster imageAdjuster = new ImageAdjuster(p);

        JFrame main_frame = new JFrame();
        main_frame.setTitle("Assignment 8 Image Adjuster");
        main_frame.setLayout(new BorderLayout());
        main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel main_panel = new JPanel(new BorderLayout());
        main_panel.add(imageAdjuster, BorderLayout.CENTER);
        main_panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        main_frame.setContentPane(main_panel);

        main_frame.pack();
        main_frame.setVisible(true);



    }
}
