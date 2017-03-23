package ijopencv.examples;


import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import ijopencv.ij.ImagePlusMatConverter;
import ijopencv.opencv.MatImagePlusConverter;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_photo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author jonathan
 */
public class Non_Local_Means_DenoisingJ_ implements PlugInFilter {

    ImagePlus imp;

    @Override
    public int setup(String arg, ImagePlus imp) {
        this.imp=imp;
        return DOES_ALL + NO_CHANGES;
    }

    @Override
    public void run(ImageProcessor ip) {
        // Converter
        ImagePlusMatConverter ic = new ImagePlusMatConverter();
        MatImagePlusConverter mip = new MatImagePlusConverter();

        opencv_core.Mat m = ic.convert(imp,opencv_core.Mat.class);

        opencv_photo.fastNlMeansDenoisingColored(m, m, 10, 10, 7, 21);

        ImagePlus resIJ = mip.convert(m,ImagePlus.class);
        resIJ.show();
    }

}
