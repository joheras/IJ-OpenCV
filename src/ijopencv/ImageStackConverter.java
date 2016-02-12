/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ijopencv;

import ij.ImagePlus;
import ij.ImageStack;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.MatVector;

/**
 *
 * @author jonathan
 */
public class ImageStackConverter implements IConverter<ImagePlus, opencv_core.MatVector> {

    @Override
    public opencv_core.MatVector convertTo(ImagePlus imp) {
        ImageStack is = imp.getImageStack();
        MatVector mv = new MatVector(is.getSize());
        ImagePlus imp1;
        ImageConverter ic = new ImageConverter();
        
        for(int i =1;i<=is.getSize();i++){
            imp1 = new ImagePlus(""+i, is.getProcessor(i));
            mv.put(i-1,ic.convertTo(imp1));
        }
        
        
        return mv;
    }

    @Override
    public ImagePlus convertFrom(opencv_core.MatVector mv) {
        ImageStack is = new ImageStack();
        ImagePlus temp;
        ImageConverter ic = new ImageConverter();
        
        for (int i = 0; i < mv.size(); i++) {
            temp = ic.convertFrom(mv.get(i));
            is.addSlice(temp.getProcessor());
        }

        ImagePlus imp = new ImagePlus("image", is);
        return imp;
    }

    @Override
    public boolean canConvertTo(ImagePlus I) {
        return true;
    }

    @Override
    public boolean canConvertFrom(opencv_core.MatVector o) {
        return true;
    }
    
}
