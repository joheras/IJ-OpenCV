package ijopencv.examples;


import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import ijopencv.ij.ImagePlusMatConverter;

import org.bytedeco.javacpp.opencv_core;
import static org.bytedeco.javacpp.opencv_core.multiply;
import static org.bytedeco.javacpp.opencv_highgui.imshow;
import static org.bytedeco.javacpp.opencv_highgui.waitKey;
import org.bytedeco.javacpp.opencv_photo;
import static org.bytedeco.javacpp.opencv_photo.createMergeMertens;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author jonathan
 */
public class High_Dynamic_Range_ImagingJ_ implements PlugInFilter {

    ImagePlus imp;

    @Override
    public int setup(String arg, ImagePlus imp) {
        this.imp=imp;
        return DOES_ALL + NO_CHANGES;
    }

    @Override
    public void run(ImageProcessor ip) {
        // Converters

        ImagePlusMatConverter isc = new ImagePlusMatConverter();

        opencv_core.MatVector mvec = isc.convert(imp,opencv_core.MatVector.class);
        opencv_core.Mat res = new opencv_core.Mat();
        opencv_photo.MergeMertens merge_mertens = createMergeMertens();
        merge_mertens.process(mvec, res);

        opencv_core.MatExpr me = multiply(255, res);

        imshow("res", me.a());
        waitKey(0);
    }

}
