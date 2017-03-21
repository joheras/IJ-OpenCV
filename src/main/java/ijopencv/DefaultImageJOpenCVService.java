/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ijopencv;


import net.imagej.Dataset;
import org.bytedeco.javacpp.opencv_core;
import org.scijava.plugin.Plugin;
import org.scijava.service.AbstractService;
import org.scijava.service.Service;

/**
 *
 * @author joheras
 */
@Plugin(type = Service.class)
public class DefaultImageJOpenCVService extends AbstractService implements ImageJOpenCVService{

    @Override
    public opencv_core.Mat getMat(Dataset dataset) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Dataset getDataset(opencv_core.Mat mat) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
