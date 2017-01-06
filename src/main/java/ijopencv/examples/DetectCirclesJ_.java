package ijopencv.examples;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import ij.IJ;
import ij.ImagePlus;
import ij.gui.OvalRoi;
import ij.gui.Roi;
import ij.plugin.PlugIn;
import ij.plugin.frame.RoiManager;
import ijopencv.ij.ImagePlusMatConverter;
import ijopencv.opencv.MatListOvalRoiConverter;
import java.util.ArrayList;
import java.util.List;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_imgproc;

/**
 *
 * @author jonathan
 */
public class DetectCirclesJ_ implements PlugIn {

    @Override
    public void run(String arg) {
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
