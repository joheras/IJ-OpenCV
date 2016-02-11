/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ijopencv;

import ij.gui.PolygonRoi;
import ij.gui.Roi;
import org.bytedeco.javacpp.opencv_core;

/**
 *
 * @author jonathan
 */
public class Polygon2fConverter implements IConverter<PolygonRoi, opencv_core.Point2f>{

    @Override
    public opencv_core.Point2f convertTo(PolygonRoi pr) {
        opencv_core.Point2f res = new opencv_core.Point2f();
        int[] xpoints = pr.getPolygon().xpoints;
        int[] ypoints = pr.getPolygon().ypoints;
        for (int i = 0; i < xpoints.length; i++) {
            opencv_core.Point2f p = new opencv_core.Point2f(xpoints[i], ypoints[i]);
            res.put(p);
        }
        return res;
    }

    @Override
    public PolygonRoi convertFrom(opencv_core.Point2f p) {
       int[] xpoints = new int[p.sizeof() / 2];
        int[] ypoints = new int[p.sizeof() / 2];

        for (int i = 0; i < p.sizeof(); i = i + 2) {

            xpoints[i / 2] = (int) p.get(i);
            ypoints[i / 2] = (int) p.get(i + 1);
        }
        PolygonRoi pr = new PolygonRoi(xpoints, ypoints, p.sizeof() / 2, Roi.POLYGON);
        return pr;
    }

    @Override
    public boolean canConvertTo(PolygonRoi I) {
        return true;
    }

    @Override
    public boolean canConvertFrom(opencv_core.Point2f o) {
        return true;
    }
    
}
