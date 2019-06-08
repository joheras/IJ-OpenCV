package ijopencv.examples;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import ij.ImagePlus;
import ij.gui.Line;
import ij.gui.GenericDialog;
import ij.plugin.filter.PlugInFilter;
import ij.plugin.frame.RoiManager;
import ij.process.ImageProcessor;
import ijopencv.ij.ImagePlusMatConverter;
import ijopencv.opencv.MatImagePlusConverter;
import ijopencv.opencv.MatListLineConverter;
import java.util.ArrayList;
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
@Plugin(type = Command.class, headless = true, menuPath = "Plugins>IJ-OpenCV-plugins>Hough lines")
public class HoughLinesJ_  implements Command {


    @Parameter
    private ImagePlus imp;
    
    @Override
    public void run() {
		
		// GUI
		GenericDialog gd = new GenericDialog("Hough Line");
		gd.addNumericField("Minimum line length (pixels)", 10, 0);
		gd.addNumericField("Step_line spacing iteration (pixels)", 10, 0);
		gd.addNumericField("Start_angle iteration (degrees)", 0, 0);
		gd.addNumericField("Stop_angle iteration (degrees)", 180, 0);
		gd.addNumericField("Step_angle iteration (degrees)", 1, 0);
		
		
		
		
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
