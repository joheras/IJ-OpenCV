/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ijopencv;

import ij.gui.Roi;
import java.awt.Rectangle;
import org.bytedeco.javacpp.opencv_core;

/**
 *
 * @author jonathan
 */
public class RoidConverter implements IConverter<Roi, opencv_core.Rectd>{

    @Override
    public opencv_core.Rectd convertTo(Roi r) {
        Rectangle r1 = r.getBounds();
        opencv_core.Rectd rect = new opencv_core.Rectd(r1.x, r1.y, r1.width, r1.height);
        return rect;
    }

    @Override
    public Roi convertFrom(opencv_core.Rectd rect) {
         Roi r = new Roi(rect.x(), rect.y(), rect.width(), rect.height());
        return r;
    }

    @Override
    public boolean canConvertTo(Roi I) {
        return true;
    }

    @Override
    public boolean canConvertFrom(opencv_core.Rectd o) {
        return true;
    }
    
}
