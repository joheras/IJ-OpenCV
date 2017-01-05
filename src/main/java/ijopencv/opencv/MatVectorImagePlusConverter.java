package
 ijopencv.opencv;
 import org.scijava.Prioritized;import org.scijava.Priority;import org.scijava.convert.AbstractConverter;import org.scijava.convert.Converter;import org.scijava.log.LogService;import org.scijava.plugin.Plugin; @Plugin(type=Converter. class , priority = Priority.LOW_PRIORITY)
public class MatVectorImagePlusConverter extends AbstractConverter < MatVector, ImagePlus > { 
 @ Override 
 public int compareTo(Prioritized o) { return super.compareTo(o);}@Override public LogService log(){return super.log();}@Override public String getIdentifier(){return super.getIdentifier();}@Override public < T > T convert(Object o, Class < T > type) {}@Override public Class < ImagePlus > getOutputType() { return ImagePlus.class;}@Override public Class < MatVector > getInputType() { return MatVector.class;}}