import  java.awt.Color;
public class SeamCarver {
    
    private static final int BORDER_PIXEL_ENERGY = 195075;
    private Picture picture; 
    public double[][] energy;
    
    public SeamCarver(Picture picture) {
       
       this.picture = picture;
       
       energy = new double[width()][height()];
       
       calculateEnergies();
       
    }
    
    private int calcXGradient(int x, int y) {
        
        int r, g, b;
        
        Color leftPixel = picture.get(x-1, y);
        Color rightPixel = picture.get(x+1, y);
        
        r = Math.abs(leftPixel.getRed() - rightPixel.getRed());
        g = Math.abs(leftPixel.getGreen() - rightPixel.getGreen());
        b = Math.abs(leftPixel.getBlue() - rightPixel.getBlue());
        
        return (int)Math.pow((double)r, 2) + (int)Math.pow((double)g, 2) + (int)Math.pow((double)b, 2);
        
    }
    
    private int calcYGradient(int x, int y ) {
        int r, g, b;
        
        Color topPixel = picture.get(x, y-1);
        Color bottomPixel = picture.get(x, y+1);
        
        r = Math.abs(topPixel.getRed() - bottomPixel.getRed());
        g = Math.abs(topPixel.getGreen() - bottomPixel.getGreen());
        b = Math.abs(topPixel.getBlue() - bottomPixel.getBlue());
        
        return (int)Math.pow((double)r, 2) + (int)Math.pow((double)g, 2) + (int)Math.pow((double)b, 2);
    }
    
    private void calculateEnergies() {
        
        //make the default energy of border pixels
        for(int i = 0; i < height(); i++) {
            energy[0][i] = BORDER_PIXEL_ENERGY;
        }
        
        for(int i = 0; i < width(); i++) {
            energy[i][0] = BORDER_PIXEL_ENERGY;
        }  
        
        
        //calculate the energy of the rest of the pixels
        
        for(int x = 1; x < height() - 1; x++) {
            for(int y = 1; y < width() - 1; y++) {
                
                energy[x][y] = calcXGradient(x, y) + calcYGradient(x, y);
            }
        }
        
        
    }
   
    public Picture picture() {
       
       return picture();
      
    }
   
    public int width() {
       
       return picture.width();
    }     
   
    public int height() {
       
       return picture.height();
    }     
   
    public double energy(int x, int y) {
       return 0.0;
    }      
   
    public int[] findHorizontalSeam() {
       return null;
    }     
 
    public int[] findVerticalSeam() {
       
       return null;
    }       
   
    public void removeHorizontalSeam(int[] a) {
       
    }  
   
    public void removeVerticalSeam(int[] a)    {
       
    } 
    
    public static void main(String args[]) {
        
        Picture pict = new Picture("3x7.png");
        SeamCarver carver = new SeamCarver(pict);
        
                for(int x = 0; x < carver.height(); x++) {
                    for(int y = 0; y < carver.width(); y++) {
                
                    System.out.print(carver.energy[x][y] + " ");
                    }
                    
                    System.out.println();
                }
    }
}




