/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ijopencv;

import ij.ImagePlus;
import ij.ImageStack;
import ij.gui.EllipseRoi;
import ij.gui.OvalRoi;
import ij.gui.PointRoi;
import ij.gui.PolygonRoi;
import ij.gui.Roi;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.KeyPoint;
import org.bytedeco.javacpp.opencv_core.KeyPointVector;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Point2d;
import org.bytedeco.javacpp.opencv_core.Point2dVector;
import org.bytedeco.javacpp.opencv_core.Point2f;
import org.bytedeco.javacpp.opencv_core.Point2fVector;
import org.bytedeco.javacpp.opencv_core.PointVector;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.RectVector;
import org.bytedeco.javacpp.opencv_core.Rectd;
import org.bytedeco.javacpp.opencv_core.Rectf;
import org.bytedeco.javacpp.opencv_core.Scalar;
import static org.bytedeco.javacpp.opencv_core.merge;
import static org.bytedeco.javacpp.opencv_core.split;
import org.bytedeco.javacpp.opencv_imgproc;
import static org.bytedeco.javacpp.opencv_imgproc.minAreaRect;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;

/**
 *
 * @author jonathan
 */
public class IJ2OpenCV {

//    public static Mat ImageJImage2OpenCVImage(ImagePlus imp) { 
//
//        // Converter ImageJ image to Frame
//        Java2DFrameConverter converterToFrame = new Java2DFrameConverter();
//        // To covert an ImageJ image to Frame, we must obtain the BufferedImage
//        BufferedImage bi = imp.getBufferedImage();
//        // Actual conversion
//        Frame frame = converterToFrame.convert(bi);
//
//        // Convert Frame to OpenCV Mat
//        OpenCVFrameConverter.ToMat converterToMat = new OpenCVFrameConverter.ToMat();
//        opencv_core.Mat img = converterToMat.convert(frame);
//        // OpenCV expects a BGR image, but it is actually an RGB image, so we 
//        // must split the channels and merge them in the correct order
//
//        /* We should check if the image is in rgb or in grayscale, but that 
//         remains as further work 
//         */
//        if (imp.getType() == ImagePlus.COLOR_RGB) { // RGB image
//
//            opencv_core.MatVector rgb = new opencv_core.MatVector(3);
//            split(img, rgb);
//            opencv_core.Mat img2 = new opencv_core.Mat();
//
//            opencv_core.Mat[] bgrarray = {rgb.get(3), rgb.get(2), rgb.get(1)};
//            opencv_core.MatVector bgr = new opencv_core.MatVector(bgrarray);
//
//            merge(bgr, img2);
//
//            return img2;
//        } else {
//            return img;
//        }
//
//    }

//    public static Mat ImageJPolygonROI2OpenCVMat(PolygonRoi pr) {
//        Mat mx = new Mat(pr.getPolygon().xpoints);
//        Mat my = new Mat(pr.getPolygon().ypoints);
//        opencv_core.MatVector mxy = new opencv_core.MatVector(2);
//        mxy.put(0, mx);
//        mxy.put(1, my);
//        Mat m = new Mat();
//        merge(mxy, m);
//        return m;
//
//    }

//    public static Mat ImageJArrayListOvalRoi2OpenCVMat(ArrayList<OvalRoi> alor) throws Exception {
//
//        float[] xpoints = new float[alor.size()];
//        float[] ypoints = new float[alor.size()];
//        float[] radiuses = new float[alor.size()];
//
//        for (int i = 0; i < alor.size(); i++) {
//            Rectangle rect = alor.get(i).getBounds();
//            if (rect.width != rect.height) {
//                throw new Exception("All the elements of the array must be circles");
//            }
//            Circle c = new Circle(new Point(rect.x + rect.width / 2, rect.y + rect.height / 2), rect.width / 2);
//
//            xpoints[i] = rect.x + rect.width / 2;
//            ypoints[i] = rect.y + rect.height / 2;
//            radiuses[i] = rect.width / 2;
//        }
//
//        Mat mx = new Mat(xpoints);
//        Mat my = new Mat(ypoints);
//        Mat mr = new Mat(radiuses);
//        opencv_core.MatVector mxyr = new opencv_core.MatVector(3);
//        mxyr.put(0, mx);
//        mxyr.put(1, my);
//        mxyr.put(2, mr);
//        Mat m = new Mat();
//        merge(mxyr, m);
//        return m;
//
//    }

//    public static Mat ImageJPointRoi2OpenCVMat(PointRoi pr) {
//        int[] xpoints = pr.getPolygon().xpoints;
//        int[] ypoints = pr.getPolygon().ypoints;
//        Mat mx = new Mat(xpoints);
//        Mat my = new Mat(ypoints);
//        opencv_core.MatVector mxy = new opencv_core.MatVector(2);
//        mxy.put(0, mx);
//        mxy.put(1, my);
//        Mat m = new Mat();
//        merge(mxy, m);
//        return m;
//    }

//    public static Circle ImageJOvalROI2OpenCVCircle(OvalRoi or) throws Exception {
//        Rectangle rect = or.getPolygon().getBounds();
//        if (rect.width != rect.height) {
//            throw new Exception("The oval must be a circle");
//        }
//        Circle c = new Circle(new Point(rect.x + rect.width / 2, rect.y + rect.height / 2), rect.width / 2);
//        return c;
//    }

//    public static Rect ImageJROI2OpenCVRect(Roi r) {
//        Rectangle r1 = r.getBounds();
//        Rect rect = new Rect(r1.x, r1.y, r1.width, r1.height);
//        return rect;
//    }

//    public static Rectd ImageJROI2OpenCVRectd(Roi r) {
//        Rectangle r1 = r.getBounds();
//        Rectd rect = new Rectd(r1.x, r1.y, r1.width, r1.height);
//        return rect;
//    }
//
//    public static Rectf ImageJROI2OpenCVRectf(Roi r) {
//        Rectangle r1 = r.getBounds();
//        Rectf rect = new Rectf(r1.x, r1.y, r1.width, r1.height);
//        return rect;
//    }

//    public static Point ImageJPointROI2OpenCVPoint(PointRoi pr) throws Exception {
//
//        if (pr.getType() != Roi.POINT) {
//            throw new Exception("You must provide a point and not a list of points");
//        }
//
//        Point p = new Point(pr.getXCoordinates()[0], pr.getYCoordinates()[0]);
//        return p;
//
//    }

//    public static Point2f ImageJPointROI2OpenCVPoint2f(PointRoi pr) throws Exception {
//        if (pr.getType() != Roi.POINT) {
//            throw new Exception("You must provide a point and not a list of points");
//        }
//        Point2f p = new Point2f(pr.getXCoordinates()[0], pr.getYCoordinates()[0]);
//        return p;
//    }

//    public static Point2d ImageJPointROI2OpenCVPoint2d(PointRoi pr) throws Exception {
//        if (pr.getType() != Roi.POINT) {
//            throw new Exception("You must provide a point and not a list of points");
//        }
//        Point2d p = new Point2d(pr.getXCoordinates()[0], pr.getYCoordinates()[0]);
//        return p;
//    }

//    public static opencv_core.KeyPoint ImageJPointROI2OpenCVKeyPoint(PointRoi pr) throws Exception {
//        if (pr.getType() != Roi.POINT) {
//            throw new Exception("You must provide a point and not a list of points");
//        }
//        opencv_core.KeyPoint p = new KeyPoint(pr.getXCoordinates()[0], pr.getYCoordinates()[0], 1);
//        return p;
//    }

//    public static PointVector ImageJPointROI2OpenCVPointVector(PointRoi pr) {
//
//        PointVector pv = new PointVector();
//        int[] xpoints = pr.getPolygon().xpoints;
//        int[] ypoints = pr.getPolygon().ypoints;
//
//        for (int i = 0; i < xpoints.length; i++) {
//            pv.put(i, new Point(xpoints[i], ypoints[i]));
//        }
//        return pv;
//    }

//    public static Point2fVector ImageJPointROI2OpenCVPoint2fVector(PointRoi pr) {
//
//        Point2fVector pv = new Point2fVector();
//        int[] xpoints = pr.getPolygon().xpoints;
//        int[] ypoints = pr.getPolygon().ypoints;
//
//        for (int i = 0; i < xpoints.length; i++) {
//            pv.put(i, new Point2f(xpoints[i], ypoints[i]));
//        }
//        return pv;
//    }

//    public static Point2dVector ImageJPointROI2OpenCVPoint2dVector(PointRoi pr) {
//
//        Point2dVector pv = new Point2dVector();
//        int[] xpoints = pr.getPolygon().xpoints;
//        int[] ypoints = pr.getPolygon().ypoints;
//
//        for (int i = 0; i < xpoints.length; i++) {
//            pv.put(i, new Point2d(xpoints[i], ypoints[i]));
//        }
//        return pv;
//    }
//
//    public static KeyPointVector ImageJPointROI2OpenCVKeyPointVectorVector(PointRoi pr) {
//
//        KeyPointVector pv = new KeyPointVector();
//        int[] xpoints = pr.getPolygon().xpoints;
//        int[] ypoints = pr.getPolygon().ypoints;
//
//        for (int i = 0; i < xpoints.length; i++) {
//            pv.put(i, new KeyPoint(xpoints[i], ypoints[i], 1));
//        }
//        return pv;
//    }

//    public static Point2f ImageJPolygonROI2Point2f(PolygonRoi pr) {
//        Point2f res = new Point2f();
//        int[] xpoints = pr.getPolygon().xpoints;
//        int[] ypoints = pr.getPolygon().ypoints;
//        for (int i = 0; i < xpoints.length; i++) {
//            Point2f p = new Point2f(xpoints[i], ypoints[i]);
//            res.put(p);
//        }
//        return res;
//    }



//    public static opencv_core.RotatedRect ImageJEllipseRoi2OpenCVRotatedRect(EllipseRoi er) {
//        Mat points = ImageJPolygonROI2OpenCVMat(er);
//        opencv_core.RotatedRect rr = opencv_imgproc.minAreaRect(points);
//        return rr;
//    }

//    public static opencv_core.RotatedRect ImageJOvalRoi2OpenCVRotatedRect(OvalRoi or) {
//        Rectangle rect = or.getPolygon().getBounds();
//
//        int[] xpoints = {rect.x, rect.x, rect.x + rect.width, rect.x + rect.width};
//        int[] ypoints = {rect.y, rect.y + rect.height, rect.y + rect.height, rect.y};
//        Mat x = new Mat(xpoints);
//        Mat y = new Mat(ypoints);
//        MatVector xy = new opencv_core.MatVector(2);
//        Mat points = new Mat();
//        merge(xy, points);
//        return minAreaRect(points);
//    }

//    public static Scalar ImageJColor2OpenCVScalar(Color c) {
//
//        Scalar s = org.bytedeco.javacpp.helper.opencv_core.RGB(c.getRed(), c.getGreen(), c.getBlue());
//        return s;
//    }

//    public static RectVector ImageJArrayListRoi2OpenCVRectVector(ArrayList<Roi> aroi) {
//        RectVector rv = new RectVector();
//        for (int i = 0; i < aroi.size(); i++) {
//            rv.put(i, ImageJROI2OpenCVRect(aroi.get(i)));
//        }
//
//        return rv;
//    }

//    public static MatVector ImageJArrayListPolygonRoi2OpenCVMatVector(ArrayList<PolygonRoi> aproi) {
//
//        MatVector mv = new MatVector();
//
//        for (int i = 0; i < aproi.size(); i++) {
//            mv.put(i, ImageJPolygonROI2OpenCVMat(aproi.get(i)));
//        }
//        return mv;
//    }
       
    
    
//    public static MatVector ImageJImageStack2OpenCVMatVector(ImagePlus imp){
//        ImageStack is = imp.getImageStack();
//        MatVector mv = new MatVector(is.getSize());
//        ImagePlus imp1;
//        
//        for(int i =0;i<is.getSize();i++){
//            imp1 = new ImagePlus(""+i, is.getProcessor(i));
//            mv.put(i,ImageJImage2OpenCVImage(imp1));
//        }
//        
//        return mv;
//    
//    
//    }

}
