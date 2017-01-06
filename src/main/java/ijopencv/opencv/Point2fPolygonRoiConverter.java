package ijopencv.opencv;

import ij.gui.PolygonRoi;
import ij.gui.Roi;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Point2f;
import org.scijava.Prioritized;
import org.scijava.Priority;
import org.scijava.convert.AbstractConverter;
import org.scijava.convert.Converter;
import org.scijava.log.LogService;
import org.scijava.plugin.Plugin;

@Plugin(type = Converter.class, priority = Priority.LOW_PRIORITY)
public class Point2fPolygonRoiConverter extends AbstractConverter< Point2f, PolygonRoi> {

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
    opencv_core.Point2f p = (opencv_core.Point2f) o;
        int[] xpoints = new int[p.sizeof() / 2];
        int[] ypoints = new int[p.sizeof() / 2];

        for (int i = 0; i < p.sizeof(); i = i + 2) {

            xpoints[i / 2] = (int) p.get(i);
            ypoints[i / 2] = (int) p.get(i + 1);
        }
        PolygonRoi pr = new PolygonRoi(xpoints, ypoints, p.sizeof() / 2, Roi.POLYGON);
        return (T)pr;
    }

    @Override
    public Class< PolygonRoi> getOutputType() {
        return PolygonRoi.class;
    }

    @Override
    public Class< Point2f> getInputType() {
        return Point2f.class;
    }
}
