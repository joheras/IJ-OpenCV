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
import ij.gui.Roi;
import ij.plugin.frame.RoiManager;
import ijopencv.ij.ImagePlusMatConverter;
import ijopencv.opencv.RectRoiConverter;
import java.nio.file.Paths;
import static org.bytedeco.opencv.global.opencv_imgproc.CV_BGR2GRAY;
import static org.bytedeco.opencv.global.opencv_imgproc.cvtColor;
import static org.bytedeco.opencv.global.opencv_objdetect.CASCADE_SCALE_IMAGE;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.RectVector;
import org.bytedeco.opencv.opencv_core.Size;

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
        Mat img2 = ImagePlusMatConverter.toMat(imp, 8); // also does RGB to Gray automatically

        // Detect the faces and store them as an array of rectangles
        RectVector rv = detectFaces(img2);

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

    public RectVector detectFaces(Mat image) {
        
        // Open xml classifier located in Fiji.app/lib (provided by IJ-OpenCV update site)
        CascadeClassifier faceclassifier = new CascadeClassifier(Paths.get(IJ.getDirectory("imagej"),"lib","haarcascade_frontalface_alt.xml").toString());

        RectVector rv = new RectVector();

        faceclassifier.detectMultiScale(image, rv, 1.1, 2, CASCADE_SCALE_IMAGE, new Size(30, 30), new Size(500, 500));
        
        faceclassifier.close(); //prevent possible memory leak
        
        return rv;

    }

}
