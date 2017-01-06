package ijopencv.ij;

import ij.gui.Roi;
import java.awt.Rectangle;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Rectf;
import org.scijava.Prioritized;
import org.scijava.Priority;
import org.scijava.convert.AbstractConverter;
import org.scijava.convert.Converter;
import org.scijava.log.LogService;
import org.scijava.plugin.Plugin;

@Plugin(type = Converter.class, priority = Priority.LOW_PRIORITY)
public class RoiRectfConverter extends AbstractConverter< Roi, Rectf> {

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
    Roi r =(Roi)o; 
        Rectangle r1 = r.getBounds();
        opencv_core.Rectf rect = new opencv_core.Rectf(r1.x, r1.y, r1.width, r1.height);
        return (T)rect;
    }

    @Override
    public Class< Rectf> getOutputType() {
        return Rectf.class;
    }

    @Override
    public Class< Roi> getInputType() {
        return Roi.class;
    }
}
