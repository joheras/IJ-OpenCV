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
public class RoifConverter implements IConverter<Roi, opencv_core.Rectf>{

    @Override
    public opencv_core.Rectf convertTo(Roi r) {
        Rectangle r1 = r.getBounds();
        opencv_core.Rectf rect = new opencv_core.Rectf(r1.x, r1.y, r1.width, r1.height);
        return rect;
    }

    @Override
    public Roi convertFrom(opencv_core.Rectf rect) {
         Roi r = new Roi(rect.x(), rect.y(), rect.width(), rect.height());
        return r;
    }

    @Override
    public boolean canConvertTo(Roi I) {
        return true;
    }

    @Override
    public boolean canConvertFrom(opencv_core.Rectf o) {
        return true;
    }
    
    
}
