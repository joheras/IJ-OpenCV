package ijopencv.opencv;

import ij.gui.PointRoi;
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
public class MatPointRoiConverter extends AbstractConverter< Mat, PointRoi> {

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
        int h = m.rows();
        int[] xpoints = new int[h];
        int[] ypoints = new int[h];

        for (int i = 0; i < h; i++) {
            xpoints[i] = xy.get(0).getIntBuffer().get(i);
            ypoints[i] = xy.get(1).getIntBuffer().get(i);
        }

        PointRoi pr = new PointRoi(xpoints, ypoints, h);
        return (T)pr;
    }

    @Override
    public Class< PointRoi> getOutputType() {
        return PointRoi.class;
    }

    @Override
    public Class< Mat> getInputType() {
        return Mat.class;
    }
}
