package ijopencv.examples;


import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
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
public class StitchingJ_ implements PlugInFilter {

    ImagePlus imp;

    @Override
    public int setup(String arg, ImagePlus imp) {
        this.imp=imp;
        return DOES_ALL + NO_CHANGES;
    }

    @Override
    public void run(ImageProcessor ip) {
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
