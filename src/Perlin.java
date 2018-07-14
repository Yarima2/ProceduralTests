import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;



public class Perlin {

	//set to 0 to get infinite perlin noise
	private int repeat = 0;
	
	
	private Integer[] p = { 151,160,137,91,90,15,                 // Hash lookup table as defined by Ken Perlin.  This is a randomly
	    131,13,201,95,96,53,194,233,7,225,140,36,103,30,69,142,8,99,37,240,21,10,23,    // arranged array of all numbers from 0-255 inclusive.
	    190, 6,148,247,120,234,75,0,26,197,62,94,252,219,203,117,35,11,32,57,177,33,
	    88,237,149,56,87,174,20,125,136,171,168, 68,175,74,165,71,134,139,48,27,166,
	    77,146,158,231,83,111,229,122,60,211,133,230,220,105,92,41,55,46,245,40,244,
	    102,143,54, 65,25,63,161, 1,216,80,73,209,76,132,187,208, 89,18,169,200,196,
	    135,130,116,188,159,86,164,100,109,198,173,186, 3,64,52,217,226,250,124,123,
	    5,202,38,147,118,126,255,82,85,212,207,206,59,227,47,16,58,17,182,189,28,42,
	    223,183,170,213,119,248,152, 2,44,154,163, 70,221,153,101,155,167, 43,172,9,
	    129,22,39,253, 19,98,108,110,79,113,224,232,178,185, 112,104,218,246,97,228,
	    251,34,242,193,238,210,144,12,191,179,162,241, 81,51,145,235,249,14,239,107,
	    49,192,214, 31,181,199,106,157,184, 84,204,176,115,121,50,45,127, 4,150,254,
	    138,236,205,93,222,114,67,29,24,72,243,141,128,195,78,66,215,61,156,180,
	    151,160,137,91,90,15};
	
	
	
	
	public Perlin(long seed){ 
		List<Integer> l = Arrays.asList(p);
		Collections.shuffle(l, new Random(seed));
		p = (Integer[]) l.toArray();
	}
	
	public Perlin(){
		List<Integer> l = Arrays.asList(p);
		Collections.shuffle(l, new Random((long)(Math.random()*9223372036854775807L*2-9223372036854775807L)));
		p = (Integer[]) l.toArray();
	}
	
	
	
	public double OctavePerlin(double x, double y, int octaves, float persistence, float lacunarity) {
	    double total = 0;
	    double frequency = 1;
	    double amplitude = 1;
	    double maxValue = 0;  // Used for normalizing result to 0.0 - 1.0
	    for(int i=0;i<octaves;i++) {
	        total += perlin(x * frequency, y * frequency) * amplitude;
	        
	        maxValue += amplitude;
	        
	        amplitude *= persistence;
	        frequency *= lacunarity;
	    }
	    return total/maxValue;
	}
	
	
	
	
	
	public double perlin(double x, double y){
		if(repeat > 0) {                                    
	        x = x%repeat;
	        y = y%repeat;
	    }
		
		
		double relX = x-(int)x;
		double relY = y-(int)y;
		int rastX = (int)x%255;
		int rastY = (int)y%255;
		
		
		double relXFaded = fade(relX);
		double relYFaded = fade(relY);
		
		
		int aa = p[(p[	 rastX  ]+	  rastY )%255];
		int ab = p[(p[	 rastX  ]+inc(rastY))%255];
		int ba = p[(p[inc(rastX)]+	  rastY )%255];
		int bb = p[(p[inc(rastX)]+inc(rastY))%255];
		
		
		
		double x1, x2, y1;
		x1 = interpolate(grad(aa, relX, relY  ), grad(ba, relX-1, relY  ), relXFaded);
		x2 = interpolate(grad(ab, relX, relY-1), grad(bb, relX-1, relY-1), relXFaded);
		y1 = interpolate(x1, x2, relYFaded);
		
		return (y1+1)/2;
	}
	
	
	
	
	
	private double fade(double t){
		return t * t * t * (t * (t * 6 - 15) + 10);
	}
	
	
	
	
	public int inc(int num) {
	    num++;
	    if (repeat > 0) num %= repeat;
	    
	    return num;
	}
	
	
	public double grad(int hash, double relX, double relY)
	{
	    switch(hash%3)	//changed - real perlin algorithm:  switch(hash & 0xF)
	    {
	        case 0: return  relX + relY;
	        case 1: return -relX + relY;
	        case 2: return  relX - relY;
	        case 3: return -relX - relY;

	        default: return 0; // never happens
	    }
	}
	
	
	
	public double interpolate(double a, double b, double x) {	//also called lerp
	    return a + x * (b - a);
	}
	
}
