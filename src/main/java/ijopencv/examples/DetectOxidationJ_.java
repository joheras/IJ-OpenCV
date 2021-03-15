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


import ij.ImagePlus;
import ij.gui.OvalRoi;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import ijopencv.ij.ImagePlusMatConverter;
import ijopencv.opencv.Circle2fOvalRoiConverter;
import ijopencv.utils.Circle2fCV;
import org.bytedeco.javacpp.FloatPointer;

import org.bytedeco.javacpp.opencv_core;
import static org.bytedeco.javacpp.opencv_core.split;
import org.bytedeco.javacpp.opencv_imgproc;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

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
        opencv_core.Mat newImage = ic.convert(imp,opencv_core.Mat.class);
        
        
        opencv_core.Mat hsv = new opencv_core.Mat();
        opencv_imgproc.cvtColor(newImage,hsv,opencv_imgproc.COLOR_BGR2HSV);
        
        opencv_core.MatVector mv = new opencv_core.MatVector(3);
        split(hsv, mv);
        
        opencv_core.Mat thres = new opencv_core.Mat();
        opencv_imgproc.threshold(mv.get(1), thres, 0, 255, opencv_imgproc.CV_THRESH_BINARY_INV + opencv_imgproc.CV_THRESH_OTSU);
        
        opencv_core.MatVector contours = new opencv_core.MatVector();
        opencv_imgproc.findContours(thres,contours,opencv_imgproc.RETR_TREE,opencv_imgproc.CHAIN_APPROX_SIMPLE);
        
        opencv_core.Point2f c = new opencv_core.Point2f();
        FloatPointer r  = new FloatPointer();
        
        
        opencv_imgproc.minEnclosingCircle(contours.get(1),c,r);  
        
        // Circle converter
        Circle2fOvalRoiConverter cc =new Circle2fOvalRoiConverter();
        
        OvalRoi or = cc.convert(new Circle2fCV(c,r.get(0)),OvalRoi.class);
        
        return or;
    
    
    
    }
    
}
