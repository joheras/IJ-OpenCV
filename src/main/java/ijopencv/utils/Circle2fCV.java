/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ijopencv.utils;

import org.bytedeco.javacpp.opencv_core;

/**
 *
 * @author jonathan
 */
public class Circle2fCV {
    
    private final opencv_core.Point2f center;
    private final float radius;

    public Circle2fCV(opencv_core.Point2f center, float radius) {
        this.center = center;
        this.radius = radius;
    }

    public opencv_core.Point2f getCenter() {
        return center;
    }

    public float getRadius() {
        return radius;
    }
    
}
