package a8;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * Created by dougied on 11/14/2016.
 */
public class ImageAdjuster extends JPanel implements ChangeListener {
    private PictureView pv;
    private Picture source;
    private JPanel imageSliders;
    private Dimension prefSize;
    private JLabel blurLabel;
    private JLabel satLabel;
    private JLabel brightLabel;
    private JSlider blur;
    private JSlider saturation;
    private JSlider brightness;
    private int blurValue;
    private double satValue;
    private double brightValue;

    public ImageAdjuster(Picture p) {
        if (p == null) {
            throw new IllegalArgumentException("Your picture is empty!");
        }
        source = p;
        setLayout(new BorderLayout());

        imageSliders = new JPanel(new GridLayout(3, 2));
        prefSize = new Dimension(128, 41);

        pv = new PictureView(source.createObservable());

        add(pv, BorderLayout.CENTER);

        blurLabel = new JLabel("Blur: ");
        blurLabel.setMinimumSize(prefSize);
        blurLabel.setPreferredSize(prefSize);
        satLabel = new JLabel("Saturation: ");
        satLabel.setMinimumSize(prefSize);
        satLabel.setPreferredSize(prefSize);
        brightLabel = new JLabel("Brightness: ");
        brightLabel.setMinimumSize(prefSize);
        brightLabel.setPreferredSize(prefSize);


        blur = new JSlider(0, 5, 0);
        blur.setPaintTicks(true);
        blur.setPaintLabels(true);
        blur.setSnapToTicks(true);
        blur.setMajorTickSpacing(1);
        blur.addChangeListener(this);

        saturation = new JSlider(-100, 100, 0);
        saturation.setPaintTicks(true);
        saturation.setPaintLabels(true);
        saturation.setMajorTickSpacing(25);
        saturation.addChangeListener(this);

        brightness = new JSlider(-100, 100, 0);
        brightness.setPaintTicks(true);
        brightness.setPaintLabels(true);
        brightness.setMajorTickSpacing(25);
        brightness.addChangeListener(this);

        imageSliders.add(blurLabel, 0);
        imageSliders.add(blur, 1);
        imageSliders.add(satLabel, 2);
        imageSliders.add(saturation, 3);
        imageSliders.add(brightLabel, 4);
        imageSliders.add(brightness, 5);


        add(imageSliders, BorderLayout.SOUTH);

    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();
        ObservablePicture newPic = new PictureImpl(source.getWidth(), source.getHeight()).createObservable();
        Pixel blurPixel;
        if (!slider.getValueIsAdjusting()) {//waits until user stops moving the slider to make adjustments to the picture
            if (blur.getValue() == 0 || saturation.getValue() == 0 || brightness.getValue() == 0) { //sets picture to default if one of the sliders is on 0
                for (int y = 0; y < source.getHeight(); y++) {
                    for (int x = 0; x < source.getWidth(); x++) {
                        newPic.setPixel(x, y, source.getPixel(x, y));
                    }
                }
            }
            //Blur method
            blurValue = blur.getValue();
            for (int y = 0; y < source.getHeight(); y++) {
                for (int x = 0; x < source.getWidth(); x++) {
                    double red = 0.0;
                    double green = 0.0;
                    double blue = 0.0;
                    int count = 0;
                    for (int y2 = y - blurValue; y2 <= y + blurValue; y2++) {
                        for (int x2 = x - blurValue; x2 <= x + blurValue; x2++) {
                            try {
                                red += source.getPixel(x2, y2).getRed();
                                green += source.getPixel(x2, y2).getGreen();
                                blue += source.getPixel(x2, y2).getBlue();

                                count++;
                            } catch (RuntimeException r) {

                            }
                        }
                    }
                    blurPixel = new ColorPixel(red / count, green / count, blue / count);

                    newPic.setPixel(x, y, blurPixel);
                }
            }
            //Saturation method
            satValue = saturation.getValue();
            if (satValue < 0) {
                for (int y = 0; y < source.getHeight(); y++) {
                    for (int x = 0; x < source.getWidth(); x++) {
                        if (newPic.getPixel(x, y).getIntensity() == 0) { //checks for black pixels and returns them as all black pixels
                            newPic.setPixel(x, y, new GrayPixel(0));
                            continue;
                        }
                        double red = negSaturation(newPic.getPixel(x, y).getRed(), satValue, newPic.getPixel(x, y).getIntensity());
                        double green = negSaturation(newPic.getPixel(x, y).getGreen(), satValue, newPic.getPixel(x, y).getIntensity());
                        double blue = negSaturation(newPic.getPixel(x, y).getBlue(), satValue, newPic.getPixel(x, y).getIntensity());
                        newPic.setPixel(x, y, new ColorPixel(red, green, blue));
                    }
                }
            } else if (satValue > 0) {
                for (int y = 0; y < source.getHeight(); y++) {
                    for (int x = 0; x < source.getWidth(); x++) {
                        if (source.getPixel(x, y).getIntensity() == 0) { //checks for black pixels and returns them as all black pixels
                            newPic.setPixel(x, y, new GrayPixel(0));
                            continue;
                        }

                        double a = largestColorValue(newPic.getPixel(x, y).getRed(), newPic.getPixel(x, y).getGreen(), newPic.getPixel(x, y).getBlue());
                        double red = posSaturation(newPic.getPixel(x, y).getRed(), satValue, a);
                        double green = posSaturation(newPic.getPixel(x, y).getGreen(), satValue, a);
                        double blue = posSaturation(newPic.getPixel(x, y).getBlue(), satValue, a);
                        newPic.setPixel(x, y, new ColorPixel(red, green, blue));
                    }
                }
            }
            //brightness method
            brightValue = brightness.getValue();
            if (brightValue < 0) {
                for (int y = 0; y < source.getHeight(); y++) {
                    for (int x = 0; x < source.getWidth(); x++) {
                        newPic.setPixel(x, y, newPic.getPixel(x, y).darken(Math.abs(brightValue / 100)));
                    }
                }

            } else if (brightValue > 0) {
                for (int y = 0; y < source.getHeight(); y++) {
                    for (int x = 0; x < source.getWidth(); x++) {
                        newPic.setPixel(x, y, newPic.getPixel(x, y).lighten(Math.abs(brightValue / 100)));
                    }
                }

            }
            pv.setPicture(newPic);
        }

    }

    //Helper method for negative saturation
    public double negSaturation(double num, double f, double brightness) {
        return num * (1.0 + (f / 100.0)) - (brightness * f / 100.0);
    }

    //Helper method to find largest color value amongst the 3 colors
    public double largestColorValue(double r, double g, double b) {
        return Math.max(b, Math.max(r, g));

    }

    //Helper method for positive saturation
    public double posSaturation(double num, double f, double a) {
        return num * ((a + ((1.0 - a) * (f / 100.0))) / a);
    }
}

