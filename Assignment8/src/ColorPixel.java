package a8;


public class ColorPixel implements Pixel {



    private double red;
    private double green;
    private double blue;

    private static final double RED_INTENSITY_FACTOR = 0.299;
    private static final double GREEN_INTENSITY_FACTOR = 0.587;
    private static final double BLUE_INTENSITY_FACTOR = 0.114;

    private static final char[] PIXEL_CHAR_MAP = {'#', 'M', 'X', 'D', '<', '>', 's', ':', '-', ' ', ' '};

    public ColorPixel(double r, double g, double b) {
        if (r > 1.0 || r < 0.0) {
            throw new IllegalArgumentException("Red out of bounds");
        }
        if (g > 1.0 || g < 0.0) {
            throw new IllegalArgumentException("Green out of bounds");
        }
        if (b > 1.0 || b < 0.0) {
            throw new IllegalArgumentException("Blue out of bounds");
        }
        red = r;
        green = g;
        blue = b;
    }

    @Override
    public double getRed() {
        return red;
    }

    public void setRed(double red) {
        this.red = red;
    }



    @Override
    public double getBlue() {
        return blue;
    }

    public void setBlue(double blue) {
        this.blue = blue;
    }

    @Override
    public double getGreen() {
        return green;
    }

    public void setGreen(double green) {
        this.green = green;
    }

    @Override
    public double getIntensity() {
        return RED_INTENSITY_FACTOR * getRed() +
                GREEN_INTENSITY_FACTOR * getGreen() +
                BLUE_INTENSITY_FACTOR * getBlue();
    }

    @Override
    public char getChar() {
        int char_idx = (int) (getIntensity() * 10.0);
        return PIXEL_CHAR_MAP[char_idx];
    }

    @Override
    public Pixel blend(Pixel p, double weight) {
        if (p == null) {
            throw new RuntimeException("You can't compare to null");
        }
        if (weight < 0 || weight > 1) {
            throw new RuntimeException("Your weight value is out of bounds!");
        }
        return new ColorPixel((weight * getRed()) + ((1 - weight) * p.getRed()), (weight * getGreen()) + ((1 - weight) * p.getGreen()), ((weight * getBlue()) + ((1 - weight) * p.getBlue())));

    }

    @Override
    public Pixel lighten(double factor) {
        if (factor < 0 || factor > 1) {
            throw new RuntimeException("Your factor value is out of bounds!");
        }

        return blend(new ColorPixel(getRed() / getRed(), getGreen() / getGreen(), getBlue() / getBlue()), (1 - factor));
    }

    @Override
    public Pixel darken(double factor) {
        if (factor < 0 || factor > 1) {
            throw new RuntimeException("Your factor value is out of bounds!");
        }

        return blend(new ColorPixel(0 * getRed(), 0 * getGreen(), 0 * getBlue()), (1 - factor));
    }

    @Override
    public boolean equals(Pixel p) {
        if (p == null) {
            throw new RuntimeException("You can't compare to a null value");
        }
        double max = 0;
        if (getIntensity() > p.getIntensity()) {
            max = getIntensity();
        } else if (p.getIntensity() > getIntensity()) {
            max = p.getIntensity();
        }
        boolean red = false;
        boolean green = false;
        boolean blue = false;
        if (Math.abs(getRed() - p.getRed()) < .1 * max) {
            red = true;
        }
        if (Math.abs(getGreen() - p.getGreen()) < .1 * max) {
            green = true;
        }
        if (Math.abs(getBlue() - p.getBlue()) < .1 * max) {
            blue = true;
        }
        return red && green && blue;
        //Credits to Chris Burgess
    }
}

