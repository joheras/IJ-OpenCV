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
import net.imagej.ImageJ;
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
@Plugin(type = Command.class, headless = true, menuPath = "Plugins>IJ-OpenCV-plugins>Detect circles")
public class DetectCirclesJ_ implements Command {

    @Parameter
    private ImagePlus imp;

    @Override
    public void run() {

        // Converters
        ImagePlusMatConverter ic = new ImagePlusMatConverter();
        opencv_core.Mat m = ic.convert(imp, Mat.class);
        MatListOvalRoiConverter cc = new MatListOvalRoiConverter();

        Mat gray = new Mat();
        opencv_imgproc.cvtColor(m, gray, opencv_imgproc.COLOR_BGR2GRAY);

        Mat circles = new Mat();
        opencv_imgproc.HoughCircles(gray, circles, opencv_imgproc.CV_HOUGH_GRADIENT, 1.2, 100);

        ArrayList<OvalRoi> or = new ArrayList<OvalRoi>();
        or = cc.convert(circles, or.getClass());

        RoiManager rm = new RoiManager();

        for (int i = 0; i < or.size(); i++) {
            rm.add(imp, or.get(i), i);
        }

    }

    public static void main(final String... args) throws Exception {
        // Launch ImageJ as usual.
        final ImageJ ij = new ImageJ();
        ij.launch(args);

        // Launch the "OpenImage" command.
        ij.command().run(DetectCirclesJ_.class, true);
    }

}
