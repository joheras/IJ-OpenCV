/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ijopencv;

import ij.gui.OvalRoi;
import java.awt.Rectangle;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bytedeco.javacpp.opencv_core;

/**
 *
 * @author jonathan
 */
public class Circle2fConverter implements IConverter<OvalRoi, Circle2fCV>{

    @Override
    public Circle2fCV convertTo(OvalRoi or) {
        Rectangle rect = or.getPolygon().getBounds();
        if (!canConvertTo(or)) {
            try {
                throw new Exception("The oval must be a circle");
            } catch (Exception ex) {
                Logger.getLogger(CircleConverter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Circle2fCV c = new Circle2fCV(new opencv_core.Point2f(rect.x + rect.width / 2, rect.y + rect.height / 2), rect.width / 2);
        return c;
    }

    @Override
    public OvalRoi convertFrom(Circle2fCV c) {
        OvalRoi or = new OvalRoi(c.getCenter().x() - c.getRadius(),
                c.getCenter().y() - c.getRadius(), c.getRadius() * 2, c.getRadius() * 2);
        return or;
    }

    @Override
    public boolean canConvertTo(OvalRoi or) {
        Rectangle rect = or.getPolygon().getBounds();
        if (rect.width != rect.height) {
            return false;
        }
        return true;
    }

    @Override
    public boolean canConvertFrom(Circle2fCV o) {
        return true;
    }
    
}
