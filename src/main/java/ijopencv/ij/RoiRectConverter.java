package
 ijopencv.ij;
import org.scijava.Prioritized;import org.scijava.Priority;import org.scijava.convert.AbstractConverter;import org.scijava.convert.Converter;import org.scijava.log.LogService;import org.scijava.plugin.Plugin;@Plugin(type=Converter. class , priority = Priority.LOW_PRIORITY)
public class RoiRectConverter extends AbstractConverter < Roi, Rect > { 
 @ Override 
 public int compareTo(Prioritized o) { return super.compareTo(o);}@Override public LogService log(){return super.log();}@Override public String getIdentifier(){return super.getIdentifier();}@Override public < T > T convert(Object o, Class < T > type) {}@Override public Class < Rect > getOutputType() { return Rect.class;}@Override public Class < Roi > getInputType() { return Roi.class;}}