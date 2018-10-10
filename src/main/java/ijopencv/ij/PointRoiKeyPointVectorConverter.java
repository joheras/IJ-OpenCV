package ijopencv.ij;

import ij.gui.PointRoi;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.KeyPointVector;
import org.scijava.Prioritized;
import org.scijava.Priority;
import org.scijava.convert.AbstractConverter;
import org.scijava.convert.Converter;
import org.scijava.log.LogService;
import org.scijava.plugin.Plugin;

@Plugin(type = Converter.class, priority = Priority.LOW_PRIORITY)
public class PointRoiKeyPointVectorConverter extends AbstractConverter< PointRoi, KeyPointVector> {

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
        PointRoi pr = (PointRoi) o;

        int[] xpoints = pr.getPolygon().xpoints;
        int[] ypoints = pr.getPolygon().ypoints;
        KeyPointVector pv = new KeyPointVector(xpoints.length);
        for (int i = 0; i < xpoints.length; i++) {
            pv.put(i, new opencv_core.KeyPoint(xpoints[i], ypoints[i], 1));
        }
        return (T) pv;
    }

    @Override
    public Class< KeyPointVector> getOutputType() {
        return KeyPointVector.class;
    }

    @Override
    public Class< PointRoi> getInputType() {
        return PointRoi.class;
    }
}
