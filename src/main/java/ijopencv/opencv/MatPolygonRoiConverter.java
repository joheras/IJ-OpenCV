package ijopencv.opencv;

import ij.gui.PolygonRoi;
import ij.gui.Roi;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;
import static org.bytedeco.javacpp.opencv_core.split;
import org.scijava.Prioritized;
import org.scijava.Priority;
import org.scijava.convert.AbstractConverter;
import org.scijava.convert.Converter;
import org.scijava.log.LogService;
import org.scijava.plugin.Plugin;

@Plugin(type = Converter.class, priority = Priority.LOW_PRIORITY)
public class MatPolygonRoiConverter extends AbstractConverter< Mat, PolygonRoi> {

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
        Mat m =(Mat)o;
        opencv_core.MatVector xy = new opencv_core.MatVector(2);
        split(m, xy);
        int h = m.size().height();
        int[] xpoints = new int[h];
        int[] ypoints = new int[h];

        for (int i = 0; i < h; i++) {
            xpoints[i] = xy.get(0).getIntBuffer().get(i);
            ypoints[i] = xy.get(1).getIntBuffer().get(i);
        }

        PolygonRoi pr = new PolygonRoi(xpoints, ypoints, h, Roi.POLYGON);
        return (T)pr;
    
    }

    @Override
    public Class< PolygonRoi> getOutputType() {
        return PolygonRoi.class;
    }

    @Override
    public Class< Mat> getInputType() {
        return Mat.class;
    }
}
