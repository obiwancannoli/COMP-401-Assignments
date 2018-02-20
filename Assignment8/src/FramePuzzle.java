package a8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;


/**
 * Created by dougied on 11/19/2016.
 */
public class FramePuzzle extends JPanel implements KeyListener, MouseListener {
    private Picture source;
    private JPanel picturePanel;
    private Picture green_tile;
    private IndexPictureView observableGreen_tile;
    private IndexPictureView[][] pictureViews;
    private Iterator<SubPicture> tileIterator;


    public FramePuzzle(Picture p) {
        if (p == null) {
            throw new IllegalArgumentException("Your picture object is empty!");
        }
        setLayout(new BorderLayout());


        picturePanel = new JPanel(new GridLayout(5, 5, 1, 1));
        picturePanel.setFocusable(false);
        setFocusable(true);
        requestFocus();
        addKeyListener(this);

        add(picturePanel, BorderLayout.CENTER);


        source = p;
        tileIterator = source.tile((source.getWidth() / 5), (source.getHeight() / 5));
        pictureViews = new IndexPictureView[5][5];

        /*
        Creates a green tile for the bottom right corner and then adds it to that corner
         */
        green_tile = new PictureImpl(source.getWidth() / 5, source.getHeight() / 5);
        for (int y = 0; y < green_tile.getHeight(); y++) {
            for (int x = 0; x < green_tile.getWidth(); x++) {
                green_tile.setPixel(x, y, new ColorPixel(0, 1, 0));
            }
        }

        observableGreen_tile = new IndexPictureView(green_tile.createObservable(), new Coordinate(4, 4));
        observableGreen_tile.addMouseListener(this);

        /*
        * Taking the tiles from the iterator and adding them to a PictureView Array list
        * Adding listeners to each picture view object
        * Then adding picture view objects to panel
        */
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                if (y == 4 && x == 4) {
                    pictureViews[4][4] = observableGreen_tile;
                    break;
                }
                pictureViews[y][x] = new IndexPictureView(tileIterator.next().createObservable(), new Coordinate(x, y));
                pictureViews[y][x].addMouseListener(this);
                picturePanel.add(pictureViews[y][x]);

                //pictureViews[y][x].setFocusable(true);
            }
        }

//        pictureViews[4][4] = observableGreen_tile;
        //observableGreen_tile.addMouseListener(this);
        picturePanel.add(observableGreen_tile, 24);


    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
        IndexPictureView temp;
        IndexPictureView temp2;
        switch (e.getKeyCode()) {

            case KeyEvent.VK_LEFT:
                if (observableGreen_tile.getCoordinate().getX() == 0) {

                } else {
                    //uses the coordinates of the green tile as a reference point to switch the green tile with the one to the left of it
                    temp = pictureViews[observableGreen_tile.getCoordinate().getY()][observableGreen_tile.getCoordinate().getX() - 1];
                    pictureViews[observableGreen_tile.getCoordinate().getY()][observableGreen_tile.getCoordinate().getX() - 1] = observableGreen_tile;
                    pictureViews[observableGreen_tile.getCoordinate().getY()][observableGreen_tile.getCoordinate().getX()] = temp;
                    pictureViews[observableGreen_tile.getCoordinate().getY()][observableGreen_tile.getCoordinate().getX()].setCoordinate(new Coordinate(observableGreen_tile.getCoordinate().getX(), observableGreen_tile.getCoordinate().getY()));
                    observableGreen_tile.setCoordinate(new Coordinate(observableGreen_tile.getCoordinate().getX() - 1, observableGreen_tile.getCoordinate().getY()));
                    pic_repaint();
                }
                break;


            case KeyEvent.VK_RIGHT:
                if (observableGreen_tile.getCoordinate().getX() == 4) {

                } else {
                    //uses the coordinates of the green tile as a reference point to switch the green tile with the tile to the right of it
                    temp = pictureViews[observableGreen_tile.getCoordinate().getY()][observableGreen_tile.getCoordinate().getX() + 1];
                    pictureViews[observableGreen_tile.getCoordinate().getY()][observableGreen_tile.getCoordinate().getX() + 1] = observableGreen_tile;
                    pictureViews[observableGreen_tile.getCoordinate().getY()][observableGreen_tile.getCoordinate().getX()] = temp;
                    pictureViews[observableGreen_tile.getCoordinate().getY()][observableGreen_tile.getCoordinate().getX()].setCoordinate(new Coordinate(observableGreen_tile.getCoordinate().getX(), observableGreen_tile.getCoordinate().getY()));
                    observableGreen_tile.setCoordinate(new Coordinate(observableGreen_tile.getCoordinate().getX() + 1, observableGreen_tile.getCoordinate().getY()));
                    pic_repaint();
                }
                break;

            case KeyEvent.VK_DOWN:
                if (observableGreen_tile.getCoordinate().getY() == 4) {
                    break;
                } else {
                    //uses the coordinates in the green tile to as a reference point to switch the green tile with the tile below it
                    temp2 = pictureViews[observableGreen_tile.getCoordinate().getY() + 1][observableGreen_tile.getCoordinate().getX()];
                    pictureViews[observableGreen_tile.getCoordinate().getY() + 1][observableGreen_tile.getCoordinate().getX()] = observableGreen_tile;
                    pictureViews[observableGreen_tile.getCoordinate().getY()][observableGreen_tile.getCoordinate().getX()] = temp2;
                    pictureViews[observableGreen_tile.getCoordinate().getY()][observableGreen_tile.getCoordinate().getX()].setCoordinate(new Coordinate(observableGreen_tile.getCoordinate().getX(), observableGreen_tile.getCoordinate().getY()));
                    observableGreen_tile.setCoordinate(new Coordinate(observableGreen_tile.getCoordinate().getX(), observableGreen_tile.getCoordinate().getY() + 1));
                    pic_repaint();
                }
                break;

            case KeyEvent.VK_UP:
                if (observableGreen_tile.getCoordinate().getY() == 0) {

                } else {
                    //uses the coordinates of the green tile as a reference point to switch the green tile with the tile above it
                    temp2 = pictureViews[observableGreen_tile.getCoordinate().getY() - 1][observableGreen_tile.getCoordinate().getX()];
                    pictureViews[observableGreen_tile.getCoordinate().getY() - 1][observableGreen_tile.getCoordinate().getX()] = observableGreen_tile;
                    pictureViews[observableGreen_tile.getCoordinate().getY()][observableGreen_tile.getCoordinate().getX()] = temp2;
                    pictureViews[observableGreen_tile.getCoordinate().getY()][observableGreen_tile.getCoordinate().getX()].setCoordinate(new Coordinate(observableGreen_tile.getCoordinate().getX(), observableGreen_tile.getCoordinate().getY()));
                    observableGreen_tile.setCoordinate(new Coordinate(observableGreen_tile.getCoordinate().getX(), observableGreen_tile.getCoordinate().getY() - 1));
                    pic_repaint();
                }
                break;
        }


    }


    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = ((IndexPictureView) e.getSource()).getCoordinate().getX();
        int mouseY = ((IndexPictureView) e.getSource()).getCoordinate().getY();
        IndexPictureView tile = (IndexPictureView) e.getSource();
        //System.out.println("Coordinates are: " + mouseX + ", " + mouseY);
        requestFocus();
        IndexPictureView temp;
        IndexPictureView temp2;
        IndexPictureView temp3;
        IndexPictureView temp4;

        //When you click to the left of the green block
        //For loop switches the green block with the tile to it's left until it reaches the tile that was clicked
        if (mouseX < observableGreen_tile.getCoordinate().getX() && mouseY == observableGreen_tile.getCoordinate().getY()) {
            for (int i = 0; i <= observableGreen_tile.getCoordinate().getX() - mouseX; i++) {
                temp = pictureViews[observableGreen_tile.getCoordinate().getY()][observableGreen_tile.getCoordinate().getX() - 1];
                pictureViews[observableGreen_tile.getCoordinate().getY()][observableGreen_tile.getCoordinate().getX() - 1] = observableGreen_tile;
                pictureViews[observableGreen_tile.getCoordinate().getY()][observableGreen_tile.getCoordinate().getX()] = temp;
                pictureViews[observableGreen_tile.getCoordinate().getY()][observableGreen_tile.getCoordinate().getX()].setCoordinate(new Coordinate(observableGreen_tile.getCoordinate().getX(), observableGreen_tile.getCoordinate().getY()));
                observableGreen_tile.setCoordinate(new Coordinate(observableGreen_tile.getCoordinate().getX() - 1, observableGreen_tile.getCoordinate().getY()));
            }
            pic_repaint();
        }


        //When you click to the right of the green block
        //For loop switches the green block with the tile to it's right until it reaches the tile that was clicked
        if (mouseX > observableGreen_tile.getCoordinate().getX() && mouseY == observableGreen_tile.getCoordinate().getY()) {
            for (int i = 0; i <= mouseX - observableGreen_tile.getCoordinate().getX(); i++) {
                temp2 = pictureViews[observableGreen_tile.getCoordinate().getY()][observableGreen_tile.getCoordinate().getX() + 1];
                pictureViews[observableGreen_tile.getCoordinate().getY()][observableGreen_tile.getCoordinate().getX() + 1] = observableGreen_tile;
                pictureViews[observableGreen_tile.getCoordinate().getY()][observableGreen_tile.getCoordinate().getX()] = temp2;
                pictureViews[observableGreen_tile.getCoordinate().getY()][observableGreen_tile.getCoordinate().getX()].setCoordinate(new Coordinate(observableGreen_tile.getCoordinate().getX(), observableGreen_tile.getCoordinate().getY()));
                observableGreen_tile.setCoordinate(new Coordinate(observableGreen_tile.getCoordinate().getX() + 1, observableGreen_tile.getCoordinate().getY()));
            }
            pic_repaint();
        }

        //When you click below the green block
        //For loop switches the green block with the tile below it until it reaches the tile that was clicked
        if (mouseY > observableGreen_tile.getCoordinate().getY() && mouseX == observableGreen_tile.getCoordinate().getX()) {

            for (int i = 0; i <= mouseY - observableGreen_tile.getCoordinate().getY(); i++) {
                temp3 = pictureViews[observableGreen_tile.getCoordinate().getY() + 1][observableGreen_tile.getCoordinate().getX()];
                pictureViews[observableGreen_tile.getCoordinate().getY() + 1][observableGreen_tile.getCoordinate().getX()] = observableGreen_tile;
                pictureViews[observableGreen_tile.getCoordinate().getY()][observableGreen_tile.getCoordinate().getX()] = temp3;
                pictureViews[observableGreen_tile.getCoordinate().getY()][observableGreen_tile.getCoordinate().getX()].setCoordinate(new Coordinate(observableGreen_tile.getCoordinate().getX(), observableGreen_tile.getCoordinate().getY()));
                observableGreen_tile.setCoordinate(new Coordinate(observableGreen_tile.getCoordinate().getX(), observableGreen_tile.getCoordinate().getY() + 1));
            }
            pic_repaint();
        }

        //When you click above the green block
        //For loop switches the green block with the tile above it until it reaches the tile that was clicked
        if (mouseY < observableGreen_tile.getCoordinate().getY() && mouseX == observableGreen_tile.getCoordinate().getX()) {

            for (int i = 0; i <= observableGreen_tile.getCoordinate().getY() - mouseY; i++) {
                temp4 = pictureViews[observableGreen_tile.getCoordinate().getY() - 1][observableGreen_tile.getCoordinate().getX()];
                pictureViews[observableGreen_tile.getCoordinate().getY() - 1][observableGreen_tile.getCoordinate().getX()] = observableGreen_tile;
                pictureViews[observableGreen_tile.getCoordinate().getY()][observableGreen_tile.getCoordinate().getX()] = temp4;
                pictureViews[observableGreen_tile.getCoordinate().getY()][observableGreen_tile.getCoordinate().getX()].setCoordinate(new Coordinate(observableGreen_tile.getCoordinate().getX(), observableGreen_tile.getCoordinate().getY()));
                observableGreen_tile.setCoordinate(new Coordinate(observableGreen_tile.getCoordinate().getX(), observableGreen_tile.getCoordinate().getY() - 1));
            }
            pic_repaint();
        }
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

    //method for repainting the panel and applying the tiles with their new coordinates
    private void pic_repaint() {
        picturePanel.removeAll();
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                picturePanel.add(pictureViews[y][x]);
            }
        }
        revalidate();
        repaint();
    }


}
