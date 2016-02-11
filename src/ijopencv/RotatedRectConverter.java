/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ijopencv;

import ij.gui.PolygonRoi;
import ij.gui.Roi;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.RotatedRect;
import org.bytedeco.javacpp.opencv_imgproc;

/**
 *
 * @author jonathan
 */
public class RotatedRectConverter implements IConverter<PolygonRoi, RotatedRect>{

    @Override
    public RotatedRect convertTo(PolygonRoi pr) {
        if (!canConvertTo(pr)){
            try {
                throw new Exception("The polygonRoi must have only 4 points");
            } catch (Exception ex) {
                Logger.getLogger(RotatedRectConverter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        PolygonConverter pg = new PolygonConverter();
        opencv_core.Mat points = pg.convertTo(pr);
        opencv_core.RotatedRect rr = opencv_imgproc.minAreaRect(points);
        return rr;
    }

    @Override
    public PolygonRoi convertFrom(RotatedRect rr) {
        double _angle = rr.angle() * Math.PI / 180.0;
        double b = (double) Math.cos(_angle) * 0.5f;
        double a = (double) Math.sin(_angle) * 0.5f;

        opencv_core.Point pt0 = new opencv_core.Point(
                (int) (rr.center().x() - a * rr.size().height() - b * rr.size().width()),
                (int) (rr.center().y() + b * rr.size().height() - a * rr.size().width()));

        opencv_core.Point pt1 = new opencv_core.Point(
                (int) (rr.center().x() + a * rr.size().height() - b * rr.size().width()),
                (int) (rr.center().y() - b * rr.size().height() - a * rr.size().width()));

        opencv_core.Point pt2 = new opencv_core.Point((int) (2 * rr.center().x() - pt0.x()),
                (int) (2 * rr.center().y() - pt0.y()));

        opencv_core.Point pt3 = new opencv_core.Point((int) (2 * rr.center().x() - pt1.x()),
                (int) (2 * rr.center().y() - pt1.y()));

        int[] xpoints = new int[4];
        int[] ypoints = new int[4];

        xpoints[0] = pt0.x();
        ypoints[0] = pt0.y();
        xpoints[1] = pt1.x();
        ypoints[1] = pt1.y();
        xpoints[2] = pt2.x();
        ypoints[2] = pt2.y();
        xpoints[3] = pt3.x();
        ypoints[3] = pt3.y();

        PolygonRoi pr = new PolygonRoi(xpoints, ypoints, 4, Roi.POLYGON);
        return pr;
    }

    @Override
    public boolean canConvertTo(PolygonRoi pr) {
        if(!pr.isArea() || pr.getPolygon().npoints != 4 ){
            return false;
        }
        return true;
    }

    @Override
    public boolean canConvertFrom(RotatedRect o) {
       return true;
    }
    
}
