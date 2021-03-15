/*-
 * #%L
 * A library that allows the connection of ImageJ and OpenCV.
 * %%
 * Copyright (C) 2017 - 2021 University of La Rioja
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package ijopencv.opencv;

import ij.gui.Line;
import ijopencv.utils.LineCV;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import org.bytedeco.javacpp.opencv_core;
import static org.bytedeco.javacpp.opencv_core.cvRound;
import org.scijava.Prioritized;
import org.scijava.Priority;
import org.scijava.convert.AbstractConverter;
import org.scijava.convert.Converter;
import org.scijava.log.LogService;
import org.scijava.plugin.Plugin;

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

        opencv_core.Point pt1 = new opencv_core.Point(cvRound(x0 + 1000 * (-b)), cvRound(y0 + 1000 * (a))),
                pt2 = new opencv_core.Point(cvRound(x0 - 1000 * (-b)), cvRound(y0 - 1000 * (a)));

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
