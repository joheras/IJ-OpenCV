package ijopencv.examples;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import ij.IJ;
import ij.ImagePlus;
import ij.gui.OvalRoi;
import ij.plugin.PlugIn;
import ijopencv.ij.ImagePlusMatConverter;
import ijopencv.ij.OvalRoiCircleCVConverter;
import ijopencv.utils.CircleCV;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bytedeco.javacpp.FloatPointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.javacpp.opencv_core;
import static org.bytedeco.javacpp.opencv_core.CV_8UC1;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_imgproc;

/**
 *
 * @author jonathan
 */
public class measureOxidationJ_ implements PlugIn {

    @Override
    public void run(String arg) {
        try {
            ImagePlus imp = IJ.getImage();
            OvalRoi or = (OvalRoi)imp.getRoi();
            double oxidation = computeOxidation(imp, or);
            IJ.error("Oxidation level", ""+oxidation);
            
            
        } catch (Exception ex) {
            Logger.getLogger(measureOxidationJ_.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       
        
        
    }
    
    
    public double computeOxidation(ImagePlus imp, OvalRoi or) throws Exception {

        ImagePlusMatConverter ic  = new ImagePlusMatConverter();
        OvalRoiCircleCVConverter cc = new OvalRoiCircleCVConverter();
        
        opencv_core.Mat newImage = ic.convert(imp,Mat.class);
        // Create mask
        opencv_core.Mat mask = new opencv_core.Mat(newImage.size(), CV_8UC1, opencv_core.Scalar.all(0));
        CircleCV c = cc.convert(or,CircleCV.class);

        opencv_imgproc.circle(mask, c.getCenter(), c.getRadius(), new opencv_core.Scalar(255, 255, 255, 0), -1, 8, 0);

        // Image in LUV colorspace
        opencv_core.Mat imageLUV = new opencv_core.Mat();
        opencv_imgproc.cvtColor(newImage, imageLUV, opencv_imgproc.COLOR_BGR2Luv);

        opencv_core.Mat hist = new opencv_core.Mat();
        opencv_core.Mat blackHist = new opencv_core.Mat();

        // Since C++ `calcHist` is using arrays of arrays we need wrap to do some wrapping
        // in `IntPointer` and `PointerPointer` objects.
        int[] channels = {0, 1, 2};
        int[] bins = {8, 8, 8};
        float[] ranges = {0.0f, 255.0f};
        IntPointer intPtrChannels = new IntPointer(0, 1, 2);
        IntPointer intPtrHistSize = new IntPointer(8, 8, 8);
        FloatPointer fltPtrRanges = new FloatPointer(0.0f, 255.0f, 0.0f, 255.0f, 0.0f, 255.0f);
        PointerPointer ptptranges = new PointerPointer(fltPtrRanges, fltPtrRanges, fltPtrRanges);

        opencv_imgproc.calcHist(imageLUV, 1, intPtrChannels, mask, hist, 3, intPtrHistSize, ptptranges, true, false);
        opencv_core.normalize(hist, hist);

        opencv_core.Mat newImage2 = new opencv_core.Mat(newImage.size(), CV_8UC1, opencv_core.Scalar.all(0));
        opencv_imgproc.cvtColor(newImage2, newImage2, opencv_imgproc.COLOR_GRAY2BGR);

        opencv_imgproc.calcHist(newImage2, 1, intPtrChannels, mask, blackHist, 3, intPtrHistSize, ptptranges, true, false);
        opencv_core.normalize(blackHist, blackHist);

        return opencv_imgproc.compareHist(hist, blackHist, opencv_imgproc.CV_COMP_CHISQR);

    }
    
    
}
