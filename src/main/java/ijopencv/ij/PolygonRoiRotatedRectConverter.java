package ijopencv.ij;

import ij.gui.PolygonRoi;
import java.lang.reflect.Type;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.RotatedRect;
import org.bytedeco.javacpp.opencv_imgproc;
import org.scijava.Prioritized;
import org.scijava.Priority;
import org.scijava.convert.AbstractConverter;
import org.scijava.convert.Converter;
import org.scijava.log.LogService;
import org.scijava.plugin.Plugin;

@Plugin(type = Converter.class, priority = Priority.LOW_PRIORITY)
public class PolygonRoiRotatedRectConverter extends AbstractConverter< PolygonRoi, RotatedRect> {

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
    PolygonRoi pr = (PolygonRoi)o;
        PolygonRoiMatConverter pg = new PolygonRoiMatConverter();
        opencv_core.Mat points = pg.convert(pr,Mat.class);
        opencv_core.RotatedRect rr = opencv_imgproc.minAreaRect(points);
        return (T)rr;
    }

    @Override
    public Class< RotatedRect> getOutputType() {
        return RotatedRect.class;
    }

    @Override
    public Class< PolygonRoi> getInputType() {
        return PolygonRoi.class;
    }

    @Override
    public boolean canConvert(Object src, Type dest) {
        PolygonRoi pr =(PolygonRoi)src;
     if(!pr.isArea() || pr.getPolygon().npoints != 4 ){
            return false;
        }
        return true;
    }
    
    
}
