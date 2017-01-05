package ijopencv.examples;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import ij.IJ;
import ij.ImagePlus;
import ij.Prefs;
import ij.gui.GenericDialog;
import ij.gui.PointRoi;
import ij.plugin.PlugIn;
import static ij.plugin.Thresholder.backgrounds;
import static ij.plugin.Thresholder.methods;
import ij.process.AutoThresholder;
import ijopencv.ImageConverter;
import ijopencv.KeyPointsConverter;
import java.awt.Choice;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.KeyPointVector;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_features2d;
import static org.bytedeco.javacpp.opencv_features2d.AGAST;
import static org.bytedeco.javacpp.opencv_features2d.AGAST;
import static org.bytedeco.javacpp.opencv_features2d.AGAST;
import org.bytedeco.javacpp.opencv_features2d.Feature2D;
import org.bytedeco.javacpp.opencv_features2d.KAZE;
import org.bytedeco.javacpp.opencv_xfeatures2d;

/**
 *
 * @author jonathan
 */
public class KeyPointDetectorJ_ implements PlugIn {

    public static final String[] methods = {
        "AGAST","AKAZE","BRISK","FAST","GFFT","KAZE","MSER","ORB","SIFT","SimpleBlob","SURF"
    };
    private String method = methods[0];
    private Vector choices;
    private ImagePlus imp;

    @Override
    public void run(String arg) {
        imp = IJ.getImage();

        GenericDialog gd = new GenericDialog("Select Keypoint detector");
        gd.addChoice("Method", methods, method);
        gd.showDialog();
        if (gd.wasCanceled()) {
            return;
        }
        method = gd.getNextChoice();
        PointRoi res = keyPointDetection(method);
        imp.setRoi(res);

    }

    public PointRoi keyPointDetection(String method) {
        opencv_features2d.Feature2D f2d = new opencv_features2d.Feature2D();

        if (method == "SIFT") {
            f2d = opencv_xfeatures2d.SIFT.create();
        }
        if (method == "SURF") {
            f2d = opencv_xfeatures2d.SURF.create();
        }
        if (method == "ORB") {
            f2d = opencv_features2d.ORB.create();
            
        }
        if (method == "BRISK") {
            f2d = opencv_features2d.BRISK.create();
        }
        if (method == "MSER") {
            f2d = opencv_features2d.MSER.create();
        }
        if (method == "AKAZE") {
            f2d = opencv_features2d.AKAZE.create();
        }
        if (method == "SimpleBlob") {
            f2d = opencv_features2d.SimpleBlobDetector.create();
        } 
        
        if (method == "KAZE") {
            f2d = KAZE.create();
        } 
        if (method == "AGAST") {
            f2d = opencv_features2d.AgastFeatureDetector.create();
        } 
        if (method == "FAST") {
            f2d = opencv_features2d.FastFeatureDetector.create();
        } 
        if (method == "GFFT") {
            f2d = opencv_features2d.GFTTDetector.create();
        }
               

        // Converters
        ImageConverter ic = new ImageConverter();
        KeyPointsConverter kpc = new KeyPointsConverter();
        Mat imageOpenCV = ic.convertTo(imp);

        
        
        KeyPointVector kpv = new opencv_core.KeyPointVector();

        f2d.detect(imageOpenCV, kpv);

        PointRoi pr = kpc.convertFrom(kpv);
        return pr;

    }

}
