package a8;

import java.util.Iterator;

/**
 * Created by dougied on 10/28/2016.
 */
public class TileSubPicIterator implements Iterator {
    private Picture source;
    private int t_width;
    private int t_height;
    private int x = 0;
    private int y = 0;

    public TileSubPicIterator(Picture p, int tile_width, int tile_height) {
        if(p == null ) {
            throw new IllegalArgumentException("Your picture is empty!");
        }
        source = p;
        t_width = tile_width;
        t_height = tile_height;
    }

    @Override
    public boolean hasNext() {
        return y <= source.getHeight() - t_height;
    }

    @Override
    public SubPicture next() {
        if(!hasNext()) {
            throw new UnsupportedOperationException();
        }
        SubPicture currentSubPic = source.extract(x, y, t_width, t_height);
        x+= t_width;
        if(x + t_width > source.getWidth()) {
            x =0;
            y+= t_height;
        }
        return currentSubPic;
    }
}
