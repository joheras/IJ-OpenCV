package ijopencv.opencv;

import ij.gui.PointRoi;
import java.lang.reflect.Type;
import org.bytedeco.javacpp.opencv_core.Point2d;
import org.scijava.Prioritized;
import org.scijava.Priority;
import org.scijava.convert.AbstractConverter;
import org.scijava.convert.Converter;
import org.scijava.log.LogService;
import org.scijava.plugin.Plugin;

@Plugin(type = Converter.class, priority = Priority.LOW_PRIORITY)
public class Point2dPointRoiConverter extends AbstractConverter< Point2d, PointRoi> {

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
        Point2d p =(Point2d)o;
        PointRoi pr = new PointRoi(p.x(), p.y());
        return (T)pr;
    }

    @Override
    public Class< PointRoi> getOutputType() {
        return PointRoi.class;
    }

    @Override
    public Class< Point2d> getInputType() {
        return Point2d.class;
    }

    @Override
    public boolean canConvert(Object src, Type dest) {
        Point2d p =(Point2d)src;
        if (p.sizeof() != 2) {
            return false;
        }
        return true;
    }
    
    
    
}
