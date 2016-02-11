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
import org.bytedeco.javacpp.opencv_core.RotatedRect;
import static org.bytedeco.javacpp.opencv_core.merge;
import static org.bytedeco.javacpp.opencv_imgproc.minAreaRect;

/**
 *
 * @author jonathan
 */
public class OvalConverter implements IConverter<OvalRoi, RotatedRect>{

    @Override
    public RotatedRect convertTo(OvalRoi or) {
        Rectangle rect = or.getPolygon().getBounds();

        int[] xpoints = {rect.x, rect.x, rect.x + rect.width, rect.x + rect.width};
        int[] ypoints = {rect.y, rect.y + rect.height, rect.y + rect.height, rect.y};
        opencv_core.Mat x = new opencv_core.Mat(xpoints);
        opencv_core.Mat y = new opencv_core.Mat(ypoints);
        opencv_core.MatVector xy = new opencv_core.MatVector(2);
        opencv_core.Mat points = new opencv_core.Mat();
        merge(xy, points);
        return minAreaRect(points);
    }

    @Override
    public OvalRoi convertFrom(RotatedRect O) {
        try {
            throw new Exception("It is not possible to convert a RotatedRect to a Ellipse, you can convert it to a PolygonRoi"); 
        } catch (Exception ex) {
            Logger.getLogger(EllipseConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean canConvertTo(OvalRoi I) {
       return true;
    }

    @Override
    public boolean canConvertFrom(RotatedRect o) {
        return false;
    }
    
}
