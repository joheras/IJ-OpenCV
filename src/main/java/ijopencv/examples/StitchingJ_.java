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
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import ijopencv.ij.ImagePlusMatVectorConverter;
import ijopencv.opencv.MatImagePlusConverter;
import org.bytedeco.opencv.opencv_core.MatVector;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_stitching.Stitcher;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author jonathan
 */
@Plugin(type = Command.class, headless = true, menuPath = "Plugins>IJ-OpenCV-plugins>Stitching")
public class StitchingJ_ implements Command {


    @Parameter
    private ImagePlus imp;

    @Override
    public void run() {
        
        // Converters
        ImagePlusMatVectorConverter isc = new ImagePlusMatVectorConverter();
        MatImagePlusConverter ic = new MatImagePlusConverter();
        
        MatVector m = isc.convert(imp,MatVector.class);

        Stitcher stitcher = Stitcher.create(Stitcher.PANORAMA);

        Mat res = new Mat();
        stitcher.stitch(m, res);

        ImagePlus resIJ = ic.convert(res,ImagePlus.class);
        resIJ.show();
    }

}
