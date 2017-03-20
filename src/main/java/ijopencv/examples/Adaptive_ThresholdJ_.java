package ijopencv.examples;


import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import ijopencv.ij.ImagePlusMatConverter;
import ijopencv.opencv.MatImagePlusConverter;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;
import static org.bytedeco.javacpp.opencv_imgproc.ADAPTIVE_THRESH_GAUSSIAN_C;
import static org.bytedeco.javacpp.opencv_imgproc.ADAPTIVE_THRESH_MEAN_C;
import static org.bytedeco.javacpp.opencv_imgproc.THRESH_BINARY;
import static org.bytedeco.javacpp.opencv_imgproc.THRESH_BINARY_INV;
import static org.bytedeco.javacpp.opencv_imgproc.adaptiveThreshold;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jonathan
 */
public class Adaptive_ThresholdJ_ implements PlugInFilter {

    String method;
    String thresholdmethod;
    int maxValue=255;
    int blockSize=3;



    private boolean showDialog() {

        GenericDialog gd = new GenericDialog("Adaptive Threshold");
        String[] items = {"Mean","Gaussian"};
        String[] itemsThreshold = {"Binary","Binary Inv"};
        gd.addChoice("Method", items, items[0]);
        gd.addChoice("Threshold Type", itemsThreshold, itemsThreshold[0]);
        gd.addNumericField("Max Value", maxValue, 0);
        gd.addNumericField("Block size", blockSize, 0);
        //gd.addNumericField("Gaussian Kernel width:", gaussianKernelWidth, 0);

        gd.showDialog();
        if (gd.wasCanceled()) {
            return false;
        }
        
        method = gd.getNextChoice();
        thresholdmethod = gd.getNextChoice();
        maxValue = (int) gd.getNextNumber();
        blockSize = (int) gd.getNextNumber();
        return true;
    }

    ImagePlus imp;
    
    @Override
    public int setup(String arg, ImagePlus imp) {
        this.imp=imp;
        return DOES_RGB; 
    }


    @Override
    public void run(ImageProcessor ip) {
        // Converter
        ImagePlusMatConverter ic = new ImagePlusMatConverter();
        MatImagePlusConverter mip = new MatImagePlusConverter();

        opencv_core.Mat m = ic.convert(imp,Mat.class);
       // opencv_imgproc.cvtColor(m, m, opencv_imgproc.COLOR_BGR2GRAY);
        opencv_core.Mat res = new opencv_core.Mat();

        if (!showDialog()) {
            return;
        }
        
        int adaptiveMethod;
        if(method=="Mean"){
            adaptiveMethod = ADAPTIVE_THRESH_MEAN_C;
        }else{
            adaptiveMethod = ADAPTIVE_THRESH_GAUSSIAN_C;
        }
        
         int thresType;
        if(thresholdmethod=="Binary"){
            thresType = THRESH_BINARY;
        }else{
            thresType = THRESH_BINARY_INV;
        }
        
        
        adaptiveThreshold(m, res, maxValue, adaptiveMethod, thresType, blockSize, 2);

        ImagePlus imp2 = mip.convert(res,ImagePlus.class);
        imp2.show();

    }


    
}
