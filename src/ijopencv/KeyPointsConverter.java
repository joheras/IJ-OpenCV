/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ijopencv;

import ij.gui.PointRoi;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.KeyPointVector;

/**
 *
 * @author jonathan
 */
public class KeyPointsConverter implements IConverter<PointRoi, KeyPointVector>{

    @Override
    public KeyPointVector convertTo(PointRoi pr) {
        KeyPointVector pv = new KeyPointVector();
        int[] xpoints = pr.getPolygon().xpoints;
        int[] ypoints = pr.getPolygon().ypoints;

        for (int i = 0; i < xpoints.length; i++) {
            pv.put(i, new opencv_core.KeyPoint(xpoints[i], ypoints[i], 1));
        }
        return pv;
    }

    @Override
    public PointRoi convertFrom(KeyPointVector pv) {
        float[] xpoints = new float[(int) pv.size()];
        float[] ypoints = new float[(int) pv.size()];

        for (int i = 0; i < (int) pv.size(); i++) {
            xpoints[i] = (float) pv.get(i).pt().x();
            ypoints[i] = (float) pv.get(i).pt().y();
        }
        PointRoi pr = new PointRoi(xpoints, ypoints);
        return pr;
    }

    @Override
    public boolean canConvertTo(PointRoi I) {
        return true;
    }

    @Override
    public boolean canConvertFrom(KeyPointVector o) {
        return true;
    }
    
}
