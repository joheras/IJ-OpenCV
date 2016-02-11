/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ijopencv;

import ij.gui.Line;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;
import static org.bytedeco.javacpp.opencv_core.cvRound;
import static org.bytedeco.javacpp.opencv_core.split;

/**
 *
 * @author jonathan
 */
public class LinesConverter implements IConverter<ArrayList<Line>, Mat>{

    @Override
    public Mat convertTo(ArrayList<Line> I) {
         try {
            throw new Exception("It is not possible to perform this conversion"); //To change body of generated methods, choose Tools | Templates.
        } catch (Exception ex) {
            Logger.getLogger(EllipseConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ArrayList<Line> convertFrom(Mat lines) {
        ArrayList<Line> ijlines = new ArrayList<>();
        opencv_core.MatVector xy = new opencv_core.MatVector(2);
        split(lines, xy);
        for (int i = 0; i < lines.rows(); i++) {

            float rho = xy.get(0).getFloatBuffer().get(i);
            float theta = xy.get(1).getFloatBuffer().get(i);

            double a = cos(theta);
            double b = sin(theta);
            double x0 = a * rho, y0 = b * rho;

            opencv_core.Point pt1 = new opencv_core.Point(cvRound(x0 + 1000 * (-b)), cvRound(y0 + 1000 * (a))),
                    pt2 = new opencv_core.Point(cvRound(x0 - 1000 * (-b)), cvRound(y0 - 1000 * (a)));
            Line line = new Line(x0 + 1000 * (-b), y0 + 1000 * (a), x0 - 1000 * (-b), y0 - 1000 * (a));
            ijlines.add(line);
        }
        return ijlines;
    }

    @Override
    public boolean canConvertTo(ArrayList<Line> I) {
        return false;
    }

    @Override
    public boolean canConvertFrom(Mat o) {
        return true;
    }
    
}
