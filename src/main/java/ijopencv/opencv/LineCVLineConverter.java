package ijopencv.opencv;

import ij.gui.Line;
import ijopencv.utils.LineCV;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import org.bytedeco.javacpp.opencv_core;
import static org.bytedeco.javacpp.opencv_core.cvRound;
import org.scijava.Prioritized;
import org.scijava.Priority;
import org.scijava.convert.AbstractConverter;
import org.scijava.convert.Converter;
import org.scijava.log.LogService;
import org.scijava.plugin.Plugin;

@Plugin(type = Converter.class, priority = Priority.LOW_PRIORITY)
public class LineCVLineConverter extends AbstractConverter< LineCV, Line> {

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
        LineCV l = (LineCV) o;
        double a = cos(l.getTheta());
        double b = sin(l.getTheta());
        double x0 = a * l.getRho(), y0 = b * l.getRho();

        opencv_core.Point pt1 = new opencv_core.Point(cvRound(x0 + 1000 * (-b)), cvRound(y0 + 1000 * (a))),
                pt2 = new opencv_core.Point(cvRound(x0 - 1000 * (-b)), cvRound(y0 - 1000 * (a)));

        Line line = new Line(pt1.x(), pt1.y(), pt2.x(), pt2.y());
        return (T) line;
    }

    @Override
    public Class< Line> getOutputType() {
        return Line.class;
    }

    @Override
    public Class< LineCV> getInputType() {
        return LineCV.class;
    }
}
