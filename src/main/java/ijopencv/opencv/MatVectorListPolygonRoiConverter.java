/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ijopencv.opencv;

import ij.gui.PolygonRoi;
import ijopencv.ij.PolygonRoiMatConverter;
import java.util.ArrayList;
import java.util.List;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.scijava.Prioritized;
import org.scijava.Priority;
import org.scijava.convert.AbstractConverter;
import org.scijava.convert.Converter;
import org.scijava.log.LogService;
import org.scijava.plugin.Plugin;

@Plugin(type = Converter.class, priority = Priority.LOW_PRIORITY)
public class MatVectorListPolygonRoiConverter extends AbstractConverter<MatVector, List<PolygonRoi>> {

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
        MatVector mv = (MatVector) o;
        ArrayList<PolygonRoi> alproi = new ArrayList<PolygonRoi>();
        MatPolygonRoiConverter pg = new MatPolygonRoiConverter();
        for (int i = 0; i < mv.size(); i++) {
            alproi.add(pg.convert(mv.get(i), PolygonRoi.class));
        }
        return (T) alproi;

    }

    @Override
    public Class< List<PolygonRoi>> getOutputType() {
        return (Class<List<PolygonRoi>>) (Object) List.class;
    }

    @Override
    public Class< opencv_core.MatVector> getInputType() {
        return opencv_core.MatVector.class;
    }
}
