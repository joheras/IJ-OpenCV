package ijopencv.opencv;

import ij.gui.PointRoi;
import java.lang.reflect.Type;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Point;
import org.scijava.Prioritized;
import org.scijava.Priority;
import org.scijava.convert.AbstractConverter;
import org.scijava.convert.Converter;
import org.scijava.log.LogService;
import org.scijava.plugin.Plugin;

@Plugin(type = Converter.class, priority = Priority.LOW_PRIORITY)
public class PointPointRoiConverter extends AbstractConverter< Point, PointRoi> {
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
        opencv_core.Point p =(opencv_core.Point)o;
        PointRoi pr = new PointRoi(p.x(), p.y());
        return (T)pr;
    }

    @Override
    public Class< PointRoi> getOutputType() {
        return PointRoi.class;
    }

    @Override
    public Class< opencv_core.Point> getInputType() {
        return opencv_core.Point.class;
    }

    @Override
    public boolean canConvert(Object src, Type dest) {
        opencv_core.Point p =(opencv_core.Point)src;
        if (p.sizeof() != 2) {
            return false;
        }
        return true;
    }
}
