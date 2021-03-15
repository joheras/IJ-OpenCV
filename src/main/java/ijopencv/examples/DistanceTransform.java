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
/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */

package ijopencv.examples;


// import net.imglib2.type.numeric.RealType;

import ij.gui.GenericDialog;

import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import ij.IJ;
import ij.ImagePlus;
import ijopencv.ij.ImagePlusMatConverter;
import ijopencv.opencv.MatImagePlusConverter;



import static org.bytedeco.javacpp.opencv_imgproc.distanceTransform;
import org.bytedeco.javacpp.opencv_imgproc;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.scijava.widget.ChoiceWidget;



/**
 * This example illustrates how to create an ImageJ {@link Command} plugin.
 * <p>
 * The code here is the DistanceTransform using ImageJ Ops.
 * </p>
 * <p>
 * You should replace the parameter fields with your own inputs and outputs,
 * and replace the {@link run} method implementation with your own logic.
 * </p>
 */
@Plugin(type = Command.class, menuPath = "Plugins>IJ-OpenCV-plugins>DistanceTransform")
public class DistanceTransform implements Command {
    
    @Parameter(label= "Measure_Method: ", style= ChoiceWidget.LIST_BOX_STYLE, choices= {"4Move", "8Move", "Euclidian"})
    String measure_method = "Euclidian"; // select the measurement method from openCV 
    @Parameter(label= "Speed: ", style= ChoiceWidget.LIST_BOX_STYLE, choices= {"fast", "precise"})
    String speed = "precise"; // select the correctness / speed for openCV
    @Parameter(label = "Threshold: ")
    int threshold = 0; // select the threshold for 8bit gray, 0 means no threshold
   
    public DistanceTransform() {
		// Auto-generated constructor stub
	}
    
    
    
    
	//
    // Feel free to add more parameters here...
    //
	@Parameter
    private ImagePlus imp;

    @Override
    public void run() {
    	//Converter classes
    	ImagePlusMatConverter ic = new ImagePlusMatConverter();
		MatImagePlusConverter mip = new MatImagePlusConverter();
//        if (!showDialog()) {
//            return;
//        }
        // transform strings from dialog into openCV int variable
        int moveMethod;
        if (measure_method == "4Move") {
            moveMethod = opencv_imgproc.DIST_C;
        }else if (measure_method == "8Move") {
            moveMethod = opencv_imgproc.DIST_L1;
        } else {
            moveMethod = opencv_imgproc.DIST_L2;
        }
        
        int moveDiagonal;
        if (speed == "fast") {
            moveDiagonal = 3;
        } else {
            moveDiagonal = 5;
        }
        
		opencv_core.Mat src = ic.convert(imp, Mat.class); //imageplus into Mat
		opencv_core.Mat res = new opencv_core.Mat(); // empty Mat
		// threshold if needed (!=0)
		if (threshold !=0) {
			opencv_imgproc.threshold(src, src, threshold, 255, opencv_imgproc.THRESH_BINARY | opencv_imgproc.THRESH_OTSU);
		}
        //distanceTransform itself
        distanceTransform(src, res, moveMethod, moveDiagonal);
        
        //back transform into imp2
        ImagePlus imp2 = mip.convert(res, ImagePlus.class);
        //get the initial title resultImage and add the transformation steps
        imp2.setTitle( stripExtension(imp.getTitle())+"_"+measure_method+"_"+speed+"_EDT");
        //imp2.setLut(Opener.openLut("fire.lut"));
        IJ.run(imp2, "Fire",""); //LUT for 32Bit Float set from gray to fire
        imp2.show();
    }
    // Modified from ImageJ code by Wayne Rasband and copied from LocalThickness
 	public String stripExtension(String name) {
 		if (name != null) {
 			final int dotIndex = name.lastIndexOf(".");
 			if (dotIndex >= 0) name = name.substring(0, dotIndex);
 		}
 		return name;
 	}
   
 	public ImagePlus ij_opencv_disttransform(ImagePlus pic) {
 		ImagePlusMatConverter ic = new ImagePlusMatConverter();
		MatImagePlusConverter mip = new MatImagePlusConverter();
		opencv_core.Mat src = ic.convert(pic, Mat.class); //imageplus into Mat
		opencv_core.Mat res = new opencv_core.Mat(); // empty Mat
        //distanceTransform itself with default settings to fit results from LocalThick
        distanceTransform(src, res, opencv_imgproc.DIST_L2, 5);
        ImagePlus imp2 = mip.convert(res, ImagePlus.class);  //back transform into imp2
        //get the initial title resultImage and add the transformation steps
        imp2.setTitle(stripExtension(pic.getTitle())+"_EDT"); 
        return imp2;
 	}
 
}


