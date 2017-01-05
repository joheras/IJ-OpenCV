package ijopencv.examples;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import ij.IJ;
import ij.ImagePlus;
import ij.gui.Roi;
import ij.plugin.PlugIn;
import ij.plugin.frame.RoiManager;
import ijopencv.ImageConverter;
import ijopencv.RoiConverter;
import org.bytedeco.javacpp.opencv_core;
import static org.bytedeco.javacpp.opencv_imgproc.CV_BGR2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvtColor;
import org.bytedeco.javacpp.opencv_objdetect;
import static org.bytedeco.javacpp.opencv_objdetect.CASCADE_SCALE_IMAGE;

/**
 *
 * @author jonathan
 */
public class FaceDetectionJ_ implements PlugIn {

    @Override
    public void run(String arg) {
        // Get the image
        ImagePlus imp = IJ.getImage();
        //Converters
        ImageConverter ic = new ImageConverter();
        RoiConverter rc = new RoiConverter();

        // Convert the ImageJ image to OpenCV image
        opencv_core.Mat img2 = ic.convertTo(imp);

        // Detect the faces and store them as an array of rectangles
        opencv_core.RectVector rv = detectFaces(img2);

        // Add rectangles to ROI Manager
        RoiManager rm = new RoiManager();
        rm.setVisible(true);
        for (int i = 0; i < rv.size(); i++) {
            Roi r = rc.convertFrom(rv.get(i));
            rm.add(imp, r, 0);
        }

    }

    public opencv_core.RectVector detectFaces(opencv_core.Mat image) {
        opencv_core.Mat img_gray = new opencv_core.Mat();
        cvtColor(image, img_gray, CV_BGR2GRAY);

        opencv_objdetect.CascadeClassifier faceclassifier = new opencv_objdetect.CascadeClassifier(IJ.getDirectory("plugins") + "haarcascade_frontalface_alt.xml");

        opencv_core.RectVector rv = new opencv_core.RectVector();

        faceclassifier.detectMultiScale(img_gray, rv, 1.1, 2, CASCADE_SCALE_IMAGE, new opencv_core.Size(30, 30), new opencv_core.Size(500, 500));

        return rv;

    }

}
