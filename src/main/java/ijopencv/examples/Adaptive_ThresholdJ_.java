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

import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import ijopencv.ij.ImagePlusMatConverter;
import ijopencv.opencv.MatImagePlusConverter;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_imgproc;
import static org.bytedeco.javacpp.opencv_imgproc.ADAPTIVE_THRESH_GAUSSIAN_C;
import static org.bytedeco.javacpp.opencv_imgproc.ADAPTIVE_THRESH_MEAN_C;
import static org.bytedeco.javacpp.opencv_imgproc.THRESH_BINARY;
import static org.bytedeco.javacpp.opencv_imgproc.THRESH_BINARY_INV;
import static org.bytedeco.javacpp.opencv_imgproc.adaptiveThreshold;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author jonathan
 */
@Plugin(type = Command.class, headless = true, menuPath = "Plugins>IJ-OpenCV-plugins>Adaptive Threshold")
public class Adaptive_ThresholdJ_ implements Command {

    String method;
    String thresholdmethod;
    int maxValue = 255;
    int blockSize = 3;

    private boolean showDialog() {

        GenericDialog gd = new GenericDialog("Adaptive Threshold");
        String[] items = {"Mean", "Gaussian"};
        String[] itemsThreshold = {"Binary", "Binary Inv"};
        gd.addChoice("Method", items, items[0]);
        gd.addChoice("Threshold Type", itemsThreshold, itemsThreshold[0]);
        gd.addNumericField("Max Value", maxValue, 0);
        gd.addNumericField("Block size", blockSize, 0);
        //gd.addNumericField("Gaussian Kernel width:", gaussianKernelWidth, 0);

        gd.showDialog();
        if (gd.wasCanceled()) {
            return false;
        }

        method = gd.getNextChoice();
        thresholdmethod = gd.getNextChoice();
        maxValue = (int) gd.getNextNumber();
        blockSize = (int) gd.getNextNumber();
        return true;
    }

    @Parameter
    private ImagePlus imp;

    @Override
    public void run() {
        // Converter
        ImagePlusMatConverter ic = new ImagePlusMatConverter();
        MatImagePlusConverter mip = new MatImagePlusConverter();

        opencv_core.Mat m = ic.convert(imp, Mat.class);
        opencv_imgproc.cvtColor(m, m, opencv_imgproc.COLOR_BGR2GRAY);
        opencv_core.Mat res = new opencv_core.Mat();

        if (!showDialog()) {
            return;
        }

        int adaptiveMethod;
        if (method == "Mean") {
            adaptiveMethod = ADAPTIVE_THRESH_MEAN_C;
        } else {
            adaptiveMethod = ADAPTIVE_THRESH_GAUSSIAN_C;
        }

        int thresType;
        if (thresholdmethod == "Binary") {
            thresType = THRESH_BINARY;
        } else {
            thresType = THRESH_BINARY_INV;
        }

        adaptiveThreshold(m, res, maxValue, adaptiveMethod, thresType, blockSize, 2);

        ImagePlus imp2 = mip.convert(res, ImagePlus.class);
        imp2.show();

    }

}
