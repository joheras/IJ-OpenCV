package ijopencv.opencv;

import ij.ImagePlus;
import ij.ImageStack;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.scijava.Prioritized;
import org.scijava.Priority;
import org.scijava.convert.AbstractConverter;
import org.scijava.convert.Converter;
import org.scijava.log.LogService;
import org.scijava.plugin.Plugin;

@Plugin(type = Converter.class, priority = Priority.LOW_PRIORITY)
public class MatVectorImagePlusConverter extends AbstractConverter< MatVector, ImagePlus> {

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
        MatVector mv = (MatVector) o;
        ImageStack is = new ImageStack();
        ImagePlus temp;
        MatImagePlusConverter ic = new MatImagePlusConverter();

        for (int i = 0; i < mv.size(); i++) {
            temp = ic.convert(mv.get(i), ImagePlus.class);
            is.addSlice(temp.getProcessor());
        }

        ImagePlus imp = new ImagePlus("image", is);
        return (T) imp;
    }

    @Override
    public Class< ImagePlus> getOutputType() {
        return ImagePlus.class;
    }

    @Override
    public Class< MatVector> getInputType() {
        return MatVector.class;
    }
}
