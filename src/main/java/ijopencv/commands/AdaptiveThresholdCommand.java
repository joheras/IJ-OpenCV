/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ijopencv.commands;

import java.io.File;
import net.imagej.Dataset;
import net.imagej.ImageJ;
import org.bytedeco.javacpp.opencv_core.Mat;
import static org.bytedeco.javacpp.opencv_imgproc.ADAPTIVE_THRESH_GAUSSIAN_C;
import static org.bytedeco.javacpp.opencv_imgproc.THRESH_BINARY;
import static org.bytedeco.javacpp.opencv_imgproc.adaptiveThreshold;
import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 *
 * @author joheras
 */



@Plugin(type = Command.class, menuPath = "Plugins>IJ-OpenCV>Adaptive Threshold")
public class AdaptiveThresholdCommand implements Command{
    
    @Parameter
    protected Mat image;
    
    @Parameter(type = ItemIO.OUTPUT)
    protected Dataset output;
    
    @Parameter
    protected int maxValue=255;
    
    @Parameter
    protected int blockSize=3;


    @Override
    public void run() {
        
        
        
        adaptiveThreshold(image, image, maxValue, ADAPTIVE_THRESH_GAUSSIAN_C, THRESH_BINARY, blockSize, 2);
        
    }
    
    
    public static void main(final String... args) throws Exception {
        // create the ImageJ application context with all available services
        final ImageJ ij = new ImageJ();
        ij.ui().showUI();

        // ask the user for a file to open
        final File file = ij.ui().chooseFile(null, "open");

        if (file != null) {
            // load the dataset
            final Dataset dataset = ij.scifio().datasetIO().open(file.getPath());

            // show the image
            ij.ui().show(dataset);

            // invoke the plugin
            ij.command().run(AdaptiveThresholdCommand.class, true);
        }
}
    
    
}
