/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ijopencv;

import net.imagej.Dataset;
import net.imagej.ImageJService;
import org.bytedeco.javacpp.opencv_core.Mat;

/**
 *
 * @author joheras
 */
public interface ImageJOpenCVService extends ImageJService {
    
    
    /**
     * Converts a {@link Dataset} to a {@link Mat} which can then
     * be safely passed to OpenCV.
     */
    Mat getMat(Dataset dataset);
    
    /**
    * Converts a {@link Mat} retrieved from OpenCV to an
    * {@link Dataset}.
    */
    Dataset getDataset(Mat mat);
    
    
}
