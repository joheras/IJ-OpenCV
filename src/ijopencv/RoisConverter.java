/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ijopencv;

import ij.gui.Roi;
import java.util.ArrayList;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.RectVector;

/**
 *
 * @author jonathan
 */
public class RoisConverter implements IConverter<ArrayList<Roi>, opencv_core.RectVector>{

    @Override
    public opencv_core.RectVector convertTo(ArrayList<Roi> aroi) {
        RectVector rv = new RectVector();
        RoiConverter rc = new RoiConverter();
        for (int i = 0; i < aroi.size(); i++) {
            rv.put(i, rc.convertTo(aroi.get(i)));
        }

        return rv;
    }

    @Override
    public ArrayList<Roi> convertFrom(opencv_core.RectVector rv) {
        ArrayList<Roi> rois = new ArrayList<>();
        RoiConverter rc = new RoiConverter();
        for (int i = 0; i < rv.size(); i++) {
            rois.add(rc.convertFrom(rv.get(i)));
        }

        return rois;
    }

    @Override
    public boolean canConvertTo(ArrayList<Roi> I) {
        return true;
    }

    @Override
    public boolean canConvertFrom(opencv_core.RectVector o) {
        return true;
    }
    
}
