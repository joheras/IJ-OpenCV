package ijopencv.examples;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import ijopencv.ij.ImagePlusMatConverter;
import ijopencv.opencv.MatImagePlusConverter;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_imgproc;

/**
 *
 * @author jonathan
 */
public class CannyEdgeDetectionJ_ implements PlugInFilter {

    double minT, maxT;

    ImagePlus imp;

    @Override
    public int setup(String arg, ImagePlus imp) {
        this.imp=imp;
        return DOES_ALL + NO_CHANGES;
    }

    @Override
    public void run(ImageProcessor ip) {
        ImagePlus imp = IJ.getImage();
        // Converter
        
        ImagePlusMatConverter ic = new ImagePlusMatConverter();
        MatImagePlusConverter mip = new MatImagePlusConverter();

        opencv_core.Mat m = ic.convert(imp,Mat.class);
        opencv_imgproc.cvtColor(m, m, opencv_imgproc.COLOR_BGR2GRAY);
        Mat res = new opencv_core.Mat();

        if (!showDialog()) {
            return;
        }

        opencv_imgproc.Canny(m, res, minT, maxT);

        ImagePlus imp2 = mip.convert(res,ImagePlus.class);
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
