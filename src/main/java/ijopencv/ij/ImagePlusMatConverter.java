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
package ijopencv.ij;

import ij.ImagePlus;
import ij.process.ByteProcessor;
import ij.process.ColorProcessor;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;
import ij.process.ShortProcessor;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.FloatPointer;
import org.bytedeco.javacpp.ShortPointer;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;
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
public class ImagePlusMatConverter extends AbstractConverter< ImagePlus, Mat> {

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
        ImagePlus imp = (ImagePlus) o;
        return (T) toMat(imp.getProcessor());

    }

    @Override
    public Class< Mat> getOutputType() {
        return Mat.class;
    }

    @Override
    public Class< ImagePlus> getInputType() {
        return ImagePlus.class;
    }

    // --------------------------------------------------------------------
    // ImageJ -> OpenCV (ImageProcessor -> Mat)
    // --------------------------------------------------------------------
    /**
     * Dispatcher method. Duplicates {@link ImageProcessor} to the corresponding
     * OpenCV image of type {@link Mat}. TODO: Could be coded more elegantly ;-)
     *
     * @param ip The {@link ImageProcessor} to be converted
     * @return The OpenCV image (of type {@link Mat})
     */
    public static Mat toMat(ImageProcessor ip) {
        Mat mat = null;
        if (ip instanceof ByteProcessor) {
            mat = toMat((ByteProcessor) ip);
        } else if (ip instanceof ColorProcessor) {
            mat = toMat((ColorProcessor) ip);
        } else if (ip instanceof ShortProcessor) {
            mat = toMat((ShortProcessor) ip);
        } else if (ip instanceof FloatProcessor) {
            mat = toMat((FloatProcessor) ip);
        } else {
            throw new IllegalArgumentException("cannot convert to Mat: " + ip);
        }
        return mat;
    }
    
    
    /**
     * Convert the {@link ImageProcessor} to a given bitDepth and then
     * to an OpenCV image of type {@link Mat}
     *
     * @param ip The {@link ImageProcessor} to be converted
     * @param bitDepth Target bit depth
     * @return The OpenCV image (of type {@link Mat})
     */
    public static Mat toMat(ImageProcessor ip, int bitDepth) {
    	if      (bitDepth==8)   ip = ip.convertToByteProcessor();
    	else if (bitDepth==16)  ip = ip.convertToShortProcessor();
    	else if (bitDepth==32)  ip = ip.convertToFloatProcessor();
    	else throw new IllegalArgumentException("bitDepth can only be 8, 16 or 32");
    	Mat mat = toMat(ip);
        return mat;
    }
    

    /**
     * Duplicates {@link ByteProcessor} to the corresponding OpenCV image of
     * type {@link Mat}.
     *
     * @param bp The {@link ByteProcessor} to be converted
     * @return The OpenCV image (of type {@link Mat})
     */
    public static Mat toMat(ByteProcessor bp) {
        final int w = bp.getWidth();
        final int h = bp.getHeight();
        final byte[] pixels = (byte[]) bp.getPixels();

        // version A - copies the pixel data to a new array
//		Size size = new Size(w, h);
//		Mat mat = new Mat(size, opencv_core.CV_8UC1);
//		mat.data().put(bData);
        // version 2 - reuses the existing pixel array
        return new Mat(h, w, opencv_core.CV_8UC1, new BytePointer(pixels));
    }

    /**
     * Duplicates {@link ShortProcessor} to the corresponding OpenCV image of
     * type {@link Mat}.
     *
     * @param sp The {@link ShortProcessor} to be converted
     * @return The OpenCV image (of type {@link Mat})
     */
    public static Mat toMat(ShortProcessor sp) {
        final int w = sp.getWidth();
        final int h = sp.getHeight();
        final short[] pixels = (short[]) sp.getPixels();
        return new Mat(h, w, opencv_core.CV_16UC1, new ShortPointer(pixels));
    }

    /**
     * Duplicates {@link FloatProcessor} to the corresponding OpenCV image of
     * type {@link Mat}.
     *
     * @param cp The {@link FloatProcessor} to be converted
     * @return The OpenCV image (of type {@link Mat})
     */
    public static Mat toMat(FloatProcessor cp) {
        final int w = cp.getWidth();
        final int h = cp.getHeight();
        final float[] pixels = (float[]) cp.getPixels();
        return new Mat(h, w, opencv_core.CV_32FC1, new FloatPointer(pixels));
    }

    /**
     * Duplicates {@link ColorProcessor} to the corresponding OpenCV image of
     * type {@link Mat}.
     *
     * @param cp The {@link ColorProcessor} to be converted
     * @return The OpenCV image (of type {@link Mat})
     */
    public static Mat toMat(ColorProcessor cp) {
        final int w = cp.getWidth();
        final int h = cp.getHeight();
        final int[] pixels = (int[]) cp.getPixels();
        byte[] bData = new byte[w * h * 3];

        // convert int-encoded RGB values to byte array
        for (int i = 0; i < pixels.length; i++) {
            bData[i * 3 + 0] = (byte) ((pixels[i] >> 16) & 0xFF);	// red
            bData[i * 3 + 1] = (byte) ((pixels[i] >> 8) & 0xFF);	// grn
            bData[i * 3 + 2] = (byte) ((pixels[i]) & 0xFF);	// blu
        }
        return new Mat(h, w, opencv_core.CV_8UC3, new BytePointer(bData));
    }
    
    /**
     * Convert an {@link ImagePlus} image to an OpenCV image of type {@link Mat}
     *
     * @param imp The {@link ImagePlus} to be converted
     * @return The OpenCV image (of type {@link Mat})
     */
    public static Mat toMat(ImagePlus imp) {
    	ImageProcessor ip = imp.getProcessor();
    	Mat mat = toMat(ip);
        return mat;
    }
    
    /**
     * Convert an {@link ImagePlus} image to a different bitDepth and then to an OpenCV image of type {@link Mat}
     *
     * @param imp The {@link ImagePlus} to be converted
     * @param bitDepth The target bitDepth
     * @return The OpenCV image (of type {@link Mat})
     */
    public static Mat toMat(ImagePlus imp, int bitDepth) {
    	ImageProcessor ip = imp.getProcessor();
    	Mat mat = toMat(ip, bitDepth);
        return mat;
    }
    
    

}
