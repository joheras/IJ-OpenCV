package ijopencv.examples;


import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ijopencv.ImageConverter;
import ijopencv.ImageStackConverter;
import org.bytedeco.javacpp.opencv_core;
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
        ImageStackConverter isc = new ImageStackConverter();
        ImageConverter ic = new ImageConverter();

        opencv_core.MatVector m = isc.convertTo(imp);

        opencv_stitching.Stitcher stitcher = opencv_stitching.Stitcher.createDefault(true);

        opencv_core.Mat res = new opencv_core.Mat();
        stitcher.stitch(m, res);

        ImagePlus resIJ = ic.convertFrom(res);
        resIJ.show();
    }

}
