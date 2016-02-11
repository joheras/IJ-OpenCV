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
public class CircleConverter implements IConverter<OvalRoi, CircleCV>{

    @Override
    public CircleCV convertTo(OvalRoi or) {
        Rectangle rect = or.getPolygon().getBounds();
        if (!canConvertTo(or)) {
            try {
                throw new Exception("The oval must be a circle");
            } catch (Exception ex) {
                Logger.getLogger(CircleConverter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        CircleCV c = new CircleCV(new opencv_core.Point(rect.x + rect.width / 2, rect.y + rect.height / 2), rect.width / 2);
        return c;
    }

    @Override
    public OvalRoi convertFrom(CircleCV c) {
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
    public boolean canConvertFrom(CircleCV o) {
        return true;
    }
    
}
