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
import ijopencv.CirclesConverter;
import ijopencv.ImageConverter;
import java.util.ArrayList;
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
        ImageConverter ic = new ImageConverter();
        CirclesConverter cc = new CirclesConverter();
        
        Mat m = ic.convertTo(imp);
        Mat gray = new Mat();
        opencv_imgproc.cvtColor(m,gray,opencv_imgproc.COLOR_BGR2GRAY);
        
        Mat circles = new Mat();
        opencv_imgproc.HoughCircles(gray,circles,opencv_imgproc.CV_HOUGH_GRADIENT,1.2,100);
        
        ArrayList<OvalRoi> or = cc.convertFrom(circles);
        
        RoiManager rm = new RoiManager();
        
        for(int i=0;i<or.size();i++){
            rm.add(imp, or.get(i), i);
        }
        
    
    
    
    }
    
}
