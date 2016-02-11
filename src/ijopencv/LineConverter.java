/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ijopencv;

import ij.gui.Line;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bytedeco.javacpp.opencv_core;
import static org.bytedeco.javacpp.opencv_core.cvRound;

/**
 *
 * @author jonathan
 */
public class LineConverter implements IConverter<Line, LineCV> {

    @Override
    public LineCV convertTo(Line I) {
         try {
            throw new Exception("It is not possible to perform this conversion"); //To change body of generated methods, choose Tools | Templates.
        } catch (Exception ex) {
            Logger.getLogger(EllipseConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Line convertFrom(LineCV l) {
        double a = cos(l.getTheta());
        double b = sin(l.getTheta());
        double x0 = a * l.getRho(), y0 = b * l.getRho();

        opencv_core.Point pt1 = new opencv_core.Point(cvRound(x0 + 1000 * (-b)), cvRound(y0 + 1000 * (a))),
                pt2 = new opencv_core.Point(cvRound(x0 - 1000 * (-b)), cvRound(y0 - 1000 * (a)));

        Line line = new Line(pt1.x(), pt1.y(), pt2.x(), pt2.y());
        return line;
    }

    @Override
    public boolean canConvertTo(Line I) {
        return false;
    }

    @Override
    public boolean canConvertFrom(LineCV o) {
        return true;
    }
    
}
