/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ijopencv;

import ij.gui.EllipseRoi;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.RotatedRect;
import org.bytedeco.javacpp.opencv_imgproc;

/**
 *
 * @author jonathan
 */
public class EllipseConverter implements IConverter<EllipseRoi, RotatedRect> {

    @Override
    public RotatedRect convertTo(EllipseRoi er) {
        PolygonConverter pg = new PolygonConverter();
        opencv_core.Mat points = pg.convertTo(er);
        opencv_core.RotatedRect rr = opencv_imgproc.minAreaRect(points);
        return rr;
    }

    @Override
    public EllipseRoi convertFrom(RotatedRect O) {
        try {
            throw new Exception("It is not possible to convert a RotatedRect to a Ellipse, you can convert it to a PolygonRoi"); //To change body of generated methods, choose Tools | Templates.
        } catch (Exception ex) {
            Logger.getLogger(EllipseConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean canConvertTo(EllipseRoi I) {
        return true;
    }

    @Override
    public boolean canConvertFrom(RotatedRect o) {
        return false;
    }
    
}
