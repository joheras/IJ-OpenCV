package ijopencv.examples;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import ij.IJ;
import ij.ImagePlus;
import ij.gui.OvalRoi;
import ij.plugin.filter.PlugInFilter;
import ij.plugin.frame.RoiManager;
import ij.process.ImageProcessor;
import ijopencv.ij.ImagePlusMatConverter;
import ijopencv.opencv.MatListOvalRoiConverter;
import java.util.ArrayList;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_imgproc;

/**
 *
 * @author jonathan
 */
public class DetectCirclesJ_ implements PlugInFilter{

ImagePlus imp;

    @Override
    public int setup(String arg, ImagePlus imp) {
        this.imp=imp;
        return DOES_ALL + NO_CHANGES;
    }

    @Override
    public void run(ImageProcessor ip) {
        ImagePlus imp = IJ.getImage();
        
        // Converters
         ImagePlusMatConverter ic = new ImagePlusMatConverter();
        opencv_core.Mat m = ic.convert(imp,Mat.class);
        MatListOvalRoiConverter cc = new MatListOvalRoiConverter();
        
        Mat gray = new Mat();
        opencv_imgproc.cvtColor(m,gray,opencv_imgproc.COLOR_BGR2GRAY);
        
        Mat circles = new Mat();
        opencv_imgproc.HoughCircles(gray,circles,opencv_imgproc.CV_HOUGH_GRADIENT,1.2,100);
        
        ArrayList<OvalRoi> or = new ArrayList<OvalRoi>();
        or= cc.convert(circles,or.getClass());
        
        RoiManager rm = new RoiManager();
        
        for(int i=0;i<or.size();i++){
            rm.add(imp, or.get(i), i);
        }
        
    
    
    
    }
    
}
