package ijopencv.examples;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;
import ijopencv.IJ2OpenCV;
import ijopencv.ImageConverter;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_imgproc;

/**
 *
 * @author jonathan
 */
public class CannyEdgeDetectionJ_ implements PlugIn {

    double minT, maxT;

    @Override
    public void run(String arg) {
        ImagePlus imp = IJ.getImage();
        // Converter
        ImageConverter ic = new ImageConverter();

        opencv_core.Mat m = ic.convertTo(imp);
        opencv_imgproc.cvtColor(m, m, opencv_imgproc.COLOR_BGR2GRAY);
        Mat res = new opencv_core.Mat();

        if (!showDialog()) {
            return;
        }

        opencv_imgproc.Canny(m, res, minT, maxT);

        ImagePlus imp2 = ic.convertFrom(res);
        imp2.show();

    }

    private boolean showDialog() {

        GenericDialog gd = new GenericDialog("Canny Edge Detector");
        gd.addNumericField("Low threshold:", minT, 1);
        gd.addNumericField("High threshold:", maxT, 255);
        //gd.addNumericField("Gaussian Kernel width:", gaussianKernelWidth, 0);

        gd.showDialog();
        if (gd.wasCanceled()) {
            return false;
        }

        minT = (float) gd.getNextNumber();
        if (minT < 0.1f) {
            minT = 0.1f;
        }
        maxT = (float) gd.getNextNumber();
        if (maxT < 0.1f) {
            maxT = 0.1f;
        }

        return true;
    }

}
