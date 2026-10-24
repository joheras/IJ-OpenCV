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
package ijopencv.ij;

import ij.gui.OvalRoi;
import ijopencv.utils.Circle2fCV;
import ijopencv.utils.CircleCV;
import java.awt.Rectangle;
import java.lang.reflect.Type;
import org.bytedeco.opencv.opencv_core.RotatedRect;
import static org.bytedeco.opencv.global.opencv_core.merge;
import static org.bytedeco.opencv.global.opencv_imgproc.minAreaRect;
import org.scijava.Prioritized;
import org.scijava.Priority;
import org.scijava.convert.AbstractConverter;
import org.scijava.convert.Converter;
import org.scijava.log.LogService;
import org.scijava.plugin.Plugin;
import org.bytedeco.opencv.opencv_core.Point;

@Plugin(type = Converter.class, priority = Priority.LOW_PRIORITY)
public class OvalRoiCircleCVConverter extends AbstractConverter< OvalRoi, CircleCV> {

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
        OvalRoi or = (OvalRoi) o;
        Rectangle rect = or.getPolygon().getBounds();
        CircleCV c = new CircleCV(new Point(rect.x + rect.width / 2, rect.y + rect.height / 2), rect.width / 2);
        return (T) c;
    }

    @Override
    public Class< CircleCV> getOutputType() {
        return CircleCV.class;
    }

    @Override
    public Class< OvalRoi> getInputType() {
        return OvalRoi.class;
    }
    
     @Override
    public boolean canConvert(Object src, Type dest) {
        if(!(src instanceof OvalRoi)){
            return false;
        }
        OvalRoi or = (OvalRoi)src;
        Rectangle rect = or.getPolygon().getBounds();
        if (rect.width != rect.height) {
            return false;
        }
        return true;
    }
}
