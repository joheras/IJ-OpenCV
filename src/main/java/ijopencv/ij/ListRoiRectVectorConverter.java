/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ijopencv.ij;

import ij.gui.Roi;
import java.util.ArrayList;
import java.util.List;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.RectVector;
import org.scijava.Prioritized;
import org.scijava.Priority;
import org.scijava.convert.AbstractConverter;
import org.scijava.convert.Converter;
import org.scijava.log.LogService;
import org.scijava.plugin.Plugin;

@Plugin(type = Converter.class, priority = Priority.LOW_PRIORITY)
public class ListRoiRectVectorConverter extends AbstractConverter<List<Roi>, opencv_core.RectVector> {

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
        ArrayList<Roi> aroi = (ArrayList<Roi>) o;
        RectVector rv = new RectVector();
        RoiRectConverter rc = new RoiRectConverter();
        for (int i = 0; i < aroi.size(); i++) {
            rv.put(i, rc.convert(aroi.get(i), Rect.class));
        }

        return (T) rv;

    }

    @Override
    public Class< List<Roi>> getInputType() {
        return (Class<List<Roi>>) (Object) List.class;
    }

    @Override
    public Class< RectVector> getOutputType() {
        return RectVector.class;
    }
}
