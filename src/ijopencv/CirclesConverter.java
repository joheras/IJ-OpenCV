/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ijopencv;

import ij.gui.OvalRoi;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;
import static org.bytedeco.javacpp.opencv_core.merge;
import static org.bytedeco.javacpp.opencv_core.split;

/**
 *
 * @author jonathan
 */
public class CirclesConverter implements IConverter<ArrayList<OvalRoi>, Mat> {

    @Override
    public Mat convertTo(ArrayList<OvalRoi> alor) {
        if(!canConvertTo(alor)){
            try {
                throw new Exception("All the elements of the array must be circles");
            } catch (Exception ex) {
                Logger.getLogger(CirclesConverter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        float[] xpoints = new float[alor.size()];
        float[] ypoints = new float[alor.size()];
        float[] radiuses = new float[alor.size()];

        for (int i = 0; i < alor.size(); i++) {
            Rectangle rect = alor.get(i).getBounds();
            CircleCV c = new CircleCV(new opencv_core.Point(rect.x + rect.width / 2, rect.y + rect.height / 2), rect.width / 2);

            xpoints[i] = rect.x + rect.width / 2;
            ypoints[i] = rect.y + rect.height / 2;
            radiuses[i] = rect.width / 2;
        }

        Mat mx = new Mat(xpoints);
        Mat my = new Mat(ypoints);
        Mat mr = new Mat(radiuses);
        opencv_core.MatVector mxyr = new opencv_core.MatVector(3);
        mxyr.put(0, mx);
        mxyr.put(1, my);
        mxyr.put(2, mr);
        Mat m = new Mat();
        merge(mxyr, m);
        return m;
    }

    @Override
    public ArrayList<OvalRoi> convertFrom(Mat circles) {
        opencv_core.MatVector xyr = new opencv_core.MatVector(3);
        split(circles, xyr);
        int num = circles.size().height();

        ArrayList<OvalRoi> result = new ArrayList<>();

        for (int i = 0; i < num; i++) {
            int cx = (int) xyr.get(0).getFloatBuffer().get(i);
            int cy = (int) xyr.get(1).getFloatBuffer().get(i);
            int r = (int) xyr.get(2).getFloatBuffer().get(i);

            OvalRoi or = new OvalRoi(cx - r, cy - r, r * 2, r * 2);
            result.add(or);
        }

        return result;
    }

    @Override
    public boolean canConvertTo(ArrayList<OvalRoi> alor) {
        for (int i = 0; i < alor.size(); i++) {
            Rectangle rect = alor.get(i).getBounds();
            if (rect.width != rect.height) {
                return false;
            }            
        }
        return true;
    }

    @Override
    public boolean canConvertFrom(Mat o) {
        return true;
    }
    
}
