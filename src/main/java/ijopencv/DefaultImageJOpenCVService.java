/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ijopencv;


import ij.ImagePlus;
import java.awt.image.BufferedImage;
import net.imagej.Dataset;
import net.imagej.DatasetService;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.service.AbstractService;
import org.scijava.service.Service;

/**
 *
 * @author joheras
 */
@Plugin(type = Service.class)
public class DefaultImageJOpenCVService extends AbstractService implements ImageJOpenCVService{

    @Parameter
    private DatasetService datasetService;
    
    @Override
    public opencv_core.Mat getMat(Dataset dataset) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Dataset getDataset(opencv_core.Mat mat) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
       
    }

    
}
