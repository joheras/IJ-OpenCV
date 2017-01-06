package ijopencv.ij;

import ij.ImagePlus;
import ij.ImageStack;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.scijava.Prioritized;
import org.scijava.Priority;
import org.scijava.convert.AbstractConverter;
import org.scijava.convert.Converter;
import org.scijava.log.LogService;
import org.scijava.plugin.Plugin;

@Plugin(type = Converter.class, priority = Priority.LOW_PRIORITY)
public class ImagePlusMatVectorConverter extends AbstractConverter< ImagePlus, MatVector> {

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
        ImageStack is = imp.getImageStack();
        MatVector mv = new MatVector(is.getSize());
        ImagePlus imp1;
        ImagePlusMatConverter ic = new ImagePlusMatConverter();

        for (int i = 1; i <= is.getSize(); i++) {
            imp1 = new ImagePlus("" + i, is.getProcessor(i));
            mv.put(i - 1, ic.convert(imp1, Mat.class));
        }

        return (T) mv;

    }

    @Override
    public Class< MatVector> getOutputType() {
        return MatVector.class;
    }

    @Override
    public Class< ImagePlus> getInputType() {
        return ImagePlus.class;
    }
}
