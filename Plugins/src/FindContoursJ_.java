/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import ij.IJ;
import ij.ImagePlus;
import ij.gui.PolygonRoi;
import ij.gui.Roi;
import ij.plugin.PlugIn;
import ij.plugin.frame.RoiManager;
import ijopencv.IJ2OpenCV;
import ijopencv.ImageConverter;
import ijopencv.OpenCV2IJ;
import ijopencv.PolygonsConverter;
import java.util.ArrayList;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.bytedeco.javacpp.opencv_imgproc;

/**
 *
 * @author jonathan
 */
public class FindContoursJ_ implements PlugIn {

    @Override
    public void run(String arg) {
        ImagePlus imp = IJ.getImage();
        
        // Converters
        ImageConverter ic = new ImageConverter();
        PolygonsConverter pc = new PolygonsConverter();
        
        
        opencv_core.Mat m = ic.convertTo(imp);
        opencv_core.Mat gray = new opencv_core.Mat();
        opencv_imgproc.cvtColor(m,gray,opencv_imgproc.COLOR_BGR2GRAY);
        
        MatVector contours = new opencv_core.MatVector();
        
        opencv_imgproc.findContours(gray,contours,opencv_imgproc.RETR_LIST,opencv_imgproc.CHAIN_APPROX_SIMPLE);
        
        ArrayList<PolygonRoi> contoursROI = pc.convertFrom(contours);
        
         // Add rectangles to ROI Manager
        RoiManager rm = new RoiManager();
        rm.setVisible(true);
        for (PolygonRoi contoursROI1 : contoursROI) {
            rm.add(imp, contoursROI1, 0);
        }
        
        
        
    
    }
    
}
