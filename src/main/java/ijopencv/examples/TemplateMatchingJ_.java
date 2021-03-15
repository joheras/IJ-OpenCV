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
import ij.gui.Roi;
import ij.plugin.filter.PlugInFilter;
import ij.plugin.frame.RoiManager;
import ij.process.ImageProcessor;
import ijopencv.ij.ImagePlusMatConverter;
import ijopencv.opencv.RectRoiConverter;
import java.util.ArrayList;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.indexer.FloatBufferIndexer;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;
import static org.bytedeco.javacpp.opencv_core.NORM_MINMAX;
import static org.bytedeco.javacpp.opencv_core.minMaxLoc;
import static org.bytedeco.javacpp.opencv_core.normalize;
import org.bytedeco.javacpp.opencv_imgproc;
import static org.bytedeco.javacpp.opencv_imgproc.TM_CCOEFF_NORMED;
import static org.bytedeco.javacpp.opencv_imgproc.matchTemplate;
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
@Plugin(type = Command.class, headless = true, menuPath = "Plugins>IJ-OpenCV-plugins>Template matching")
public class TemplateMatchingJ_ implements Command {


    @Parameter
    private ImagePlus imp;

    @Override
    public void run() {
        ImagePlus original = (ImagePlus) imp.clone();

        Roi r = imp.getRoi();

        if (r == null) {
            IJ.error("Select a rectangle first");
            return;
        }

        if (r.isArea() && (r.getType() == Roi.RECTANGLE)) {

            // Crop the selection to be the template
            IJ.run(imp, "Crop", "");

            //Converters
            ImagePlusMatConverter ic = new ImagePlusMatConverter();
            RectRoiConverter rc = new RectRoiConverter();

            // Convert the ImageJ images to OpenCV images
            opencv_core.Mat matching = ic.convert(original, Mat.class);
            opencv_core.Mat template = ic.convert(imp, Mat.class);

            opencv_core.Mat gray = new opencv_core.Mat();
            opencv_imgproc.cvtColor(matching, gray, opencv_imgproc.COLOR_BGR2GRAY);
            opencv_imgproc.cvtColor(template, template, opencv_imgproc.COLOR_BGR2GRAY);

            opencv_core.Mat results = new opencv_core.Mat();

            // Matching and normalisation
            matchTemplate(gray, template, results, TM_CCOEFF_NORMED);
            normalize(results, results, 0, 1, NORM_MINMAX, -1, new opencv_core.Mat());

            DoublePointer minVal = new DoublePointer();
            DoublePointer maxVal= new DoublePointer();
            opencv_core.Point minLoc = new opencv_core.Point();
            opencv_core.Point maxLoc = new opencv_core.Point();
            opencv_core.Point matchLoc;

            minMaxLoc(results, minVal, maxVal, minLoc, maxLoc, new opencv_core.Mat());

            ArrayList<opencv_core.Point> locations = obtainLocations(results, 0.99, template.cols(), template.rows());
            RoiManager rm = new RoiManager();
            rm.setVisible(true);

            opencv_core.Rect solution;
            Roi solutionIJ;
            opencv_core.Point p;
            for (int i = 0; i < locations.size(); i++) {
                p = locations.get(i);
                solution = new opencv_core.Rect(p.x(), p.y(), template.cols(), template.rows());
                solutionIJ = rc.convert(solution, Roi.class);
                rm.add(original, solutionIJ, i);

            }

            imp.changes = false;
            imp.close();
            original.show();

        } else {
            IJ.error("Select a rectangle");
        }
    }

    ArrayList<opencv_core.Point> obtainLocations(opencv_core.Mat results, double threshold, int stepX, int stepY) {

        ArrayList<opencv_core.Point> points = new ArrayList<opencv_core.Point>();

        FloatBufferIndexer sI = results.createIndexer();

        for (int y = 0; y < results.rows(); y++) {
            int x = 0;
            while (x < results.cols()) {
                if (sI.get(y, x) > threshold) {
                    opencv_core.Point p = new opencv_core.Point(x, y);
                    points.add(p);
                    x = x + stepX;
                } else {
                    x++;
                }
            }
        }

        return points;

    }

}
