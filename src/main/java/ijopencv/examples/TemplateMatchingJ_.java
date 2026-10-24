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
import org.bytedeco.opencv.opencv_core.Mat;
import static org.bytedeco.opencv.global.opencv_core.NORM_MINMAX;
import static org.bytedeco.opencv.global.opencv_core.minMaxLoc;
import static org.bytedeco.opencv.global.opencv_core.normalize;
import org.bytedeco.opencv.global.opencv_imgproc;
import static org.bytedeco.opencv.global.opencv_imgproc.TM_CCOEFF_NORMED;
import static org.bytedeco.opencv.global.opencv_imgproc.matchTemplate;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.bytedeco.opencv.opencv_core.Point;
import org.bytedeco.opencv.opencv_core.Rect;

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
            Mat matching = ic.convert(original, Mat.class);
            Mat template = ic.convert(imp, Mat.class);

            Mat gray = new Mat();
            opencv_imgproc.cvtColor(matching, gray, opencv_imgproc.COLOR_BGR2GRAY);
            opencv_imgproc.cvtColor(template, template, opencv_imgproc.COLOR_BGR2GRAY);

            Mat results = new Mat();

            // Matching and normalisation
            matchTemplate(gray, template, results, TM_CCOEFF_NORMED);
            normalize(results, results, 0, 1, NORM_MINMAX, -1, new Mat());

            DoublePointer minVal = new DoublePointer();
            DoublePointer maxVal= new DoublePointer();
            Point minLoc = new Point();
            Point maxLoc = new Point();
            Point matchLoc;

            minMaxLoc(results, minVal, maxVal, minLoc, maxLoc, new Mat());

            ArrayList<Point> locations = obtainLocations(results, 0.99, template.cols(), template.rows());
            RoiManager rm = new RoiManager();
            rm.setVisible(true);

            Rect solution;
            Roi solutionIJ;
            Point p;
            for (int i = 0; i < locations.size(); i++) {
                p = locations.get(i);
                solution = new Rect(p.x(), p.y(), template.cols(), template.rows());
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

    ArrayList<Point> obtainLocations(Mat results, double threshold, int stepX, int stepY) {

        ArrayList<Point> points = new ArrayList<Point>();

        FloatBufferIndexer sI = results.createIndexer();

        for (int y = 0; y < results.rows(); y++) {
            int x = 0;
            while (x < results.cols()) {
                if (sI.get(y, x) > threshold) {
                    Point p = new Point(x, y);
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
