package ijopencv.examples;


import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ijopencv.ij.ImagePlusMatConverter;
import ijopencv.ij.ImagePlusMatVectorConverter;
import ijopencv.opencv.MatImagePlusConverter;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.MatVector;
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
public class StitchingJ_ implements PlugIn {

    @Override
    public void run(String arg) {
        ImagePlus imp = IJ.getImage();
        // Converters
        ImagePlusMatVectorConverter isc = new ImagePlusMatVectorConverter();
        MatImagePlusConverter ic = new MatImagePlusConverter();
        
        opencv_core.MatVector m = isc.convert(imp,MatVector.class);

        opencv_stitching.Stitcher stitcher = opencv_stitching.Stitcher.createDefault(true);

        opencv_core.Mat res = new opencv_core.Mat();
        stitcher.stitch(m, res);

        ImagePlus resIJ = ic.convert(res,ImagePlus.class);
        resIJ.show();
    }

}
