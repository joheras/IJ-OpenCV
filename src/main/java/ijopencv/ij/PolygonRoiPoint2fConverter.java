package ijopencv.ij;

import ij.gui.PolygonRoi;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Point2f;
import org.scijava.Prioritized;
import org.scijava.Priority;
import org.scijava.convert.AbstractConverter;
import org.scijava.convert.Converter;
import org.scijava.log.LogService;
import org.scijava.plugin.Plugin;

@Plugin(type = Converter.class, priority = Priority.LOW_PRIORITY)
public class PolygonRoiPoint2fConverter extends AbstractConverter< PolygonRoi, Point2f> {

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
  PolygonRoi pr = (PolygonRoi)o;
             opencv_core.Point2f res = new opencv_core.Point2f();
        int[] xpoints = pr.getPolygon().xpoints;
        int[] ypoints = pr.getPolygon().ypoints;
        for (int i = 0; i < xpoints.length; i++) {
            opencv_core.Point2f p = new opencv_core.Point2f(xpoints[i], ypoints[i]);
            res.put(p);
        }
        return (T)res;
    }

    @Override
    public Class< Point2f> getOutputType() {
        return Point2f.class;
    }

    @Override
    public Class< PolygonRoi> getInputType() {
        return PolygonRoi.class;
    }
}
