/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ijopencv;

import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.gui.Line;
import ij.gui.OvalRoi;
import ij.gui.PointRoi;
import ij.gui.PolygonRoi;
import ij.gui.Roi;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.ArrayList;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Point2d;
import org.bytedeco.javacpp.opencv_core.Point2f;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.RectVector;
import org.bytedeco.javacpp.opencv_core.Rectd;
import org.bytedeco.javacpp.opencv_core.Rectf;
import org.bytedeco.javacpp.opencv_core.Scalar;
import static org.bytedeco.javacpp.opencv_core.cvRound;
import static org.bytedeco.javacpp.opencv_core.split;
import org.bytedeco.javacpp.opencv_imgproc;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;

/**
 *
 * @author jonathan
 */
public class OpenCV2IJ {

//    public static ImagePlus OpenCVImage2ImageJImage(Mat image) {
//
//        // Converter to OpenCV Mat
//        OpenCVFrameConverter.ToMat converterToMat = new OpenCVFrameConverter.ToMat();
//        // Converter ImageJ image to Frame
//        Java2DFrameConverter converterToFrame = new Java2DFrameConverter();
//
//        Frame frame = converterToMat.convert(image);
//        BufferedImage bf = converterToFrame.convert(frame);
//
//        return new ImagePlus("image", bf);
//    }

//    public static Roi OpenCVRect2ImageJROI(Rect rect) {
//        Roi r = new Roi(rect.x(), rect.y(), rect.width(), rect.height());
//        return r;
//    }

//    public static Roi OpenCVRectd2ImageJROI(Rectd rect) {
//        Roi r = new Roi(rect.x(), rect.y(), rect.width(), rect.height());
//        return r;
//    }
//
//    public static Roi OpenCVRectf2ImageJROI(Rectf rect) {
//        Roi r = new Roi(rect.x(), rect.y(), rect.width(), rect.height());
//        return r;
//    }

//    public static Color OpenCVScalar2ImageJColor(Scalar s) {
//        Color c = new Color((int) s.red(), (int) s.green(), (int) s.blue());
//        return c;
//    }

//    public static ArrayList<Roi> OpenCVRectVector2ImageJArrayListRoi(RectVector rv) {
//        ArrayList<Roi> rois = new ArrayList<>();
//
//        for (int i = 0; i < rv.size(); i++) {
//            rois.add(OpenCVRect2ImageJROI(rv.get(i)));
//        }
//
//        return rois;
//
//    }

//    public static OvalRoi OpenCVCircle2ImageJOvalROI(Circle c) {
//        OvalRoi or = new OvalRoi(c.getCenter().x() - c.getRadius(),
//                c.getCenter().y() - c.getRadius(), c.getRadius() * 2, c.getRadius() * 2);
//        return or;
//
//    }

    // Polygons are stored in (Java) OpenCV as matrices with 2 channels, width
    // equal to 1 and height equal to the number of points.
    // This can be seen for instance with the function ConvexHull or findContours
//    public static PolygonRoi OpenCVMat2PolygonROI(opencv_core.Mat m) {
//        opencv_core.MatVector xy = new opencv_core.MatVector(2);
//        split(m, xy);
//        int h = m.size().height();
//        int[] xpoints = new int[h];
//        int[] ypoints = new int[h];
//
//        for (int i = 0; i < h; i++) {
//            xpoints[i] = xy.get(0).getIntBuffer().get(i);
//            ypoints[i] = xy.get(1).getIntBuffer().get(i);
//        }
//
//        PolygonRoi pr = new PolygonRoi(xpoints, ypoints, h, Roi.POLYGON);
//        return pr;
//    }

//    public static PointRoi OpenCVMat2PointROI(opencv_core.Mat m) {
//        opencv_core.MatVector xy = new opencv_core.MatVector(2);
//        split(m, xy);
//        int h = m.rows();
//        int[] xpoints = new int[h];
//        int[] ypoints = new int[h];
//
//        for (int i = 0; i < h; i++) {
//            xpoints[i] = xy.get(0).getIntBuffer().get(i);
//            ypoints[i] = xy.get(1).getIntBuffer().get(i);
//        }
//
//        PointRoi pr = new PointRoi(ypoints, ypoints, h);
//        return pr;
//    }

    // For instance, a MatVector is used to store the result in findContours
//    public static ArrayList<PolygonRoi> OpenCVMatVector2ArrayListPolygonROI(opencv_core.MatVector mv) {
//        ArrayList<PolygonRoi> alproi = new ArrayList<>();
//        for (int i = 0; i < mv.size(); i++) {
//            alproi.add(OpenCVMat2PolygonROI(mv.get(i)));
//        }
//        return alproi;
//    }

//    public static PointRoi OpenCVPoint2ImageJPointROI(opencv_core.Point p) {
//        PointRoi pr = new PointRoi(p.x(), p.y());
//        return pr;
//    }

//    public static PointRoi OpenCVPoint2f2ImageJPointROI(Point2f p) throws Exception {
//        if (p.sizeof() != 2) {
//            throw new Exception("The point2f should only have two values");
//        }
//        PointRoi pr = new PointRoi(p.x(), p.y());
//        return pr;
//    }

//    public static PointRoi OpenCVPoint2d2ImageJPointROI(Point2d p) throws Exception {
//        if (p.sizeof() != 2) {
//            throw new Exception("The point2f should only have two values");
//        }
//        PointRoi pr = new PointRoi(p.x(), p.y());
//        return pr;
//    }

//    public static PointRoi OpenCVKeyPoint2ImageJPointROI(opencv_core.KeyPoint p) throws Exception {
//        if (p.sizeof() != 2) {
//            throw new Exception("The point2f should only have two values");
//        }
//        PointRoi pr = new PointRoi(p.pt().x(), p.pt().y());
//        return pr;
//    }

//    public static PointRoi OpenCVPointVector2ImageJPointROI(opencv_core.PointVector pv) {
//        int[] xpoints = new int[(int) pv.size()];
//        int[] ypoints = new int[(int) pv.size()];
//
//        for (int i = 0; i < (int) pv.size(); i++) {
//            xpoints[i] = pv.get(i).x();
//            ypoints[i] = pv.get(i).y();
//        }
//
//        PointRoi pr = new PointRoi(xpoints, ypoints, pv.sizeof());
//        return pr;
//
//    }

//    public static PointRoi OpenCVPoint2fVector2ImageJPointROI(opencv_core.Point2fVector pv) {
//        float[] xpoints = new float[(int) pv.size()];
//        float[] ypoints = new float[(int) pv.size()];
//
//        for (int i = 0; i < (int) pv.size(); i++) {
//            xpoints[i] = pv.get(i).x();
//            ypoints[i] = pv.get(i).y();
//        }
//
//        PointRoi pr = new PointRoi(xpoints, ypoints, pv.sizeof());
//        return pr;
//
//    }

//    public static PointRoi OpenCVPoint2dVector2ImageJPointROI(opencv_core.Point2dVector pv) {
//        float[] xpoints = new float[(int) pv.size()];
//        float[] ypoints = new float[(int) pv.size()];
//
//        for (int i = 0; i < (int) pv.size(); i++) {
//            xpoints[i] = (float) pv.get(i).x();
//            ypoints[i] = (float) pv.get(i).y();
//        }
//        PointRoi pr = new PointRoi(xpoints, ypoints);
//        return pr;
//    }

//    public static PointRoi OpenCVKeyPointVector2ImageJPointROI(opencv_core.KeyPointVector pv) {
//        float[] xpoints = new float[(int) pv.size()];
//        float[] ypoints = new float[(int) pv.size()];
//
//        for (int i = 0; i < (int) pv.size(); i++) {
//            xpoints[i] = (float) pv.get(i).pt().x();
//            ypoints[i] = (float) pv.get(i).pt().y();
//        }
//        PointRoi pr = new PointRoi(xpoints, ypoints);
//        return pr;
//    }

    // For instance, Point2f is used  to store the points of a rotated rectangle
//    public static PolygonRoi OpenCVPoint2f2ImageJPolygonROI(Point2f p) {
//        int[] xpoints = new int[p.sizeof() / 2];
//        int[] ypoints = new int[p.sizeof() / 2];
//
//        for (int i = 0; i < p.sizeof(); i = i + 2) {
//
//            xpoints[i / 2] = (int) p.get(i);
//            ypoints[i / 2] = (int) p.get(i + 1);
//        }
//        PolygonRoi pr = new PolygonRoi(xpoints, ypoints, p.sizeof() / 2, Roi.POLYGON);
//        return pr;
//    }

    // For instance, the function Hough Circles stores the result in an object of
    // type Mat
//    public static ArrayList<OvalRoi> OpenCVMat2OpenCVArrayListOvalRoi(Mat circles) {
//        opencv_core.MatVector xyr = new opencv_core.MatVector(3);
//        split(circles, xyr);
//        int num = circles.size().height();
//
//        ArrayList<OvalRoi> result = new ArrayList<>();
//
//        for (int i = 0; i < num; i++) {
//            int cx = (int) xyr.get(0).getFloatBuffer().get(i);
//            int cy = (int) xyr.get(1).getFloatBuffer().get(i);
//            int r = (int) xyr.get(2).getFloatBuffer().get(i);
//
//            OvalRoi or = new OvalRoi(cx - r, cy - r, r * 2, r * 2);
//            result.add(or);
//        }
//
//        return result;
//
//    }

    // From MinEnclosingCircle
//    public static OvalRoi OpenCVCircle2ImageJOvalROI(Point2f center, float radius) {
//
//        OvalRoi or = new OvalRoi(center.x() - radius, center.y() - radius, radius * 2, radius * 2);
//        return or;
//
//    }

    // From HoughLine
//    public static Line OpenCVRhoTheta2ImageJLine(float rho, float theta) {
//
//        double a = cos(theta);
//        double b = sin(theta);
//        double x0 = a * rho, y0 = b * rho;
//
//        opencv_core.Point pt1 = new opencv_core.Point(cvRound(x0 + 1000 * (-b)), cvRound(y0 + 1000 * (a))),
//                pt2 = new opencv_core.Point(cvRound(x0 - 1000 * (-b)), cvRound(y0 - 1000 * (a)));
//
//        Line line = new Line(pt1.x(), pt1.y(), pt2.x(), pt2.y());
//        return line;
//    }

    // From HoughLine
//    public static ArrayList<Line> OpenCVMat2ImageJArrayListLine(Mat lines) {
//        ArrayList<Line> ijlines = new ArrayList<>();
//        opencv_core.MatVector xy = new opencv_core.MatVector(2);
//        split(lines, xy);
//        for (int i = 0; i < lines.rows(); i++) {
//
//            float rho = xy.get(0).getFloatBuffer().get(i);
//            float theta = xy.get(1).getFloatBuffer().get(i);
//
//            double a = cos(theta);
//            double b = sin(theta);
//            double x0 = a * rho, y0 = b * rho;
//
//            opencv_core.Point pt1 = new opencv_core.Point(cvRound(x0 + 1000 * (-b)), cvRound(y0 + 1000 * (a))),
//                    pt2 = new opencv_core.Point(cvRound(x0 - 1000 * (-b)), cvRound(y0 - 1000 * (a)));
//            Line line = new Line(x0 + 1000 * (-b), y0 + 1000 * (a), x0 - 1000 * (-b), y0 - 1000 * (a));
//            ijlines.add(line);
//        }
//        return ijlines;
//    }

//    public static PolygonRoi OpenCVRotatedRect2ImageJPolygonROI(opencv_core.RotatedRect rr) {
//        double _angle = rr.angle() * Math.PI / 180.0;
//        double b = (double) Math.cos(_angle) * 0.5f;
//        double a = (double) Math.sin(_angle) * 0.5f;
//
//        Point pt0 = new Point(
//                (int) (rr.center().x() - a * rr.size().height() - b * rr.size().width()),
//                (int) (rr.center().y() + b * rr.size().height() - a * rr.size().width()));
//
//        Point pt1 = new Point(
//                (int) (rr.center().x() + a * rr.size().height() - b * rr.size().width()),
//                (int) (rr.center().y() - b * rr.size().height() - a * rr.size().width()));
//
//        Point pt2 = new Point((int) (2 * rr.center().x() - pt0.x()),
//                (int) (2 * rr.center().y() - pt0.y()));
//
//        Point pt3 = new Point((int) (2 * rr.center().x() - pt1.x()),
//                (int) (2 * rr.center().y() - pt1.y()));
//
//        int[] xpoints = new int[4];
//        int[] ypoints = new int[4];
//
//        xpoints[0] = pt0.x();
//        ypoints[0] = pt0.y();
//        xpoints[1] = pt1.x();
//        ypoints[1] = pt1.y();
//        xpoints[2] = pt2.x();
//        ypoints[2] = pt2.y();
//        xpoints[3] = pt3.x();
//        ypoints[3] = pt3.y();
//
//        PolygonRoi pr = new PolygonRoi(xpoints, ypoints, 4, Roi.POLYGON);
//        return pr;
//    }

//    public static ImagePlus OpenCVMatVector2ImageJImageStack(MatVector mv) {
//
//        ImageStack is = new ImageStack();
//        ImagePlus temp;
//        for (int i = 0; i < mv.size(); i++) {
//            temp = OpenCVImage2ImageJImage(mv.get(i));
//            is.addSlice(temp.getProcessor());
//        }
//
//        ImagePlus imp = new ImagePlus("image", is);
//        return imp;
//        
//    
//    
//    
//    }

}
