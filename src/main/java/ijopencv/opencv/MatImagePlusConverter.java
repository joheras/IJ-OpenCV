package ijopencv.opencv;

import ij.ImagePlus;
import java.awt.image.BufferedImage;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.scijava.Prioritized;
import org.scijava.Priority;
import org.scijava.convert.AbstractConverter;
import org.scijava.convert.Converter;
import org.scijava.log.LogService;
import org.scijava.plugin.Plugin;

@Plugin(type = Converter.class, priority = Priority.LOW_PRIORITY)
public class MatImagePlusConverter extends AbstractConverter< Mat, ImagePlus> {

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
        Mat image = (Mat) o;
// Converter to OpenCV Mat
        OpenCVFrameConverter.ToMat converterToMat = new OpenCVFrameConverter.ToMat();
        // Converter ImageJ image to Frame
        Java2DFrameConverter converterToFrame = new Java2DFrameConverter();

        Frame frame = converterToMat.convert(image);
        BufferedImage bf = converterToFrame.convert(frame);

        return (T) (new ImagePlus("image", bf));
    }

    @Override
    public Class< ImagePlus> getOutputType() {
        return ImagePlus.class;
    }

    @Override
    public Class< Mat> getInputType() {
        return Mat.class;
    }
}
