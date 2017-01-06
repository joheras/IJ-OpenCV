package ijopencv.examples;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import ij.IJ;
import ij.ImagePlus;
import ij.gui.OvalRoi;
import ij.gui.PolygonRoi;
import ij.plugin.PlugIn;
import ij.plugin.frame.RoiManager;
import ijopencv.ij.PolygonRoiMatConverter;
import ijopencv.opencv.MatPointRoiConverter;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_imgproc;

/**
 *
 * @author jonathan
 */
public class ConvexHullFromPolygonROIJ_ implements PlugIn {

    @Override
    public void run(String arg) {

        ImagePlus imp = IJ.getImage();

        // Get the ROI and check that it is a polygon
        PolygonRoi r = (PolygonRoi) imp.getRoi();
        
        if(r==null){
            IJ.error("Select first a polygon ROI");
            return;
        }
        
        
        // Converter
        PolygonRoiMatConverter pc = new PolygonRoiMatConverter();
        MatPointRoiConverter mpc = new MatPointRoiConverter();
        
        Mat m = pc.convert(r,Mat.class);
        Mat convexHull = new Mat();
        opencv_imgproc.convexHull(m, convexHull);
        
        PolygonRoi pr = mpc.convert(convexHull,PolygonRoi.class);
        
        RoiManager rm = new RoiManager();
        rm.add(imp, r, 0);
        rm.add(imp,pr,1);
        
        
    }

}
