/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ijopencv;

import ij.ImagePlus;
import java.awt.image.BufferedImage;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;
import static org.bytedeco.javacpp.opencv_core.merge;
import static org.bytedeco.javacpp.opencv_core.split;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;

/**
 *
 * @author jonathan
 */
public class ImageConverter implements IConverter<ImagePlus, Mat> {

    @Override
    public Mat convertTo(ImagePlus imp) {
        // Converter ImageJ image to Frame
        Java2DFrameConverter converterToFrame = new Java2DFrameConverter();
        // To covert an ImageJ image to Frame, we must obtain the BufferedImage
        BufferedImage bi = imp.getBufferedImage();
        // Actual conversion
        Frame frame = converterToFrame.convert(bi);

        // Convert Frame to OpenCV Mat
        OpenCVFrameConverter.ToMat converterToMat = new OpenCVFrameConverter.ToMat();
        opencv_core.Mat img = converterToMat.convert(frame);
        // OpenCV expects a BGR image, but it is actually an RGB image, so we 
        // must split the channels and merge them in the correct order

        /* We should check if the image is in rgb or in grayscale, but that 
         remains as further work 
         */
        if (imp.getType() == ImagePlus.COLOR_RGB) { // RGB image

            opencv_core.MatVector rgb = new opencv_core.MatVector(3);
            split(img, rgb);
            opencv_core.Mat img2 = new opencv_core.Mat();

            opencv_core.Mat[] bgrarray = {rgb.get(3), rgb.get(2), rgb.get(1)};
            opencv_core.MatVector bgr = new opencv_core.MatVector(bgrarray);

            merge(bgr, img2);

            return img2;
        } else {
            return img;
        }
    }

    @Override
    public ImagePlus convertFrom(Mat image) {
        // Converter to OpenCV Mat
        OpenCVFrameConverter.ToMat converterToMat = new OpenCVFrameConverter.ToMat();
        // Converter ImageJ image to Frame
        Java2DFrameConverter converterToFrame = new Java2DFrameConverter();

        Frame frame = converterToMat.convert(image);
        BufferedImage bf = converterToFrame.convert(frame);

        return new ImagePlus("image", bf);
    }

    @Override
    public boolean canConvertTo(ImagePlus I) {
        return true;
    }

    @Override
    public boolean canConvertFrom(Mat o) {
        return true;
    }

   
    
}
