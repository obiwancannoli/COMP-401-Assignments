package a8;

public class GrayPixel implements Pixel {

	private double intensity;

	private static final char[] PIXEL_CHAR_MAP = {'#', 'M', 'X', 'D', '<', '>', 's', ':', '-', ' '};


	public GrayPixel(double intensity) {
		if (intensity < 0.0 || intensity > 1.0) {
			throw new IllegalArgumentException("Intensity of gray pixel is out of bounds.");
		}
		this.intensity = intensity;
	}

	@Override
	public double getRed() {
		return getIntensity();
	}

    @Override
    public void setRed(double red) {
        intensity = getIntensity();
    }

    @Override
	public double getBlue() {
		return getIntensity();
	}

    @Override
    public void setBlue(double blue) {
        intensity = getIntensity();
    }

    @Override
	public double getGreen() {
		return getIntensity();
	}

    @Override
    public void setGreen(double green) {
        intensity = getIntensity();
    }

    @Override
	public double getIntensity() {
		return intensity;
	}

	@Override
	public char getChar() {
		return PIXEL_CHAR_MAP[(int) (getIntensity()*10.0)];
	}

	@Override
	public Pixel blend(Pixel p, double weight) {
		if(p == null) {
			throw new RuntimeException("Can't compare to null");
		}
		if(weight < 0 || weight > 1) {
			throw new RuntimeException("Your weight value is out of bounds!");
		}
		weight = ((1-weight)*p.getIntensity()) + (weight*getIntensity());
		return new GrayPixel(weight);
	}

	@Override
	public Pixel lighten(double factor) {
		if(factor < 0 || factor > 1) {
			throw new RuntimeException("Your factor value is out of bounds!");
		}

		return blend(new GrayPixel(getIntensity()+(1-getIntensity())), (1-factor));
	}

	@Override
	public Pixel darken(double factor) {
		if(factor < 0 || factor > 1) {
			throw new RuntimeException("Your factor value is out of bounds!");
		}
		Pixel darker = new GrayPixel(getIntensity()*0);

		return blend(darker, (1-factor));
	}

	@Override
	public boolean equals(Pixel p) {
		if(p == null) {
			throw new RuntimeException("You can't compare to null");
		}
		double max = 0;
		if(getIntensity() > p.getIntensity()) {
			max = getIntensity();
		} else if (p.getIntensity() > getIntensity()) {
			max = p.getIntensity();
		}
		boolean red = false;
		boolean green = false;
		boolean blue = false;
		if (Math.abs(getRed()-p.getRed()) < .1*max) { red = true; }
		if (Math.abs(getGreen()-p.getGreen()) < .1*max) { green = true; }
		if (Math.abs(getBlue()-p.getBlue()) < .1*max) { blue = true; }
		return red && green && blue;
		//Credits to Chris Burghess
	}
	}

