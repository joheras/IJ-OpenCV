package ijopencv.opencv;

import ijopencv.ij.*;
import ij.gui.OvalRoi;
import ijopencv.utils.Circle2fCV;
import ijopencv.utils.CircleCV;
import java.awt.Rectangle;
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
public class CircleOvalRoiConverter extends AbstractConverter< CircleCV,OvalRoi> {

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
        CircleCV c = (CircleCV)o;
        OvalRoi or = new OvalRoi(c.getCenter().x() - c.getRadius(),
                c.getCenter().y() - c.getRadius(), c.getRadius() * 2, c.getRadius() * 2);
        return (T)or;
    }

    @Override
    public Class< CircleCV> getInputType() {
        return CircleCV.class;
    }

    @Override
    public Class< OvalRoi> getOutputType() {
        return OvalRoi.class;
    }
}
