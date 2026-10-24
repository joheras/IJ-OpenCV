/*-
 * #%L
 * A library that allows the connection of ImageJ and OpenCV.
 * %%
 * Copyright (C) 2017 - 2026 University of La Rioja
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package ijopencv.examples;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import net.imagej.ImageJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.gui.PointRoi;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import ijopencv.ij.ImagePlusMatConverter;
import ijopencv.opencv.KeyPointVectorPointRoiConverter;
import ijopencv.ij.PointRoiKeyPointVectorConverter;
import java.util.Vector;
import org.bytedeco.opencv.opencv_core.KeyPointVector;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_features2d.AKAZE;
import org.bytedeco.opencv.opencv_features2d.BRISK;
import org.bytedeco.opencv.opencv_features2d.KAZE;
import org.bytedeco.opencv.opencv_features2d.MSER;
import org.bytedeco.opencv.opencv_features2d.ORB;
import org.bytedeco.opencv.opencv_features2d.SIFT;
import org.bytedeco.opencv.opencv_xfeatures2d.SURF;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.bytedeco.opencv.opencv_features2d.AgastFeatureDetector;
import org.bytedeco.opencv.opencv_features2d.FastFeatureDetector;
import org.bytedeco.opencv.opencv_features2d.Feature2D;
import org.bytedeco.opencv.opencv_features2d.GFTTDetector;
import org.bytedeco.opencv.opencv_features2d.SimpleBlobDetector;

/**
 *
 * @author jonathan
 */
@Plugin(type = Command.class, headless = true, menuPath = "Plugins>IJ-OpenCV-plugins>Keypoint detector")
public class KeyPointDetectorJ_ implements Command {

    @Parameter
    private ImagePlus imp;

    public static final String[] methods = {
        "AGAST", "AKAZE", "BRISK", "FAST", "GFFT", "KAZE", "MSER", "ORB", "SIFT", "SimpleBlob", "SURF"
    };
    private String method = methods[0];
    private Vector choices;

    @Override
    public void run() {

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
        Feature2D f2d = new Feature2D();

        if (method == "SIFT") {
            f2d = SIFT.create();
        }
        if (method == "SURF") {
            f2d = SURF.create();
        }
        if (method == "ORB") {
            f2d = ORB.create();

        }
        if (method == "BRISK") {
            f2d = BRISK.create();
        }
        if (method == "MSER") {
            f2d = MSER.create();
        }
        if (method == "AKAZE") {
            f2d = AKAZE.create();
        }
        if (method == "SimpleBlob") {
            f2d = SimpleBlobDetector.create();
        }

        if (method == "KAZE") {
            f2d = KAZE.create();
        }
        if (method == "AGAST") {
            f2d = AgastFeatureDetector.create();
        }
        if (method == "FAST") {
            f2d = FastFeatureDetector.create();
        }
        if (method == "GFFT") {
            f2d = GFTTDetector.create();
        }

        // Converters
        ImagePlusMatConverter ic = new ImagePlusMatConverter();
        KeyPointVectorPointRoiConverter kpc = new KeyPointVectorPointRoiConverter();
        PointRoiKeyPointVectorConverter roi2kp = new PointRoiKeyPointVectorConverter();
        
        
        Mat imageOpenCV = ic.convert(imp, Mat.class);

        KeyPointVector kpv = new KeyPointVector();

        f2d.detect(imageOpenCV, kpv);

        PointRoi pr = kpc.convert(kpv, PointRoi.class);
        roi2kp.convert(pr, KeyPointVector.class);
        
        
        return pr;

    }

    public static void main(final String... args) throws Exception {
        // Launch ImageJ as usual.
        final ImageJ ij = new ImageJ();
        ij.launch(args);
    }

}
