package ijopencv.opencv;

import ij.gui.PointRoi;
import java.lang.reflect.Type;
import org.bytedeco.javacpp.opencv_core.KeyPoint;
import org.scijava.Prioritized;
import org.scijava.Priority;
import org.scijava.convert.AbstractConverter;
import org.scijava.convert.Converter;
import org.scijava.log.LogService;
import org.scijava.plugin.Plugin;

@Plugin(type = Converter.class, priority = Priority.LOW_PRIORITY)
public class KeyPointPointRoiConverter extends AbstractConverter< KeyPoint, PointRoi> {

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
        KeyPoint p = (KeyPoint)o;
        PointRoi pr = new PointRoi(p.pt().x(), p.pt().y());
        return (T)pr;
    }

    @Override
    public Class< PointRoi> getOutputType() {
        return PointRoi.class;
    }

    @Override
    public Class< KeyPoint> getInputType() {
        return KeyPoint.class;
    }

    @Override
    public boolean canConvert(Object src, Type dest) {
                if(!(src instanceof KeyPoint)){
            return false;
        }
        KeyPoint p = (KeyPoint)src;
        if (p.sizeof() != 2) {
            return false;
        }
        return true; //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
