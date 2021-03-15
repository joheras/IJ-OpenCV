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

import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import ij.text.TextWindow;
import ijopencv.ij.ImagePlusMatVectorConverter;

import java.util.ArrayList;
import org.bytedeco.javacpp.FloatPointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.javacpp.opencv_core;
import static org.bytedeco.javacpp.opencv_core.CV_32F;
import static org.bytedeco.javacpp.opencv_core.KMEANS_PP_CENTERS;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
import static org.bytedeco.javacpp.opencv_core.kmeans;
import static org.bytedeco.javacpp.opencv_imgproc.calcHist;
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
@Plugin(type = Command.class, headless = true, menuPath = "Plugins>IJ-OpenCV-plugins>Kmeans clustering")
public class KMeans_ClusteringJ implements Command {

    int nclusters = 2;


    @Parameter
    private ImagePlus imp;

    @Override
    public void run() {
        int stacksize = imp.getStack().getSize();

        if (imp.getStack().getSize() == 1) {
            IJ.error("You need a stack of images");
            return;
        }

        // Converters
        ImagePlusMatVectorConverter isc = new ImagePlusMatVectorConverter();

        opencv_core.MatVector mvec = isc.convert(imp, MatVector.class);

        if (!showDialog()) {
            return;
        }

        // feature data
        FloatPointer featuresData = new FloatPointer((int) mvec.size() * 512);

        // Compute the histograms
        Mat mask = new Mat();
        IntPointer intPtrChannels = new IntPointer(0, 1, 2);
        IntPointer intPtrHistSize = new IntPointer(8, 8, 8);
        FloatPointer fltPtrRanges = new FloatPointer(0.0f, 255.0f, 0.0f, 255.0f, 0.0f, 255.0f);
        PointerPointer ptptranges = new PointerPointer(fltPtrRanges, fltPtrRanges, fltPtrRanges);

        Mat hist1 = new Mat();
        int n = 0;
        for (int i = 0; i < mvec.size(); i++) {
            calcHist(mvec.get(i), 1, intPtrChannels, mask, hist1, 3, intPtrHistSize, ptptranges, true, false);
            opencv_core.normalize(hist1, hist1);
            for (int j = 0; j < 512; j++) {
                featuresData.put(n, hist1.getFloatBuffer().get(j));
                n++;
            }

        }

        Mat data = new Mat((int) mvec.size(), 512, CV_32F, featuresData);

        Mat labels = new Mat();
        Mat centers = new Mat();

        opencv_core.TermCriteria tc = new opencv_core.TermCriteria(opencv_core.TermCriteria.EPS + opencv_core.TermCriteria.COUNT, 10, 1.0);

        kmeans(data, nclusters, labels, tc, 1, KMEANS_PP_CENTERS);

        String headings = "Image\t Cluster";
        ArrayList list = new ArrayList();

        String row = "";

        for (int i = 0; i < labels.rows(); i++) {
            row = imp.getStack().getSliceLabel(i + 1) + "\t" + labels.getIntBuffer().get(i);
            list.add(row);

        }

        TextWindow textWindow = new TextWindow("Clustering Table", headings, list, 600, 400);
        textWindow.setVisible(true);

    }

    private boolean showDialog() {

        GenericDialog gd = new GenericDialog("Number of clusters");
        gd.addNumericField("N clusters", nclusters, 0);
        //gd.addNumericField("Gaussian Kernel width:", gaussianKernelWidth, 0);

        gd.showDialog();
        if (gd.wasCanceled()) {
            return false;
        }

        nclusters = (int) gd.getNextNumber();
        if (nclusters < 2) {
            nclusters = 2;
        }

        return true;
    }

}
