
import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ijopencv.ImageConverter;
import ijopencv.ImageStackConverter;
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
public class High_Dynamic_Range_ImagingJ_ implements PlugIn {

    @Override
    public void run(String arg) {
        ImagePlus imp = IJ.getImage();
        // Converters

        ImageStackConverter isc = new ImageStackConverter();
        ImageConverter ic = new ImageConverter();

        opencv_core.MatVector mvec = isc.convertTo(imp);
        opencv_core.Mat res = new opencv_core.Mat();
        opencv_photo.MergeMertens merge_mertens = createMergeMertens();
        merge_mertens.process(mvec, res);

        opencv_core.MatExpr me = multiply(255, res);

        imshow("res", me.a());
        waitKey(0);
    }

}
