/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ijopencv.opencv;

import ij.gui.PolygonRoi;
import ij.gui.Roi;
import java.util.ArrayList;
import java.util.List;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.RectVector;
import org.scijava.Prioritized;
import org.scijava.Priority;
import org.scijava.convert.AbstractConverter;
import org.scijava.convert.Converter;
import org.scijava.log.LogService;
import org.scijava.plugin.Plugin;

@Plugin(type = Converter.class, priority = Priority.LOW_PRIORITY)
public class RectVectorListRoiConverter extends AbstractConverter<RectVector, List<Roi>> {

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
        opencv_core.RectVector rv = (opencv_core.RectVector) o;
        ArrayList<Roi> rois = new ArrayList<>();
        RectRoiConverter rc = new RectRoiConverter();
        for (int i = 0; i < rv.size(); i++) {
            rois.add(rc.convert(rv.get(i), Roi.class));
        }

        return (T) rois;

    }

    @Override
    public Class< List<Roi>> getOutputType() {
        return (Class<List<Roi>>) (Object) List.class;
    }

    @Override
    public Class< opencv_core.RectVector> getInputType() {
        return opencv_core.RectVector.class;
    }
}
