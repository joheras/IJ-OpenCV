/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ijopencv.ij;

import ij.gui.PolygonRoi;
import java.util.ArrayList;
import java.util.List;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.scijava.Prioritized;
import org.scijava.Priority;
import org.scijava.convert.AbstractConverter;
import org.scijava.convert.Converter;
import org.scijava.log.LogService;
import org.scijava.plugin.Plugin;

@Plugin(type = Converter.class, priority = Priority.LOW_PRIORITY)
public class ListPolygonRoiMatVectorConverter extends AbstractConverter<List<PolygonRoi>, opencv_core.MatVector> {

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
        ArrayList<PolygonRoi> aproi = (ArrayList<PolygonRoi>) o;
        opencv_core.MatVector mv = new opencv_core.MatVector();
        PolygonRoiMatConverter pg = new PolygonRoiMatConverter();

        for (int i = 0; i < aproi.size(); i++) {
            mv.put(i, pg.convert(aproi.get(i), Mat.class));
        }
        return (T) mv;

    }

    @Override
    public Class< List<PolygonRoi>> getInputType() {
        return (Class<List<PolygonRoi>>) (Object) List.class;
    }

    @Override
    public Class< MatVector> getOutputType() {
        return MatVector.class;
    }

}
