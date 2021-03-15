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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ijopencv.opencv;

import ij.gui.Line;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.ArrayList;
import java.util.List;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;
import static org.bytedeco.javacpp.opencv_core.cvRound;
import static org.bytedeco.javacpp.opencv_core.split;
import org.scijava.Prioritized;
import org.scijava.Priority;
import org.scijava.convert.AbstractConverter;
import org.scijava.convert.Converter;
import org.scijava.log.LogService;
import org.scijava.plugin.Plugin;

@Plugin(type = Converter.class, priority = Priority.LOW_PRIORITY)
public class MatListLineConverter extends AbstractConverter<Mat, List<Line>> {

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
        Mat lines = (Mat) o;
        ArrayList<Line> ijlines = new ArrayList<Line>();
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
        return (T) ijlines;
    }

    @Override
    public Class< List<Line>> getOutputType() {
        return (Class<List<Line>>) (Object) List.class;
    }

    @Override
    public Class< Mat> getInputType() {
        return Mat.class;
    }
}
