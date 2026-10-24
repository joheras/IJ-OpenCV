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


import ij.ImagePlus;
import ij.gui.OvalRoi;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import ijopencv.ij.ImagePlusMatConverter;
import ijopencv.opencv.Circle2fOvalRoiConverter;
import ijopencv.utils.Circle2fCV;
import org.bytedeco.javacpp.FloatPointer;

import static org.bytedeco.opencv.global.opencv_core.split;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.MatVector;
import org.bytedeco.opencv.opencv_core.Point2f;

/**
 *
 * @author jonathan
 */
@Plugin(type = Command.class, headless = true, menuPath = "Plugins>IJ-OpenCV-plugins>Detect oxidation")
public class DetectOxidationJ_ implements Command {

    @Parameter
    private ImagePlus imp;

    @Override
    public void run() {
        OvalRoi or = detectROI(imp);
        imp.setRoi(or);
        
        
    }
    
    public OvalRoi detectROI(ImagePlus imp){
        // Converter 
         ImagePlusMatConverter ic = new ImagePlusMatConverter();
        Mat newImage = ic.convert(imp,Mat.class);
        
        
        Mat hsv = new Mat();
        opencv_imgproc.cvtColor(newImage,hsv,opencv_imgproc.COLOR_BGR2HSV);
        
        MatVector mv = new MatVector(3);
        split(hsv, mv);
        
        Mat thres = new Mat();
        opencv_imgproc.threshold(mv.get(1), thres, 0, 255, opencv_imgproc.CV_THRESH_BINARY_INV + opencv_imgproc.CV_THRESH_OTSU);
        
        MatVector contours = new MatVector();
        opencv_imgproc.findContours(thres,contours,opencv_imgproc.RETR_TREE,opencv_imgproc.CHAIN_APPROX_SIMPLE);
        
        Point2f c = new Point2f();
        FloatPointer r  = new FloatPointer();
        
        
        opencv_imgproc.minEnclosingCircle(contours.get(1),c,r);  
        
        // Circle converter
        Circle2fOvalRoiConverter cc =new Circle2fOvalRoiConverter();
        
        OvalRoi or = cc.convert(new Circle2fCV(c,r.get(0)),OvalRoi.class);
        
        return or;
    
    
    
    }
    
}
