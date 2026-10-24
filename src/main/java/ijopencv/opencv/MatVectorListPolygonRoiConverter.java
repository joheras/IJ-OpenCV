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

import ij.gui.PolygonRoi;
import ijopencv.ij.PolygonRoiMatConverter;
import java.util.ArrayList;
import java.util.List;
import org.bytedeco.opencv.opencv_core.MatVector;
import org.scijava.Prioritized;
import org.scijava.Priority;
import org.scijava.convert.AbstractConverter;
import org.scijava.convert.Converter;
import org.scijava.log.LogService;
import org.scijava.plugin.Plugin;

@Plugin(type = Converter.class, priority = Priority.LOW_PRIORITY)
public class MatVectorListPolygonRoiConverter extends AbstractConverter<MatVector, List<PolygonRoi>> {

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
        MatVector mv = (MatVector) o;
        ArrayList<PolygonRoi> alproi = new ArrayList<PolygonRoi>();
        MatPolygonRoiConverter pg = new MatPolygonRoiConverter();
        for (int i = 0; i < mv.size(); i++) {
            alproi.add(pg.convert(mv.get(i), PolygonRoi.class));
        }
        return (T) alproi;

    }

    @Override
    public Class< List<PolygonRoi>> getOutputType() {
        return (Class<List<PolygonRoi>>) (Object) List.class;
    }

    @Override
    public Class< MatVector> getInputType() {
        return MatVector.class;
    }
}
