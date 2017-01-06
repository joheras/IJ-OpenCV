package ijopencv.ij;

import ij.gui.OvalRoi;
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
public class OvalRoiRotatedRectConverter extends AbstractConverter< OvalRoi, RotatedRect> {

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
        OvalRoi or =(OvalRoi)o;
        Rectangle rect = or.getPolygon().getBounds();

        int[] xpoints = {rect.x, rect.x, rect.x + rect.width, rect.x + rect.width};
        int[] ypoints = {rect.y, rect.y + rect.height, rect.y + rect.height, rect.y};
        opencv_core.Mat x = new opencv_core.Mat(xpoints);
        opencv_core.Mat y = new opencv_core.Mat(ypoints);
        opencv_core.MatVector xy = new opencv_core.MatVector(2);
        opencv_core.Mat points = new opencv_core.Mat();
        merge(xy, points);
        return (T)minAreaRect(points);
    }

    @Override
    public Class< RotatedRect> getOutputType() {
        return RotatedRect.class;
    }

    @Override
    public Class< OvalRoi> getInputType() {
        return OvalRoi.class;
    }
}
