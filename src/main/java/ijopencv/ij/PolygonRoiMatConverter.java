package ijopencv.ij;

import ij.gui.PolygonRoi;
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
public class PolygonRoiMatConverter extends AbstractConverter< PolygonRoi, Mat> {

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
        PolygonRoi pr =(PolygonRoi)o;
        Mat mx = new Mat(pr.getPolygon().xpoints);
        Mat my = new Mat(pr.getPolygon().ypoints);
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
    public Class< PolygonRoi> getInputType() {
        return PolygonRoi.class;
    }
}
