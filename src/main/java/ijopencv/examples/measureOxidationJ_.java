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
import ij.process.ImageProcessor;
import ijopencv.ij.ImagePlusMatConverter;
import ijopencv.ij.OvalRoiCircleCVConverter;
import ijopencv.utils.CircleCV;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bytedeco.javacpp.FloatPointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.opencv.global.opencv_core;
import static org.bytedeco.opencv.global.opencv_core.CV_8UC1;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.bytedeco.opencv.opencv_core.Scalar;

/**
 *
 * @author jonathan
 */
@Plugin(type = Command.class, headless = true, menuPath = "Plugins>IJ-OpenCV-plugins>Measure oxidation")
public class measureOxidationJ_ implements Command {


    @Parameter
    private ImagePlus imp;

    @Override
    public void run() {
        try {
            
            OvalRoi or = (OvalRoi)imp.getRoi();
            double oxidation = computeOxidation(imp, or);
            IJ.error("Oxidation level", ""+oxidation);
            
            
        } catch (Exception ex) {
            Logger.getLogger(measureOxidationJ_.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       
        
        
    }
    
    
    public double computeOxidation(ImagePlus imp, OvalRoi or) throws Exception {

        ImagePlusMatConverter ic  = new ImagePlusMatConverter();
        OvalRoiCircleCVConverter cc = new OvalRoiCircleCVConverter();
        
        Mat newImage = ic.convert(imp,Mat.class);
        // Create mask
        Mat mask = new Mat(newImage.size(), CV_8UC1, Scalar.all(0));
        CircleCV c = cc.convert(or,CircleCV.class);

        opencv_imgproc.circle(mask, c.getCenter(), c.getRadius(), new Scalar(255, 255, 255, 0), -1, 8, 0);

        // Image in LUV colorspace
        Mat imageLUV = new Mat();
        opencv_imgproc.cvtColor(newImage, imageLUV, opencv_imgproc.COLOR_BGR2Luv);

        Mat hist = new Mat();
        Mat blackHist = new Mat();

        // Since C++ `calcHist` is using arrays of arrays we need wrap to do some wrapping
        // in `IntPointer` and `PointerPointer` objects.
        int[] channels = {0, 1, 2};
        int[] bins = {8, 8, 8};
        float[] ranges = {0.0f, 255.0f};
        IntPointer intPtrChannels = new IntPointer(0, 1, 2);
        IntPointer intPtrHistSize = new IntPointer(8, 8, 8);
        FloatPointer fltPtrRanges = new FloatPointer(0.0f, 255.0f, 0.0f, 255.0f, 0.0f, 255.0f);
        PointerPointer ptptranges = new PointerPointer(fltPtrRanges, fltPtrRanges, fltPtrRanges);

        opencv_imgproc.calcHist(imageLUV, 1, intPtrChannels, mask, hist, 3, intPtrHistSize, ptptranges, true, false);
        opencv_core.normalize(hist, hist);

        Mat newImage2 = new Mat(newImage.size(), CV_8UC1, Scalar.all(0));
        opencv_imgproc.cvtColor(newImage2, newImage2, opencv_imgproc.COLOR_GRAY2BGR);

        opencv_imgproc.calcHist(newImage2, 1, intPtrChannels, mask, blackHist, 3, intPtrHistSize, ptptranges, true, false);
        opencv_core.normalize(blackHist, blackHist);

        return opencv_imgproc.compareHist(hist, blackHist, opencv_imgproc.CV_COMP_CHISQR);

    }
    
    
}
