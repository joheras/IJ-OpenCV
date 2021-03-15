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


import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import ijopencv.ij.ImagePlusMatConverter;
import ijopencv.opencv.MatImagePlusConverter;
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
@Plugin(type = Command.class, headless = true, menuPath = "Plugins>IJ-OpenCV-plugins>Canny Edge Detection")
public class CannyEdgeDetectionJ_ implements Command {

    double minT, maxT;

     @Parameter
    private ImagePlus imp;

    @Override
    public void run() {
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

        opencv_imgproc.Canny(m, res, minT, maxT);

        ImagePlus imp2 = mip.convert(res,ImagePlus.class);
        imp2.show();

    }

    private boolean showDialog() {

        GenericDialog gd = new GenericDialog("Canny Edge Detector");
        gd.addNumericField("Low threshold:", minT, 1);
        gd.addNumericField("High threshold:", maxT, 255);
        //gd.addNumericField("Gaussian Kernel width:", gaussianKernelWidth, 0);

        gd.showDialog();
        if (gd.wasCanceled()) {
            return false;
        }

        minT = (float) gd.getNextNumber();
        if (minT < 0.1f) {
            minT = 0.1f;
        }
        maxT = (float) gd.getNextNumber();
        if (maxT < 0.1f) {
            maxT = 0.1f;
        }

        return true;
    }

}
