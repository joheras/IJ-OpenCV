/*-
 * #%L
 * A library that allows the connection of ImageJ and OpenCV.
 * %%
 * Copyright (C) 2017 - 2026 University of La Rioja
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package ijopencv.opencv;

import ij.gui.Line;
import ijopencv.utils.LineCV;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static org.bytedeco.opencv.global.opencv_core.cvRound;
import org.scijava.Prioritized;
import org.scijava.Priority;
import org.scijava.convert.AbstractConverter;
import org.scijava.convert.Converter;
import org.scijava.log.LogService;
import org.scijava.plugin.Plugin;
import org.bytedeco.opencv.opencv_core.Point;

@Plugin(type = Converter.class, priority = Priority.LOW_PRIORITY)
public class LineCVLineConverter extends AbstractConverter< LineCV, Line> {

    @Override
    public int compareTo(Prioritized o) {
        return super.compareTo(o);
    }

    @Override
    public LogService log() {
        return super.log();
    }

    @Override
    public String getIdentifier() {
        return super.getIdentifier();
    }

    @Override
    public < T> T convert(Object o, Class< T> type) {
        LineCV l = (LineCV) o;
        double a = cos(l.getTheta());
        double b = sin(l.getTheta());
        double x0 = a * l.getRho(), y0 = b * l.getRho();

        Point pt1 = new Point(cvRound(x0 + 1000 * (-b)), cvRound(y0 + 1000 * (a))),
                pt2 = new Point(cvRound(x0 - 1000 * (-b)), cvRound(y0 - 1000 * (a)));

        Line line = new Line(pt1.x(), pt1.y(), pt2.x(), pt2.y());
        return (T) line;
    }

    @Override
    public Class< Line> getOutputType() {
        return Line.class;
    }

    @Override
    public Class< LineCV> getInputType() {
        return LineCV.class;
    }
}
