
import java.awt.Color;
import java.util.ArrayList;

public class SeamCarver {

    private static final int BORDER_PIXEL_ENERGY = 195075;
    private Picture picture;
    public double[][] energy;

    public SeamCarver(Picture picture) {

        this.picture = picture;

        energy = new double[width()][height()];

        //calculateEnergies();

    }

    private double calcXGradient(int x, int y) {

        double r, g, b;

        Color leftPixel = picture.get(x - 1, y);
        Color rightPixel = picture.get(x + 1, y);

        r = Math.abs(leftPixel.getRed() - rightPixel.getRed());
        g = Math.abs(leftPixel.getGreen() - rightPixel.getGreen());
        b = Math.abs(leftPixel.getBlue() - rightPixel.getBlue());

        return Math.pow(r, 2) + Math.pow(g, 2) + Math.pow(b, 2);

    }

    private double calcYGradient(int x, int y) {
        double r, g, b;

        Color topPixel = picture.get(x, y - 1);
        Color bottomPixel = picture.get(x, y + 1);

        r = Math.abs(topPixel.getRed() - bottomPixel.getRed());
        g = Math.abs(topPixel.getGreen() - bottomPixel.getGreen());
        b = Math.abs(topPixel.getBlue() - bottomPixel.getBlue());

        return Math.pow(r, 2) + Math.pow(g, 2) + Math.pow(b, 2);
    }

    private void calculateEnergies() {

        //make the default energy of border pixels
        for (int i = 0; i < height(); i++) {
            energy[0][i] = BORDER_PIXEL_ENERGY;
            energy[width() - 1][i] = BORDER_PIXEL_ENERGY;
        }

        for (int i = 0; i < width(); i++) {
            energy[i][0] = BORDER_PIXEL_ENERGY;
            energy[i][height() - 1] = BORDER_PIXEL_ENERGY;
        }


        //calculate the energy of the rest of the pixels

        for (int y = 1; y < height() - 1; y++) {
            for (int x = 1; x < width() - 1; x++) {

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

        return calcXGradient(x, y) + calcYGradient(x, y);
    }

    public int[] findHorizontalSeam() {
        return null;
    }

    private int coordsToIndex(int x, int y) {

        return (y * width()) + x;
    }

    public Object[] findVerticalSeam() {

        EdgeWeightedDigraph ewd = new EdgeWeightedDigraph(height());

        for (int y = 0; y <height (); y++) {
            for (int x = 0; x < width(); x++) {
                //top-left
                if (x == 0 && y == 0) {
                    DirectedEdge edge1 = new DirectedEdge(coordsToIndex(x, y), coordsToIndex(x, y + 1), energy(x, y + 1));
                    DirectedEdge edge2 = new DirectedEdge(coordsToIndex(x, y), coordsToIndex(x + 1, y + 1), energy(x + 1, y + 1));

                    ewd.addEdge(edge1);
                    ewd.addEdge(edge2);

                    continue;
                }

                // top-right
                if (x == width() && y == 0) {
                    DirectedEdge edge1 = new DirectedEdge(coordsToIndex(x, y), coordsToIndex(x - 1, y + 1), energy(x - 1, y + 1));
                    DirectedEdge edge2 = new DirectedEdge(coordsToIndex(x, y), coordsToIndex(x, y + 1), energy(x, y + 1));

                    ewd.addEdge(edge1);
                    ewd.addEdge(edge2);

                    continue;
                }

                //bottom-left 
                if (y == height()) {
                    continue;
                }

                // normal case
                DirectedEdge edge1 = new DirectedEdge(coordsToIndex(x, y), coordsToIndex(x - 1, y + 1), energy(x - 1, y + 1));
                DirectedEdge edge2 = new DirectedEdge(coordsToIndex(x, y), coordsToIndex(x, y + 1), energy(x, y + 1));
                DirectedEdge edge3 = new DirectedEdge(coordsToIndex(x, y), coordsToIndex(x + 1, y + 1), energy(x + 1, y + 1));

                ewd.addEdge(edge1);
                ewd.addEdge(edge2);
                ewd.addEdge(edge3);
            }
        }

        double minWeight = Double.POSITIVE_INFINITY;
        ArrayList<Integer> path = new ArrayList<Integer>(height());
        Iterable<DirectedEdge> vSeam = null;

        //find the the REALLY SHORTEST PATH FROM ALL DIJKSTRA instances
        for (int x = 0; x < width(); x++) {
            DijkstraSP dsp = new DijkstraSP(ewd, coordsToIndex(x, 0));

            //find the shortest path from source to the bottom
            double dspMinWeight = Double.POSITIVE_INFINITY;
            int shortestIndex = -1;
            for (int x2 = 0; x2 < width(); x2++) {

                if (dsp.distTo(coordsToIndex(x, height())) < dspMinWeight) {
                    dspMinWeight = dsp.distTo(coordsToIndex(x, height()));
                    shortestIndex = x2;
                }
            }

            if (dspMinWeight < minWeight) {
                minWeight = dspMinWeight;
                vSeam = dsp.pathTo(shortestIndex);
            }


        }

        boolean first = true;
        for (DirectedEdge edge : vSeam) {
            if (first) {
                path.add(edge.from());
                first = false;
            } else {
                path.add(edge.to());
            }

        }

        return path.toArray();
    }

    public void removeHorizontalSeam(int[] a) {
    }

    public void removeVerticalSeam(int[] a) {
    }

    public static void main(String args[]) {

        Picture pict = new Picture("3x7.png");
        SeamCarver carver = new SeamCarver(pict);

        for (int x = 0; x < carver.height(); x++) {
            for (int y = 0; y < carver.width(); y++) {

                System.out.print(carver.energy[y][x] + " ");
            }

            System.out.println();
        }
    }
}
