/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import ij.IJ;
import ij.ImagePlus;
import ij.gui.OvalRoi;
import ij.plugin.PlugIn;
import ijopencv.Circle2fCV;
import ijopencv.Circle2fConverter;
import ijopencv.ImageConverter;
import ijopencv.OpenCV2IJ;
import org.bytedeco.javacpp.opencv_core;
import static org.bytedeco.javacpp.opencv_core.split;
import static org.bytedeco.javacpp.opencv_highgui.imshow;
import static org.bytedeco.javacpp.opencv_highgui.waitKey;
import org.bytedeco.javacpp.opencv_imgproc;

/**
 *
 * @author jonathan
 */
public class DetectOxidationJ_ implements PlugIn {

    @Override
    public void run(String arg) {
        ImagePlus imp = IJ.getImage();
        OvalRoi or = detectROI(imp);
        imp.setRoi(or);
        
        
    }
    
    public OvalRoi detectROI(ImagePlus imp){
        // Converter 
        ImageConverter ic = new ImageConverter();
        
        
        opencv_core.Mat newImage = ic.convertTo(imp);
        opencv_core.Mat hsv = new opencv_core.Mat();
        opencv_imgproc.cvtColor(newImage,hsv,opencv_imgproc.COLOR_BGR2HSV);
        
        opencv_core.MatVector mv = new opencv_core.MatVector(3);
        split(hsv, mv);
        
        opencv_core.Mat thres = new opencv_core.Mat();
        opencv_imgproc.threshold(mv.get(1), thres, 0, 255, opencv_imgproc.CV_THRESH_BINARY_INV + opencv_imgproc.CV_THRESH_OTSU);
        
        opencv_core.MatVector contours = new opencv_core.MatVector();
        opencv_imgproc.findContours(thres,contours,opencv_imgproc.RETR_TREE,opencv_imgproc.CHAIN_APPROX_SIMPLE);
        
        opencv_core.Point2f c = new opencv_core.Point2f();
        float[] r  = new float[1];
        
        
        opencv_imgproc.minEnclosingCircle(contours.get(1),c,r);  
        
        // Circle converter
        Circle2fConverter cc =new Circle2fConverter();
        
        OvalRoi or = cc.convertFrom(new Circle2fCV(c,r[0]));
        
        return or;
    
    
    
    }
    
}
