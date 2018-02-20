package a8;

/**
 * Created by dougied on 11/20/2016.
 */
public class IndexPictureView extends PictureView {


    private Coordinate coordinate;


    public IndexPictureView(ObservablePicture p, Coordinate c) {
        super(p);
        if (c == null) {
            throw new IllegalArgumentException("Your coordinate object is empty");
        }
        coordinate = c;

    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate c) {
        coordinate = c;
    }
}
