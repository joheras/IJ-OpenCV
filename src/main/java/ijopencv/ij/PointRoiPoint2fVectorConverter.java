package ijopencv.ij;

import ij.gui.PointRoi;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Point2fVector;
import org.scijava.Prioritized;
import org.scijava.Priority;
import org.scijava.convert.AbstractConverter;
import org.scijava.convert.Converter;
import org.scijava.log.LogService;
import org.scijava.plugin.Plugin;

@Plugin(type = Converter.class, priority = Priority.LOW_PRIORITY)
public class PointRoiPoint2fVectorConverter extends AbstractConverter< PointRoi, Point2fVector> {

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
        opencv_core.Point2fVector pv = new opencv_core.Point2fVector();
        int[] xpoints = pr.getPolygon().xpoints;
        int[] ypoints = pr.getPolygon().ypoints;

        for (int i = 0; i < xpoints.length; i++) {
            pv.put(i, new opencv_core.Point2f(xpoints[i], ypoints[i]));
        }
        return (T)pv;
    }

    @Override
    public Class< Point2fVector> getOutputType() {
        return Point2fVector.class;
    }

    @Override
    public Class< PointRoi> getInputType() {
        return PointRoi.class;
    }
}
