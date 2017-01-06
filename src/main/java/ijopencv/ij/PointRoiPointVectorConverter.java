package ijopencv.ij;

import ij.gui.PointRoi;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.PointVector;
import org.scijava.Prioritized;
import org.scijava.Priority;
import org.scijava.convert.AbstractConverter;
import org.scijava.convert.Converter;
import org.scijava.log.LogService;
import org.scijava.plugin.Plugin;

@Plugin(type = Converter.class, priority = Priority.LOW_PRIORITY)
public class PointRoiPointVectorConverter extends AbstractConverter< PointRoi, PointVector> {

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
        opencv_core.PointVector pv = new opencv_core.PointVector();
        int[] xpoints = pr.getPolygon().xpoints;
        int[] ypoints = pr.getPolygon().ypoints;

        for (int i = 0; i < xpoints.length; i++) {
            pv.put(i, new opencv_core.Point(xpoints[i], ypoints[i]));
        }
        return (T)pv;
    }

    @Override
    public Class< PointVector> getOutputType() {
        return PointVector.class;
    }

    @Override
    public Class< PointRoi> getInputType() {
        return PointRoi.class;
    }
}
