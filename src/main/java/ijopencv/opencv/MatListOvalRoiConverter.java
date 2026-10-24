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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ijopencv.opencv;

import ij.gui.Line;
import ij.gui.OvalRoi;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.ArrayList;
import java.util.List;
import org.bytedeco.opencv.opencv_core.Mat;
import static org.bytedeco.opencv.global.opencv_core.cvRound;
import static org.bytedeco.opencv.global.opencv_core.split;
import org.scijava.Prioritized;
import org.scijava.Priority;
import org.scijava.convert.AbstractConverter;
import org.scijava.convert.Converter;
import org.scijava.log.LogService;
import org.scijava.plugin.Plugin;
import org.bytedeco.opencv.opencv_core.MatVector;

@Plugin(type = Converter.class, priority = Priority.LOW_PRIORITY)
public class MatListOvalRoiConverter extends AbstractConverter<Mat, List<OvalRoi>> {

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
        Mat circles = (Mat) o;
        MatVector xyr = new MatVector(3);
        split(circles, xyr);
        int num = circles.size().height();

        ArrayList<OvalRoi> result = new ArrayList<OvalRoi>();

        for (int i = 0; i < num; i++) {
            int cx = (int) xyr.get(0).getFloatBuffer().get(i);
            int cy = (int) xyr.get(1).getFloatBuffer().get(i);
            int r = (int) xyr.get(2).getFloatBuffer().get(i);

            OvalRoi or = new OvalRoi(cx - r, cy - r, r * 2, r * 2);
            result.add(or);
        }

        return (T) result;
    }

    @Override
    public Class< List<OvalRoi>> getOutputType() {
        return (Class<List<OvalRoi>>) (Object) List.class;
    }

    @Override
    public Class< Mat> getInputType() {
        return Mat.class;
    }
}
