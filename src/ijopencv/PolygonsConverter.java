/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ijopencv;

import ij.gui.PolygonRoi;
import java.util.ArrayList;
import org.bytedeco.javacpp.opencv_core;

/**
 *
 * @author jonathan
 */
public class PolygonsConverter implements IConverter<ArrayList<PolygonRoi>, opencv_core.MatVector> {

    @Override
    public opencv_core.MatVector convertTo(ArrayList<PolygonRoi> aproi) {
        opencv_core.MatVector mv = new opencv_core.MatVector();
        PolygonConverter pg = new PolygonConverter();

        for (int i = 0; i < aproi.size(); i++) {
            mv.put(i, pg.convertTo(aproi.get(i)));
        }
        return mv;
    }

    @Override
    public ArrayList<PolygonRoi> convertFrom(opencv_core.MatVector mv) {
        ArrayList<PolygonRoi> alproi = new ArrayList<>();
        PolygonConverter pg = new PolygonConverter();
        for (int i = 0; i < mv.size(); i++) {
            alproi.add(pg.convertFrom(mv.get(i)));
        }
        return alproi;
    }

    @Override
    public boolean canConvertTo(ArrayList<PolygonRoi> I) {
        return true;
    }

    @Override
    public boolean canConvertFrom(opencv_core.MatVector o) {
       return true;
    }
    
}
