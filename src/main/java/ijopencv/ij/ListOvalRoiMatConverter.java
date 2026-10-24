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
package ijopencv.ij;

import ij.gui.OvalRoi;
import ijopencv.utils.CircleCV;
import java.awt.Rectangle;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.bytedeco.opencv.opencv_core.Mat;
import static org.bytedeco.opencv.global.opencv_core.merge;
import org.scijava.Prioritized;
import org.scijava.Priority;
import org.scijava.convert.AbstractConverter;
import org.scijava.convert.Converter;
import org.scijava.log.LogService;
import org.scijava.plugin.Plugin;
import org.bytedeco.opencv.opencv_core.MatVector;
import org.bytedeco.opencv.opencv_core.Point;

@Plugin(type = Converter.class, priority = Priority.LOW_PRIORITY)
public class ListOvalRoiMatConverter extends AbstractConverter<List<OvalRoi>, Mat> {

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
        ArrayList<OvalRoi> alor = (ArrayList<OvalRoi>) o;
        float[] xpoints = new float[alor.size()];
        float[] ypoints = new float[alor.size()];
        float[] radiuses = new float[alor.size()];

        for (int i = 0; i < alor.size(); i++) {
            Rectangle rect = alor.get(i).getBounds();
            CircleCV c = new CircleCV(new Point(rect.x + rect.width / 2, rect.y + rect.height / 2), rect.width / 2);

            xpoints[i] = rect.x + rect.width / 2;
            ypoints[i] = rect.y + rect.height / 2;
            radiuses[i] = rect.width / 2;
        }

        Mat mx = new Mat(xpoints);
        Mat my = new Mat(ypoints);
        Mat mr = new Mat(radiuses);
        MatVector mxyr = new MatVector(3);
        mxyr.put(0, mx);
        mxyr.put(1, my);
        mxyr.put(2, mr);
        Mat m = new Mat();
        merge(mxyr, m);
        return (T) m;

    }

    @Override
    public Class< List<OvalRoi>> getInputType() {
        return (Class<List<OvalRoi>>) (Object) List.class;
    }

    @Override
    public Class< Mat> getOutputType() {
        return Mat.class;
    }

    @Override
    public boolean canConvert(Object src, Type dest) {
        
        if(!(src instanceof ArrayList<?>)){
            return false;
        }
        
        
        ArrayList<OvalRoi> alor = (ArrayList<OvalRoi>) src;

        for (int i = 0; i < alor.size(); i++) {
            Rectangle rect = alor.get(i).getBounds();
            if (rect.width != rect.height) {
                return false;
            }
        }
        return true;
    }

}
