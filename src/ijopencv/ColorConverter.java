/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ijopencv;

import java.awt.Color;
import org.bytedeco.javacpp.opencv_core.Scalar;

/**
 *
 * @author jonathan
 */
public class ColorConverter implements IConverter<Color, Scalar> {

    @Override
    public Scalar convertTo(Color c) {
        Scalar s = org.bytedeco.javacpp.helper.opencv_core.RGB(c.getRed(), c.getGreen(), c.getBlue());
        return s;
    }

    @Override
    public Color convertFrom(Scalar s) {
        Color c = new Color((int) s.red(), (int) s.green(), (int) s.blue());
        return c;
    }

    @Override
    public boolean canConvertTo(Color I) {
        return true;
    }

    @Override
    public boolean canConvertFrom(Scalar o) {
        return true;
    }
    
}
