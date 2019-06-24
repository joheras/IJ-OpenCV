#@ ImagePlus imp

from ijopencv.ij     import ImagePlusMatConverter as ImpToMat
from ijopencv.opencv import MatImagePlusConverter as MatToImp
# I - Convert the ImagePlus to an opencv matrix

ImCV   = ImpToMat.toMat(imp)
ImCV8  = ImpToMat.toMat(imp, 8)
ImCV16 = ImpToMat.toMat(imp, 16)
ImCV32 = ImpToMat.toMat(imp, 32)

print "\nConvert ImagePlus->Mat"
print ImCV
print ImCV8
print ImCV16
print ImCV32

# II - Convert the ImageProcessor to an opencv matrix

ImCVbis   = ImpToMat.toMat(imp.getProcessor())
ImCV8bis  = ImpToMat.toMat(imp.getProcessor(), 8)
ImCV16bis = ImpToMat.toMat(imp.getProcessor(), 16)
ImCV32bis = ImpToMat.toMat(imp.getProcessor(), 32)

print "\nConvert ImageProcessor->Mat"
print ImCVbis
print ImCV8bis
print ImCV16bis
print ImCV32bis


# II - Convert back Mat to Imp
Ip     = MatToImp.toImageProcessor(ImCV8) 
Imp    = MatToImp.toImagePlus(ImCV8)
ImpBis = MatToImp.toImagePlus(ImCV8, "Converted")

print "\nConvert Mat->ImageProcessor, ->ImagePlus"
print Ip
print Imp
print ImpBis