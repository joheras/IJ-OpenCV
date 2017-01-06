package ijopencv.examples;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import ij.IJ;
import ij.ImagePlus;
import ij.gui.Line;
import ij.plugin.PlugIn;
import ij.plugin.frame.RoiManager;
import ijopencv.ij.ImagePlusMatConverter;
import ijopencv.opencv.MatImagePlusConverter;
import ijopencv.opencv.MatListLineConverter;
import java.util.ArrayList;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_imgproc;

/**
 *
 * @author jonathan
 */
public class HoughLinesJ_ implements PlugIn {

    @Override
    public void run(String arg) {
        ImagePlus imp = IJ.getImage();
        // Converters
        ImagePlusMatConverter ic = new ImagePlusMatConverter();
        MatImagePlusConverter mip = new MatImagePlusConverter();

        opencv_core.Mat m = ic.convert(imp, Mat.class);
        MatListLineConverter lc = new MatListLineConverter();
        Mat dst = new opencv_core.Mat();

        opencv_imgproc.Canny(m, dst, 50, 200);

        Mat lines = new Mat();
        opencv_imgproc.HoughLines(dst, lines, 1, opencv_core.CV_PI / 180, 100);

        ArrayList<Line> linesIJ = new ArrayList<Line>();
        linesIJ = lc.convert(lines, linesIJ.getClass());
        RoiManager rm = new RoiManager();
        rm.setVisible(true);

        for (int i = 0; i < linesIJ.size(); i++) {
            rm.add(imp, linesIJ.get(i), i);
        }

    }

}
