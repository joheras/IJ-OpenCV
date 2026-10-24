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
import ij.process.ByteProcessor;
import ij.process.ColorProcessor;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;
import ij.process.ShortProcessor;
import org.bytedeco.javacpp.FloatPointer;
import org.bytedeco.javacpp.ShortPointer;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.opencv_core.Mat;
import org.scijava.Prioritized;
import org.scijava.Priority;
import org.scijava.convert.AbstractConverter;
import org.scijava.convert.Converter;
import org.scijava.log.LogService;
import org.scijava.plugin.Plugin;

/**
 * @author J. Heras
 * @author W. Burger
 */
@Plugin(type = Converter.class, priority = Priority.LOW_PRIORITY)
public class MatImagePlusConverter extends AbstractConverter< Mat, ImagePlus> {

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
        Mat image = (Mat) o;
        ImageProcessor ip = toImageProcessor(image);
        return (T) (new ImagePlus("image", ip));
    }

    @Override
    public Class< ImagePlus> getOutputType() {
        return ImagePlus.class;
    }

    @Override
    public Class< Mat> getInputType() {
        return Mat.class;
    }
    
    /**
     * Convert from an OpenCV Matrix to the equivalent ImageProcessor
     * @param mat the OpenCV matrix to convert
     * @return result the ImageProcessor resulting from the conversion
     */
    public static ImageProcessor toImageProcessor(Mat mat) {
        final int type = mat.type();
        ImageProcessor result = null;

        if (type == opencv_core.CV_8UC1) { // type = BufferedImage.TYPE_BYTE_GRAY;
            result = makeByteProcessor(mat);
        } else if (type == opencv_core.CV_8UC3) {	// type = BufferedImage.TYPE_3BYTE_BGR;
            result = makeColorProcessor(mat); // faulty 
        } else if (type == opencv_core.CV_16UC1) {	// signed short image
            result = makeShortProcessor(mat);
        } else if (type == opencv_core.CV_32FC1) {	// float image
            result = makeFloatProcessor(mat);
        } else {
            throw new IllegalArgumentException("cannot convert Mat of type " + type);
        }
        return result;
    }
    
    /**
     * Convert from an OpenCV Matrix to the equivalent ImagePlus with default title Image
     * @param mat the OpenCV matrix to convert
     * @return result the ImagePlus resulting from the conversion
     */
    public static ImagePlus toImagePlus(Mat mat) {
    	ImageProcessor ip = toImageProcessor(mat);
        return new ImagePlus("Image", ip);
        }
    
    /**
     * Convert from an OpenCV Matrix to the equivalent ImagePlus with custom title
     * @param mat the OpenCV matrix to convert
     * @param title Title for the ImagePlus
     * @return result the ImagePlus resulting from the conversion
     */
    public static ImagePlus toImagePlus(Mat mat, String title) {
    	ImageProcessor ip = toImageProcessor(mat);
        return new ImagePlus(title, ip);
        }    
    
    

    // private methods ----------------------------------------------
    private static ByteProcessor makeByteProcessor(Mat mat) {
        if (mat.type() != opencv_core.CV_8UC1) {
            throw new IllegalArgumentException("wrong Mat type: " + mat.type());
        }
        final int w = mat.cols();
        final int h = mat.rows();
        ByteProcessor bp = new ByteProcessor(w, h);
        mat.data().get((byte[]) bp.getPixels());
        return bp;
    }

    private static ShortProcessor makeShortProcessor(Mat mat) {
        if (mat.type() != opencv_core.CV_16UC1) {
            throw new IllegalArgumentException("wrong Mat type: " + mat.type());
        }
        final int w = mat.cols();
        final int h = mat.rows();
        ShortProcessor sp = new ShortProcessor(w, h);
        ShortPointer sptr = new ShortPointer(mat.data());
        sptr.get((short[]) sp.getPixels());
        sptr.close();
        return sp;
    }

    private static FloatProcessor makeFloatProcessor(Mat mat) {
        if (mat.type() != opencv_core.CV_32FC1) {
            throw new IllegalArgumentException("wrong Mat type: " + mat.type());
        }
        final int w = mat.cols();
        final int h = mat.rows();
        FloatProcessor fp = new FloatProcessor(w, h);
        FloatPointer fptr = new FloatPointer(mat.data());
        fptr.get((float[]) fp.getPixels());
        fptr.close();
        return fp;
    }

    private static ColorProcessor makeColorProcessor(Mat mat) {
        if (mat.type() != opencv_core.CV_8UC3) {
            throw new IllegalArgumentException("wrong Mat type: " + mat.type());
        }
        final int w = mat.cols();
        final int h = mat.rows();
        byte[] pixels = new byte[w * h * (int) mat.elemSize()];
        mat.data().get(pixels);
        // convert byte array to int-encoded RGB values
        ColorProcessor cp = new ColorProcessor(w, h);
        int[] iData = (int[]) cp.getPixels();
        for (int i = 0; i < w * h; i++) {
            int red = pixels[i * 3 + 0] & 0xff;
            int grn = pixels[i * 3 + 1] & 0xff;
            int blu = pixels[i * 3 + 2] & 0xff;
            iData[i] = (red << 16) | (grn << 8) | blu;
        }
        return cp;
    }

}
