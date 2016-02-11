/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ijopencv;

import ij.gui.Roi;
import java.awt.Rectangle;
import org.bytedeco.javacpp.opencv_core.Rect;

/**
 *
 * @author jonathan
 */
public class RoiConverter implements IConverter<Roi, Rect>{

    @Override
    public Rect convertTo(Roi r) {
        Rectangle r1 = r.getBounds();
        Rect rect = new Rect(r1.x, r1.y, r1.width, r1.height);
        return rect;
    }

    @Override
    public Roi convertFrom(Rect rect) {
         Roi r = new Roi(rect.x(), rect.y(), rect.width(), rect.height());
        return r;
    }

    @Override
    public boolean canConvertTo(Roi I) {
        return true;
    }

    @Override
    public boolean canConvertFrom(Rect o) {
        return true;
    }
    
}
