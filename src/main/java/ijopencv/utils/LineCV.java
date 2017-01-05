/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ijopencv.utils;

/**
 *
 * @author jonathan
 */
public class LineCV {
    private final float rho;
    private final float theta;

    public LineCV(float rho, float theta) {
        this.rho = rho;
        this.theta = theta;
    }

    public float getRho() {
        return rho;
    }

    public float getTheta() {
        return theta;
    }
    
    
    
}
