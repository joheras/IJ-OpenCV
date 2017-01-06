package ijopencv.ij;

import ij.gui.OvalRoi;
import ijopencv.utils.Circle2fCV;
import java.awt.Rectangle;
import java.lang.reflect.Type;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.RotatedRect;
import static org.bytedeco.javacpp.opencv_core.merge;
import static org.bytedeco.javacpp.opencv_imgproc.minAreaRect;
import org.scijava.Prioritized;
import org.scijava.Priority;
import org.scijava.convert.AbstractConverter;
import org.scijava.convert.Converter;
import org.scijava.log.LogService;
import org.scijava.plugin.Plugin;

@Plugin(type = Converter.class, priority = Priority.LOW_PRIORITY)
public class OvalRoiCircle2fConverter extends AbstractConverter< OvalRoi, Circle2fCV> {

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
        OvalRoi or = (OvalRoi) o;
        Rectangle rect = or.getPolygon().getBounds();
        Circle2fCV c = new Circle2fCV(new opencv_core.Point2f(rect.x + rect.width / 2, rect.y + rect.height / 2), rect.width / 2);
        return (T) c;
    }

    @Override
    public Class< Circle2fCV> getOutputType() {
        return Circle2fCV.class;
    }

    @Override
    public Class< OvalRoi> getInputType() {
        return OvalRoi.class;
    }

    @Override
    public boolean canConvert(Object src, Type dest) {
        OvalRoi or = (OvalRoi)src;
        Rectangle rect = or.getPolygon().getBounds();
        if (rect.width != rect.height) {
            return false;
        }
        return true;
    }
    
    
}
