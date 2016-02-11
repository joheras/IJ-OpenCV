/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ijopencv;

/**
 *
 * @author jonathan
 * @param <ImageJ>
 * @param <OpenCV>
 */
public interface IConverter<ImageJ,OpenCV> {
    
    OpenCV convertTo(ImageJ I);
    ImageJ convertFrom(OpenCV O);
    boolean canConvertTo(ImageJ I);
    boolean canConvertFrom(OpenCV o);
   
}
