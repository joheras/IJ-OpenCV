package ijopencv.opencv;

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
public class Point2fVectorPointRoiConverter extends AbstractConverter< Point2fVector, PointRoi> {

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
    opencv_core.Point2fVector pv = (opencv_core.Point2fVector)o;
        float[] xpoints = new float[(int) pv.size()];
        float[] ypoints = new float[(int) pv.size()];

        for (int i = 0; i < (int) pv.size(); i++) {
            xpoints[i] = (float) pv.get(i).x();
            ypoints[i] = (float) pv.get(i).y();
        }
        PointRoi pr = new PointRoi(xpoints, ypoints);
        return(T) pr;
    
    }

    @Override
    public Class< PointRoi> getOutputType() {
        return PointRoi.class;
    }

    @Override
    public Class< Point2fVector> getInputType() {
        return Point2fVector.class;
    }
}
