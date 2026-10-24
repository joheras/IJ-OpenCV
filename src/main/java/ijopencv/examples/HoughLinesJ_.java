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
package ijopencv.examples;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import ij.IJ;
import ij.ImagePlus;
import ij.gui.Line;
import ij.plugin.frame.RoiManager;
import ij.process.ImageProcessor;
import ijopencv.ij.ImagePlusMatConverter;
import ijopencv.opencv.MatListLineConverter;
import java.util.ArrayList;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.javacpp.indexer.FloatIndexer;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Point2f;
import org.bytedeco.opencv.opencv_imgproc.Vec2fVector;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 *
 * @author jonathan
 */
@Plugin(type = Command.class, headless = true, menuPath = "Plugins>IJ-OpenCV-plugins>Hough lines")
public class HoughLinesJ_  implements Command {
			
    @Parameter (validater="checkType")
    private ImagePlus imp;
    
    @Parameter(label="Minimum line length (pixels)", min="1", stepSize="10")
    private int min_length=10;
    
    @Parameter(label="Step_line spacing iteration (pixels)", min="1", stepSize="10")
    private double step_line=5;
    
    @Parameter(label="Start_angle iteration (degrees)", min="0", stepSize="10")
    private double min_theta=0;

    @Parameter(label="Stop_angle iteration (degrees)", min="0", max="180", stepSize="10")
    private double max_theta=180;
    
    @Parameter(label="Step_angle iteration (degrees)", min="1", stepSize="5")
    private double step_theta=5;
    
    /**
     * Function associated to Imp input: check image type
     * Hough line is defined for binary images
     * param imp : input ImagePlus
     */
    protected void checkType() {
    	ImageProcessor ImProc = imp.getProcessor();
    	if ( !ImProc.isBinary() ) {
    		IJ.error("Image must be a 8-bit binary image (0-255 exclusively)");
    		throw new IllegalArgumentException("Image must be a 8-bit binary image (0-255 exclusively)");
    	}
    }
    
    @Override
    public void run() {
 
		// Do line detection
		ArrayList<Line> linesIJ = HoughLines(imp, min_length, step_line, min_theta, max_theta, step_theta);
		
		// Add lines to RoiManager
        RoiManager rm = new RoiManager();
        rm.setVisible(true);

        for (int i = 0; i < linesIJ.size(); i++) {
            rm.add(imp, linesIJ.get(i), i);
	        }
		}
		
	/**
	 * Hough lines detection: Perform line detection on a binary image
	 * param imp        : 8-bit Binary ImagePlus 
	 * param min_length : Minimum number of points lying on a line to return it
	 * param step_line  : Step size (pixels) between parallel lines for iterative line search
	 * param min_theta  : Start looking for lines with orientation corresponding to this Rho angle (degrees)
	 * param max_theta  : Stop looking for lines with orientation corresponding to this Rho angle (degrees)
	 * param step_theta : Angle step size for line search (degrees) 
	 * return
	 */
	public ArrayList<Line> HoughLines(ImagePlus imp, int min_length, double step_line, double min_theta, double max_theta, double step_theta){
        
		// Initialise Imp>Mat Converters
        ImagePlusMatConverter ic  = new ImagePlusMatConverter();
        
		// Convert input imp to Mat
        Mat m = ic.convert(imp, Mat.class);
        
        // Convert from degrees to radians
        min_theta  = Math.toRadians(min_theta);
        max_theta  = Math.toRadians(max_theta);
        step_theta = Math.toRadians(step_theta);
        		
		// Do Hough line detection
        Vec2fVector linesVec = new Vec2fVector();
        opencv_imgproc.HoughLines(m, linesVec, step_line, step_theta, min_length, 0, 0, min_theta, max_theta);

        // Copy into an Nx1 2-channel float Mat, as expected by the converter
        int n = (int) linesVec.size();
        Mat lines = new Mat(n, 1, opencv_core.CV_32FC2);
        FloatIndexer idx = lines.createIndexer();
        for (int i = 0; i < n; i++) {
            Point2f l = linesVec.get(i);
            idx.put(i, 0, 0, l.x());
            idx.put(i, 0, 1, l.y());
        }
        idx.release();
		
		// Convert lines Mat to ArrayList of ij line
		MatListLineConverter lc = new MatListLineConverter();
        ArrayList<Line> linesIJ = new ArrayList<Line>();
        linesIJ = lc.convert(lines, linesIJ.getClass());
        
        return linesIJ;
    }

}
