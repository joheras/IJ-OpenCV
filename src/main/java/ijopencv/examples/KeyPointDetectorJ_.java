/*-
 * #%L
 * A library that allows the connection of ImageJ and OpenCV.
 * %%
 * Copyright (C) 2017 - 2021 University of La Rioja
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
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
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.KeyPointVector;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_features2d;
import org.bytedeco.javacpp.opencv_features2d.KAZE;
import org.bytedeco.javacpp.opencv_xfeatures2d;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

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
        ImagePlusMatConverter ic = new ImagePlusMatConverter();
        KeyPointVectorPointRoiConverter kpc = new KeyPointVectorPointRoiConverter();
        PointRoiKeyPointVectorConverter roi2kp = new PointRoiKeyPointVectorConverter();
        
        
        opencv_core.Mat imageOpenCV = ic.convert(imp, Mat.class);

        KeyPointVector kpv = new opencv_core.KeyPointVector();

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
