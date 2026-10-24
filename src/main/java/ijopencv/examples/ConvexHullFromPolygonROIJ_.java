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
import ij.gui.PolygonRoi;
import ij.plugin.filter.PlugInFilter;
import ij.plugin.frame.RoiManager;
import ij.process.ImageProcessor;
import ijopencv.ij.PolygonRoiMatConverter;
import ijopencv.opencv.MatPointRoiConverter;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 *
 * @author jonathan
 */
@Plugin(type = Command.class, headless = true, menuPath = "Plugins>IJ-OpenCV-plugins>Convex Hull from Polygon ROI")
public class ConvexHullFromPolygonROIJ_ implements Command {

 

  @Parameter
    private ImagePlus imp;


    @Override
    public void run() {
        // Get the ROI and check that it is a polygon
        PolygonRoi r = (PolygonRoi) imp.getRoi();

        if (r == null) {
            IJ.error("Select first a polygon ROI");
            return;
        }

        // Converter
        PolygonRoiMatConverter pc = new PolygonRoiMatConverter();
        MatPointRoiConverter mpc = new MatPointRoiConverter();

        Mat m = pc.convert(r, Mat.class);
        Mat convexHull = new Mat();
        opencv_imgproc.convexHull(m, convexHull);

        PolygonRoi pr = mpc.convert(convexHull, PolygonRoi.class);

        RoiManager rm = new RoiManager();
        rm.add(imp, r, 0);
        rm.add(imp, pr, 1);

    }

}
