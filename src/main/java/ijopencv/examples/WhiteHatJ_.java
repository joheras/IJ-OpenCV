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
import ij.plugin.filter.PlugInFilter;
import ij.process.AutoThresholder;
import ij.process.ImageProcessor;
import ijopencv.ij.ImagePlusMatConverter;
import ijopencv.opencv.MatImagePlusConverter;
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
import org.bytedeco.javacpp.opencv_imgproc;
import static org.bytedeco.javacpp.opencv_imgproc.MORPH_BLACKHAT;
import static org.bytedeco.javacpp.opencv_imgproc.MORPH_RECT;
import static org.bytedeco.javacpp.opencv_imgproc.getStructuringElement;
import static org.bytedeco.javacpp.opencv_imgproc.morphologyEx;
import org.bytedeco.javacpp.opencv_xfeatures2d;

/**
 *
 * @author jonathan
 */
public class WhiteHatJ_ implements PlugInFilter {
int xSize, ySize;



    ImagePlus imp;

    @Override
    public int setup(String arg, ImagePlus imp) {
        this.imp=imp;
        return DOES_RGB; 
    }

    @Override
    public void run(ImageProcessor ip) {
        ImagePlus imp = IJ.getImage();
        // Converter
        ImagePlusMatConverter ic = new ImagePlusMatConverter();
        MatImagePlusConverter mip = new MatImagePlusConverter();

        opencv_core.Mat m = ic.convert(imp,Mat.class);
        opencv_imgproc.cvtColor(m, m, opencv_imgproc.COLOR_BGR2GRAY);
        Mat res = new opencv_core.Mat();

        if (!showDialog()) {
            return;
        }
        
        Mat element = getStructuringElement(MORPH_RECT, new opencv_core.Size(xSize, ySize));

        morphologyEx(m, res, opencv_imgproc.MORPH_TOPHAT, element);

        ImagePlus imp2 = mip.convert(res,ImagePlus.class);
        imp2.show();

    }

    private boolean showDialog() {

        GenericDialog gd = new GenericDialog("White Hat Kernel");
        gd.addNumericField("X size", xSize, 1);
        gd.addNumericField("Y size", ySize, 1);
        //gd.addNumericField("Gaussian Kernel width:", gaussianKernelWidth, 0);

        gd.showDialog();
        if (gd.wasCanceled()) {
            return false;
        }

        xSize = (int) gd.getNextNumber();
        if (xSize < 1) {
            xSize = 1;
        }
        ySize = (int) gd.getNextNumber();
        if (ySize < 1) {
            ySize = 1;
        }

        return true;
    }


}
