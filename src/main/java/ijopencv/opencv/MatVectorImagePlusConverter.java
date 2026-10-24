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

import ij.ImagePlus;
import ij.ImageStack;
import org.bytedeco.opencv.opencv_core.MatVector;
import org.scijava.Prioritized;
import org.scijava.Priority;
import org.scijava.convert.AbstractConverter;
import org.scijava.convert.Converter;
import org.scijava.log.LogService;
import org.scijava.plugin.Plugin;

@Plugin(type = Converter.class, priority = Priority.LOW_PRIORITY)
public class MatVectorImagePlusConverter extends AbstractConverter< MatVector, ImagePlus> {

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
        ImageStack is = new ImageStack();
        ImagePlus temp;
        MatImagePlusConverter ic = new MatImagePlusConverter();

        for (int i = 0; i < mv.size(); i++) {
            temp = ic.convert(mv.get(i), ImagePlus.class);
            is.addSlice(temp.getProcessor());
        }

        ImagePlus imp = new ImagePlus("image", is);
        return (T) imp;
    }

    @Override
    public Class< ImagePlus> getOutputType() {
        return ImagePlus.class;
    }

    @Override
    public Class< MatVector> getInputType() {
        return MatVector.class;
    }
}
