/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ijopencv;

import org.bytedeco.javacpp.opencv_core.*;

/**
 *
 * @author jonathan
 */
public class CircleCV {
    
    private final Point center;
    private final int radius;

    public CircleCV(Point center, int radius) {
        this.center = center;
        this.radius = radius;
    }

    public Point getCenter() {
        return center;
    }

    public int getRadius() {
        return radius;
    }
    
    
    
    
}
