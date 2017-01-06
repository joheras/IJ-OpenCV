package ijopencv.ij;

import ij.gui.PointRoi;
import ij.gui.Roi;
import java.lang.reflect.Type;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.KeyPoint;
import org.scijava.Prioritized;
import org.scijava.Priority;
import org.scijava.convert.AbstractConverter;
import org.scijava.convert.Converter;
import org.scijava.log.LogService;
import org.scijava.plugin.Plugin;

@Plugin(type = Converter.class, priority = Priority.LOW_PRIORITY)
public class PointRoiKeyPointConverter extends AbstractConverter< PointRoi, KeyPoint> {

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
        PointRoi pr = (PointRoi)o;
        opencv_core.KeyPoint p = new KeyPoint(pr.getXCoordinates()[0], pr.getYCoordinates()[0], 1);
        return(T) p;
    
    }

    @Override
    public Class< KeyPoint> getOutputType() {
        return KeyPoint.class;
    }

    @Override
    public Class< PointRoi> getInputType() {
        return PointRoi.class;
    }

    @Override
    public boolean canConvert(Object src, Type dest) {
        PointRoi pr =(PointRoi)src;
        if (pr.getType() != Roi.POINT) {
            return false;
        }
        return true;
    }
    
    
}
