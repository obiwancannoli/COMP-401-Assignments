package a8;

public interface Pixel {

	public double getRed();
    public void setRed(double red);
	public double getBlue();
    public void setBlue(double blue);
	public double getGreen();
    public void setGreen(double green);
	public double getIntensity();
	public char getChar();
    public Pixel blend(Pixel p, double weight);
    public Pixel lighten(double factor);
    public Pixel darken(double factor);
    public boolean equals(Pixel p);
}
