package
 ijopencv.ij;
import org.scijava.Prioritized;import org.scijava.Priority;import org.scijava.convert.AbstractConverter;import org.scijava.convert.Converter;import org.scijava.log.LogService;import org.scijava.plugin.Plugin;@Plugin(type=Converter. class , priority = Priority.LOW_PRIORITY)
public class PointRoiPoint2fConverter extends AbstractConverter < PointRoi, Point2f > { 
 @ Override 
 public int compareTo(Prioritized o) { return super.compareTo(o);}@Override public LogService log(){return super.log();}@Override public String getIdentifier(){return super.getIdentifier();}@Override public < T > T convert(Object o, Class < T > type) {}@Override public Class < Point2f > getOutputType() { return Point2f.class;}@Override public Class < PointRoi > getInputType() { return PointRoi.class;}}