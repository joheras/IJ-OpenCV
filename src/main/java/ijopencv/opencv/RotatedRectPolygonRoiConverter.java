package ijopencv.opencv;

import ij.gui.PolygonRoi;
import ij.gui.Roi;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.RotatedRect;
import org.scijava.Prioritized;
import org.scijava.Priority;
import org.scijava.convert.AbstractConverter;
import org.scijava.convert.Converter;
import org.scijava.log.LogService;
import org.scijava.plugin.Plugin;

@Plugin(type = Converter.class, priority = Priority.LOW_PRIORITY)
public class RotatedRectPolygonRoiConverter extends AbstractConverter< RotatedRect, PolygonRoi> {

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
        RotatedRect rr = (opencv_core.RotatedRect) o;
        double _angle = rr.angle() * Math.PI / 180.0;
        double b = (double) Math.cos(_angle) * 0.5f;
        double a = (double) Math.sin(_angle) * 0.5f;

        opencv_core.Point pt0 = new opencv_core.Point(
                (int) (rr.center().x() - a * rr.size().height() - b * rr.size().width()),
                (int) (rr.center().y() + b * rr.size().height() - a * rr.size().width()));

        opencv_core.Point pt1 = new opencv_core.Point(
                (int) (rr.center().x() + a * rr.size().height() - b * rr.size().width()),
                (int) (rr.center().y() - b * rr.size().height() - a * rr.size().width()));

        opencv_core.Point pt2 = new opencv_core.Point((int) (2 * rr.center().x() - pt0.x()),
                (int) (2 * rr.center().y() - pt0.y()));

        opencv_core.Point pt3 = new opencv_core.Point((int) (2 * rr.center().x() - pt1.x()),
                (int) (2 * rr.center().y() - pt1.y()));

        int[] xpoints = new int[4];
        int[] ypoints = new int[4];

        xpoints[0] = pt0.x();
        ypoints[0] = pt0.y();
        xpoints[1] = pt1.x();
        ypoints[1] = pt1.y();
        xpoints[2] = pt2.x();
        ypoints[2] = pt2.y();
        xpoints[3] = pt3.x();
        ypoints[3] = pt3.y();

        PolygonRoi pr = new PolygonRoi(xpoints, ypoints, 4, Roi.POLYGON);
        return (T)pr;

    }

    @Override
    public Class< PolygonRoi> getOutputType() {
        return PolygonRoi.class;
    }

    @Override
    public Class< RotatedRect> getInputType() {
        return RotatedRect.class;
    }
}
