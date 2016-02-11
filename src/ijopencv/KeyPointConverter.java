/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ijopencv;

import ij.gui.PointRoi;
import ij.gui.Roi;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.KeyPoint;

/**
 *
 * @author jonathan
 */
public class KeyPointConverter implements IConverter<PointRoi, KeyPoint>{

    @Override
    public KeyPoint convertTo(PointRoi pr) {
        if (!canConvertTo(pr)) {
            try {
                throw new Exception("You must provide a point and not a list of points");
            } catch (Exception ex) {
                Logger.getLogger(KeyPointConverter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        opencv_core.KeyPoint p = new KeyPoint(pr.getXCoordinates()[0], pr.getYCoordinates()[0], 1);
        return p;
    }

    @Override
    public PointRoi convertFrom(KeyPoint p) {
        if (!canConvertFrom(p)) {
            try {
                throw new Exception("The keypoint should only have two values");
            } catch (Exception ex) {
                Logger.getLogger(KeyPointConverter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        PointRoi pr = new PointRoi(p.pt().x(), p.pt().y());
        return pr;
    }

    @Override
    public boolean canConvertTo(PointRoi pr) {
        if (pr.getType() != Roi.POINT) {
            return false;
        }
        return true;
    }

    @Override
    public boolean canConvertFrom(KeyPoint p) {
        if (p.sizeof() != 2) {
            return false;
        }
        return true;
    }
    
}
