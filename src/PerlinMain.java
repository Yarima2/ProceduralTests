import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class PerlinMain {

	static final double res = 1000;
	static final double size = 4;
	
	
	
	public static void main(String[] args) throws IOException {
		
		BufferedImage b = new BufferedImage((int)(res*size), (int)(res*size), BufferedImage.TYPE_INT_RGB);
		Perlin p = new Perlin(1);
		
		for(double y = 0; y<size; y+=(1/res)){
			for(double x = 0; x<size; x+=(1/res)){
				double v = p.OctavePerlin(x, y, 3, 0.5f, 2);

				if(v>0.75) {
					b.setRGB((int)(x*res), (int)(y*res), Color.WHITE.getRGB());
				}
				else if(v>0.7) {
					b.setRGB((int)(x*res), (int)(y*res), Color.GRAY.getRGB());
				}
				else if(v>0.6) {
					b.setRGB((int)(x*res), (int)(y*res), new Color(0x1fc121, false).getRGB());
				}
				else if(v>0.55) {
					b.setRGB((int)(x*res), (int)(y*res), Color.GREEN.getRGB());
				}
				else if(v>0.5) {
					b.setRGB((int)(x*res), (int)(y*res), Color.YELLOW.getRGB());
				}
				else {
					b.setRGB((int)(x*res), (int)(y*res), Color.BLUE.getRGB());
				}
				
			}
		}

		ImageIO.write(b, "jpg", new File("map.jpg"));
		
	
		
	
	}

}
