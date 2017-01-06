package ijopencv.opencv;

import ij.gui.Roi;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Rectd;
import org.scijava.Prioritized;
import org.scijava.Priority;
import org.scijava.convert.AbstractConverter;
import org.scijava.convert.Converter;
import org.scijava.log.LogService;
import org.scijava.plugin.Plugin;

@Plugin(type = Converter.class, priority = Priority.LOW_PRIORITY)
public class RectdRoiConverter extends AbstractConverter< Rectd, Roi> {

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
      opencv_core.Rectd rect = (opencv_core.Rectd)o; 
        Roi r = new Roi(rect.x(), rect.y(), rect.width(), rect.height());
        return (T)r;
    }

    @Override
    public Class< Roi> getOutputType() {
        return Roi.class;
    }

    @Override
    public Class< Rectd> getInputType() {
        return Rectd.class;
    }
}
