package ijopencv.ij;

import ij.ImagePlus;
import java.awt.Frame;
import java.awt.image.BufferedImage;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;
import static org.bytedeco.javacpp.opencv_core.merge;
import static org.bytedeco.javacpp.opencv_core.split;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.scijava.Prioritized;
import org.scijava.Priority;
import org.scijava.convert.AbstractConverter;
import org.scijava.convert.Converter;
import org.scijava.log.LogService;
import org.scijava.plugin.Plugin;

@Plugin(type = Converter.class, priority = Priority.LOW_PRIORITY)
public class ImagePlusMatConverter extends AbstractConverter< ImagePlus, Mat> {

    @Override
    public int compareTo(Prioritized o) {
        return super.compareTo(o);
    }

    @Override
    public LogService log() {
        return super.log();
    }

    @Override
    public String getIdentifier() {
        return super.getIdentifier();
    }

    @Override
    public < T> T convert(Object o, Class< T> type) {
        ImagePlus imp = (ImagePlus) o;
        // Converter ImageJ image to Frame
        Java2DFrameConverter converterToFrame = new Java2DFrameConverter();
        // To covert an ImageJ image to Frame, we must obtain the BufferedImage
        BufferedImage bi = imp.getBufferedImage();
        // Actual conversion
        org.bytedeco.javacv.Frame frame = converterToFrame.convert(bi);

        // Convert Frame to OpenCV Mat
        OpenCVFrameConverter.ToMat converterToMat = new OpenCVFrameConverter.ToMat();
        opencv_core.Mat img = converterToMat.convert(frame);
        // OpenCV expects a BGR image, but it is actually an RGB image, so we 
        // must split the channels and merge them in the correct order

        /* We should check if the image is in rgb or in grayscale, but that 
         remains as further work 
         */
        if (imp.getType() == ImagePlus.COLOR_RGB) { // RGB image

            opencv_core.MatVector rgb = new opencv_core.MatVector(3);
            split(img, rgb);
            opencv_core.Mat img2 = new opencv_core.Mat();

            opencv_core.Mat[] bgrarray = {rgb.get(3), rgb.get(2), rgb.get(1)};
            opencv_core.MatVector bgr = new opencv_core.MatVector(bgrarray);

            merge(bgr, img2);

            return (T) img2;
        } else {
            return (T) img;
        }

    }

    @Override
    public Class< Mat> getOutputType() {
        return Mat.class;
    }

    @Override
    public Class< ImagePlus> getInputType() {
        return ImagePlus.class;
    }
}
