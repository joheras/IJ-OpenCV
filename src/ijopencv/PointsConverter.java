/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ijopencv;

import ij.gui.PointRoi;
import org.bytedeco.javacpp.opencv_core;

/**
 *
 * @author jonathan
 */
public class PointsConverter implements IConverter<PointRoi, opencv_core.PointVector> {

    @Override
    public opencv_core.PointVector convertTo(PointRoi pr) {
        opencv_core.PointVector pv = new opencv_core.PointVector();
        int[] xpoints = pr.getPolygon().xpoints;
        int[] ypoints = pr.getPolygon().ypoints;

        for (int i = 0; i < xpoints.length; i++) {
            pv.put(i, new opencv_core.Point(xpoints[i], ypoints[i]));
        }
        return pv;
    }

    @Override
    public PointRoi convertFrom(opencv_core.PointVector pv) {
        int[] xpoints = new int[(int) pv.size()];
        int[] ypoints = new int[(int) pv.size()];

        for (int i = 0; i < (int) pv.size(); i++) {
            xpoints[i] = pv.get(i).x();
            ypoints[i] = pv.get(i).y();
        }

        PointRoi pr = new PointRoi(xpoints, ypoints, pv.sizeof());
        return pr;
    }

    @Override
    public boolean canConvertTo(PointRoi I) {
        return true;
    }

    @Override
    public boolean canConvertFrom(opencv_core.PointVector o) {
        return true;
    }
    
    
}
