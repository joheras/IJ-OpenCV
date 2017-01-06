/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ijopencv.opencv;

import ij.gui.Line;
import ij.gui.OvalRoi;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.ArrayList;
import java.util.List;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;
import static org.bytedeco.javacpp.opencv_core.cvRound;
import static org.bytedeco.javacpp.opencv_core.split;
import org.scijava.Prioritized;
import org.scijava.Priority;
import org.scijava.convert.AbstractConverter;
import org.scijava.convert.Converter;
import org.scijava.log.LogService;
import org.scijava.plugin.Plugin;

@Plugin(type = Converter.class, priority = Priority.LOW_PRIORITY)
public class MatListOvalRoiConverter extends AbstractConverter<Mat, List<OvalRoi>> {

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
        Mat circles = (Mat) o;
        opencv_core.MatVector xyr = new opencv_core.MatVector(3);
        split(circles, xyr);
        int num = circles.size().height();

        ArrayList<OvalRoi> result = new ArrayList<OvalRoi>();

        for (int i = 0; i < num; i++) {
            int cx = (int) xyr.get(0).getFloatBuffer().get(i);
            int cy = (int) xyr.get(1).getFloatBuffer().get(i);
            int r = (int) xyr.get(2).getFloatBuffer().get(i);

            OvalRoi or = new OvalRoi(cx - r, cy - r, r * 2, r * 2);
            result.add(or);
        }

        return (T) result;
    }

    @Override
    public Class< List<OvalRoi>> getOutputType() {
        return (Class<List<OvalRoi>>) (Object) List.class;
    }

    @Override
    public Class< opencv_core.Mat> getInputType() {
        return opencv_core.Mat.class;
    }
}
