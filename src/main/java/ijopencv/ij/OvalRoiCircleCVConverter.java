package ijopencv.ij;

import ij.gui.OvalRoi;
import ijopencv.utils.Circle2fCV;
import ijopencv.utils.CircleCV;
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
public class OvalRoiCircleCVConverter extends AbstractConverter< OvalRoi, CircleCV> {

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
        CircleCV c = new CircleCV(new opencv_core.Point(rect.x + rect.width / 2, rect.y + rect.height / 2), rect.width / 2);
        return (T) c;
    }

    @Override
    public Class< CircleCV> getOutputType() {
        return CircleCV.class;
    }

    @Override
    public Class< OvalRoi> getInputType() {
        return OvalRoi.class;
    }
    
     @Override
    public boolean canConvert(Object src, Type dest) {
        if(!(src instanceof OvalRoi)){
            return false;
        }
        OvalRoi or = (OvalRoi)src;
        Rectangle rect = or.getPolygon().getBounds();
        if (rect.width != rect.height) {
            return false;
        }
        return true;
    }
}
