package ijopencv.examples;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import ij.ImagePlus;
import ij.gui.PolygonRoi;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import ijopencv.ij.PolygonRoiMatConverter;
import ijopencv.opencv.RotatedRectPolygonRoiConverter;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_imgproc;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 *
 * @author jonathan
 */
@Plugin(type = Command.class, headless = true, menuPath = "Plugins>IJ-OpenCV-plugins>Rotatedrect from polygon roi")
public class RotatedRectFromPolygonROIJ_ implements Command {

  
    @Parameter
    private ImagePlus imp;

    @Override
    public void run() {
        PolygonRoi pr = (PolygonRoi) imp.getRoi();
        // Converters
        PolygonRoiMatConverter pc = new PolygonRoiMatConverter();
        RotatedRectPolygonRoiConverter p2c = new RotatedRectPolygonRoiConverter();

        Mat m = pc.convert(pr,Mat.class);

        opencv_core.RotatedRect rr = opencv_imgproc.minAreaRect(m);
        opencv_core.Point2f pt = new opencv_core.Point2f(9);

        rr.points(pt);

        PolygonRoi newpr = p2c.convert(pt,PolygonRoi.class);

        imp.setRoi(newpr);

    }

}
