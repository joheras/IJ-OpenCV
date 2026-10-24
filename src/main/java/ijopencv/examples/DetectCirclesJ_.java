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
import ij.IJ;
import ij.ImagePlus;
import ij.gui.OvalRoi;
import ij.plugin.filter.PlugInFilter;
import ij.plugin.frame.RoiManager;
import ij.process.ImageProcessor;
import ijopencv.ij.ImagePlusMatConverter;
import ijopencv.opencv.MatListOvalRoiConverter;
import java.util.ArrayList;
import net.imagej.ImageJ;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.javacpp.indexer.FloatIndexer;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Point3f;
import org.bytedeco.opencv.opencv_imgproc.Vec3fVector;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 *
 * @author jonathan
 */
@Plugin(type = Command.class, headless = true, menuPath = "Plugins>IJ-OpenCV-plugins>Detect circles")
public class DetectCirclesJ_ implements Command {

    @Parameter
    private ImagePlus imp;

    @Override
    public void run() {

        // Converters
        ImagePlusMatConverter ic = new ImagePlusMatConverter();
        Mat m = ic.convert(imp, Mat.class);
        MatListOvalRoiConverter cc = new MatListOvalRoiConverter();

        Mat gray = new Mat();
        opencv_imgproc.cvtColor(m, gray, opencv_imgproc.COLOR_BGR2GRAY);

        Vec3fVector circlesVec = new Vec3fVector();
        opencv_imgproc.HoughCircles(gray, circlesVec, opencv_imgproc.HOUGH_GRADIENT, 1.2, 100, 100, 100, 0, 0);

        // Copy into an Nx1 3-channel float Mat, as expected by the converter
        int n = (int) circlesVec.size();
        Mat circles = new Mat(n, 1, opencv_core.CV_32FC3);
        FloatIndexer idx = circles.createIndexer();
        for (int i = 0; i < n; i++) {
            Point3f c = circlesVec.get(i);
            idx.put(i, 0, 0, c.x());
            idx.put(i, 0, 1, c.y());
            idx.put(i, 0, 2, c.z());
        }
        idx.release();

        ArrayList<OvalRoi> or = new ArrayList<OvalRoi>();
        or = cc.convert(circles, or.getClass());

        RoiManager rm = new RoiManager();

        for (int i = 0; i < or.size(); i++) {
            rm.add(imp, or.get(i), i);
        }

    }

    public static void main(final String... args) throws Exception {
        // Launch ImageJ as usual.
        final ImageJ ij = new ImageJ();
        ij.launch(args);
    }

}
