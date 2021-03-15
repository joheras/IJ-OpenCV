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
import ij.IJ;
import ij.ImagePlus;
import ij.gui.Roi;
import ij.plugin.frame.RoiManager;
import ijopencv.ij.ImagePlusMatConverter;
import ijopencv.opencv.RectRoiConverter;
import java.nio.file.Paths;
import org.bytedeco.javacpp.opencv_core;
import static org.bytedeco.javacpp.opencv_imgproc.CV_BGR2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvtColor;
import org.bytedeco.javacpp.opencv_objdetect;
import static org.bytedeco.javacpp.opencv_objdetect.CASCADE_SCALE_IMAGE;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 *
 * @author jonathan
 */
@Plugin(type = Command.class, headless = true, menuPath = "Plugins>IJ-OpenCV-plugins>Face detection")
public class FaceDetectionJ_ implements Command {

    @Parameter
    private ImagePlus imp;

    @Override
    public void run() {
        //Converters
        RectRoiConverter rc = new RectRoiConverter();
        opencv_core.Mat img2 = ImagePlusMatConverter.toMat(imp, 8); // also does RGB to Gray automatically

        // Detect the faces and store them as an array of rectangles
        opencv_core.RectVector rv = detectFaces(img2);

        // Add rectangles to ROI Manager
        RoiManager rm = new RoiManager();
        rm.setVisible(true);
        for (int i = 0; i < rv.size(); i++) {
            Roi r = rc.convert(rv.get(i), Roi.class);
            rm.add(imp, r, 0);
        }
        
        //Show all ROI
        rm.runCommand("Show All");

    }

    public opencv_core.RectVector detectFaces(opencv_core.Mat image) {
        
        // Open xml classifier located in Fiji.app/lib (provided by IJ-OpenCV update site)
        opencv_objdetect.CascadeClassifier faceclassifier = new opencv_objdetect.CascadeClassifier(Paths.get(IJ.getDirectory("imagej"),"lib","haarcascade_frontalface_alt.xml").toString());

        opencv_core.RectVector rv = new opencv_core.RectVector();

        faceclassifier.detectMultiScale(image, rv, 1.1, 2, CASCADE_SCALE_IMAGE, new opencv_core.Size(30, 30), new opencv_core.Size(500, 500));
        
        faceclassifier.close(); //prevent possible memory leak
        
        return rv;

    }

}
