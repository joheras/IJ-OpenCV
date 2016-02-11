/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ijopencv;

import ij.gui.PointRoi;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;
import static org.bytedeco.javacpp.opencv_core.merge;
import static org.bytedeco.javacpp.opencv_core.split;

/**
 *
 * @author jonathan
 */
public class PointMatConverter implements IConverter<PointRoi, Mat>{

    @Override
    public Mat convertTo(PointRoi pr) {
         int[] xpoints = pr.getPolygon().xpoints;
        int[] ypoints = pr.getPolygon().ypoints;
        Mat mx = new Mat(xpoints);
        Mat my = new Mat(ypoints);
        opencv_core.MatVector mxy = new opencv_core.MatVector(2);
        mxy.put(0, mx);
        mxy.put(1, my);
        Mat m = new Mat();
        merge(mxy, m);
        return m;
    }

    @Override
    public PointRoi convertFrom(Mat m) {
        opencv_core.MatVector xy = new opencv_core.MatVector(2);
        split(m, xy);
        int h = m.rows();
        int[] xpoints = new int[h];
        int[] ypoints = new int[h];

        for (int i = 0; i < h; i++) {
            xpoints[i] = xy.get(0).getIntBuffer().get(i);
            ypoints[i] = xy.get(1).getIntBuffer().get(i);
        }

        PointRoi pr = new PointRoi(xpoints, ypoints, h);
        return pr;
    }

    @Override
    public boolean canConvertTo(PointRoi I) {
        return true;
    }

    @Override
    public boolean canConvertFrom(Mat o) {
        return true;
    }
    
}
