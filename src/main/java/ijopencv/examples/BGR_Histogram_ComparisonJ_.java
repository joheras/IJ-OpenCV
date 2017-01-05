package ijopencv.examples;


import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;
import ij.text.TextWindow;
import ijopencv.ImageStackConverter;
import java.util.ArrayList;
import org.bytedeco.javacpp.FloatPointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgproc;
import static org.bytedeco.javacpp.opencv_imgproc.calcHist;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author jonathan
 */
public class BGR_Histogram_ComparisonJ_ implements PlugIn {

    int blueBins = 8;
    int greenBins = 8;
    int redBins = 8;

    @Override
    public void run(String arg) {

        ImagePlus imp = IJ.getImage();
        int stacksize = imp.getStack().getSize();

        if (imp.getStack().getSize() == 1) {
            IJ.error("You need a stack of images");
            return;
        }

        // Converter
        ImageStackConverter isc = new ImageStackConverter();
        opencv_core.MatVector mvec = isc.convertTo(imp);

        if (!showDialog()) {
            return;
        }

        double[][] comparison = new double[4][stacksize * (stacksize - 1) / 2];

        opencv_core.Mat mask = new opencv_core.Mat();
        IntPointer intPtrChannels = new IntPointer(0, 1, 2);
        IntPointer intPtrHistSize = new IntPointer(blueBins, greenBins, redBins);
        FloatPointer fltPtrRanges = new FloatPointer(0.0f, 255.0f, 0.0f, 255.0f, 0.0f, 255.0f);
        PointerPointer ptptranges = new PointerPointer(fltPtrRanges, fltPtrRanges, fltPtrRanges);

        opencv_core.Mat hist1 = new opencv_core.Mat();
        opencv_core.Mat hist2 = new opencv_core.Mat();
        int n = 0;
        for (int i = 0; i < mvec.size() - 1; i++) {
            calcHist(mvec.get(i), 1, intPtrChannels, mask, hist1, 3, intPtrHistSize, ptptranges, true, false);
            opencv_core.normalize(hist1, hist1);
            for (int j = i + 1; j < mvec.size(); j++) {
                calcHist(mvec.get(j), 1, intPtrChannels, mask, hist2, 3, intPtrHistSize, ptptranges, true, false);
                opencv_core.normalize(hist2, hist2);
                comparison[0][n] = opencv_imgproc.compareHist(hist1, hist2, opencv_imgproc.CV_COMP_CORREL);
                comparison[1][n] = opencv_imgproc.compareHist(hist1, hist2, opencv_imgproc.CV_COMP_CHISQR);
                comparison[2][n] = opencv_imgproc.compareHist(hist1, hist2, opencv_imgproc.CV_COMP_INTERSECT);
                comparison[3][n] = opencv_imgproc.compareHist(hist1, hist2, opencv_imgproc.CV_COMP_BHATTACHARYYA);
                n++;
            }
        }

        String headings = "Method\t";
        for (int i = 0; i < mvec.size() - 1; i++) {
            for (int j = i + 1; j < mvec.size(); j++) {
                headings = headings + (i + 1) + "-" + (j + 1) + "\t";
            }
        }
        headings = headings.substring(0, headings.lastIndexOf("\t"));
        ArrayList list = new ArrayList();

        String row1 = "Correlation\t";
        String row2 = "CHI Square\t";
        String row3 = "Intersection\t";
        String row4 = "BHATTACHARYYA\t";
        for (int i = 0; i < comparison[0].length - 1; i++) {
            row1 = row1 + comparison[0][i] + "\t";
            row2 = row2 + comparison[1][i] + "\t";
            row3 = row3 + comparison[2][i] + "\t";
            row4 = row4 + comparison[3][i] + "\t";

        }
        row1 = row1 + comparison[0][comparison[0].length - 1] ;
        row2 = row2 + comparison[1][comparison[0].length - 1];
        row3 = row3 + comparison[2][comparison[0].length - 1];
        row4 = row4 + comparison[3][comparison[0].length - 1];
        
        list.add(row1);
        list.add(row2);
        list.add(row3);
        list.add(row4);

        TextWindow textWindow = new TextWindow("Similarity Table", headings, list, 600, 400);
        textWindow.setVisible(true);

    }

    private boolean showDialog() {

        GenericDialog gd = new GenericDialog("BGR Histogram Comparison");
        gd.addNumericField("Blue bins", blueBins, 0);
        gd.addNumericField("Green bins", greenBins, 0);
        gd.addNumericField("Red bins", redBins, 0);
        //gd.addNumericField("Gaussian Kernel width:", gaussianKernelWidth, 0);

        gd.showDialog();
        if (gd.wasCanceled()) {
            return false;
        }

        blueBins = (int) gd.getNextNumber();
        if (blueBins < 1) {
            blueBins = 1;
        }
        if (blueBins > 255) {
            blueBins = 255;
        }

        greenBins = (int) gd.getNextNumber();
        if (greenBins < 1) {
            greenBins = 1;
        }
        if (greenBins > 255) {
            greenBins = 255;
        }
        redBins = (int) gd.getNextNumber();
        if (redBins < 1) {
            redBins = 1;
        }
        if (redBins > 255) {
            redBins = 255;
        }

        return true;
    }
    
    
    
    

}
