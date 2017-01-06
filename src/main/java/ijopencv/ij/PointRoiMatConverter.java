package ijopencv.ij;

import ij.gui.PointRoi;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;
import static org.bytedeco.javacpp.opencv_core.merge;
import org.scijava.Prioritized;
import org.scijava.Priority;
import org.scijava.convert.AbstractConverter;
import org.scijava.convert.Converter;
import org.scijava.log.LogService;
import org.scijava.plugin.Plugin;

@Plugin(type = Converter.class, priority = Priority.LOW_PRIORITY)
public class PointRoiMatConverter extends AbstractConverter< PointRoi, Mat> {

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
        PointRoi pr =(PointRoi)o; 
        int[] xpoints = pr.getPolygon().xpoints;
        int[] ypoints = pr.getPolygon().ypoints;
        Mat mx = new Mat(xpoints);
        Mat my = new Mat(ypoints);
        opencv_core.MatVector mxy = new opencv_core.MatVector(2);
        mxy.put(0, mx);
        mxy.put(1, my);
        Mat m = new Mat();
        merge(mxy, m);
        return (T)m;
    }

    @Override
    public Class< Mat> getOutputType() {
        return Mat.class;
    }

    @Override
    public Class< PointRoi> getInputType() {
        return PointRoi.class;
    }
}
