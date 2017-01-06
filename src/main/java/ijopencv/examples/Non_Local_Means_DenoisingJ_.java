package ijopencv.examples;


import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ijopencv.ij.ImagePlusMatConverter;
import ijopencv.opencv.MatImagePlusConverter;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_photo;
import org.bytedeco.javacpp.opencv_stitching;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author jonathan
 */
public class Non_Local_Means_DenoisingJ_ implements PlugIn {

    @Override
    public void run(String arg) {
        ImagePlus imp = IJ.getImage();
        // Converter
        ImagePlusMatConverter ic = new ImagePlusMatConverter();
        MatImagePlusConverter mip = new MatImagePlusConverter();

        opencv_core.Mat m = ic.convert(imp,opencv_core.Mat.class);

        opencv_photo.fastNlMeansDenoisingColored(m, m, 10, 10, 7, 21);

        ImagePlus resIJ = mip.convert(m,ImagePlus.class);
        resIJ.show();
    }

}
