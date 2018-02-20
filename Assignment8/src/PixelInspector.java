package a8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by dougied on 11/12/2016.
 */
public class PixelInspector extends JPanel implements MouseListener {
    private PictureView picture_view;
    private JPanel pixelData;
    private Dimension prefferedSize;
    private JLabel xCoordinate;
    private JLabel yCoordinate;
    private JLabel redLabel;
    private JLabel greenLabel;
    private JLabel blueLabel;
    private JLabel brightnessLabel;

    public PixelInspector(Picture p) {
        if (p == null) {
            throw new IllegalArgumentException("Your Picture is empty!");
        }
        setLayout(new BorderLayout());
        pixelData = new JPanel(new GridLayout(6, 1));
        prefferedSize = new Dimension(128, 41);
        //Credits to Chris Burgess for Dimensional values

        picture_view = new PictureView(p.createObservable());
        picture_view.addMouseListener(this);
        add(picture_view, BorderLayout.CENTER);

        xCoordinate = new JLabel("X: " + 0);
        xCoordinate.setMinimumSize(prefferedSize);
        xCoordinate.setPreferredSize(prefferedSize);

        yCoordinate = new JLabel("Y: " + 0);
        yCoordinate.setMinimumSize(prefferedSize);
        yCoordinate.setPreferredSize(prefferedSize);

        redLabel = new JLabel("Red: " + roundOff(picture_view.getPicture().getPixel(0, 0).getRed()));
        redLabel.setMinimumSize(prefferedSize);
        redLabel.setPreferredSize(prefferedSize);

        greenLabel = new JLabel("Green: " + roundOff(picture_view.getPicture().getPixel(0, 0).getGreen()));
        greenLabel.setMinimumSize(prefferedSize);
        greenLabel.setPreferredSize(prefferedSize);

        blueLabel = new JLabel("Blue: " + roundOff(picture_view.getPicture().getPixel(0, 0).getBlue()));
        blueLabel.setMinimumSize(prefferedSize);
        blueLabel.setPreferredSize(prefferedSize);

        brightnessLabel = new JLabel("Brightness: " + roundOff(picture_view.getPicture().getPixel(0, 0).getIntensity()));
        brightnessLabel.setMinimumSize(prefferedSize);
        brightnessLabel.setPreferredSize(prefferedSize);

        pixelData.add(xCoordinate, 0);
        pixelData.add(yCoordinate, 1);
        pixelData.add(redLabel, 2);
        pixelData.add(greenLabel, 3);
        pixelData.add(blueLabel, 4);
        pixelData.add(brightnessLabel, 5);

        add(pixelData, BorderLayout.WEST);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        xCoordinate.setText("X: " + e.getX());
        yCoordinate.setText("Y: " + e.getY());
        redLabel.setText("Red: " + roundOff(picture_view.getPicture().getPixel(e.getX(), e.getY()).getRed()));
        greenLabel.setText("Green: " + roundOff(picture_view.getPicture().getPixel(e.getX(), e.getY()).getGreen()));
        blueLabel.setText("Blue: " + roundOff(picture_view.getPicture().getPixel(e.getX(), e.getY()).getBlue()));
        brightnessLabel.setText("Brightness: " + roundOff(picture_view.getPicture().getPixel(e.getX(), e.getY()).getIntensity()));
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /*
    Helper method to round off values
     */
    private double roundOff(double d) {
        return Math.round(d * 100.0) / 100.0;
    }
}
