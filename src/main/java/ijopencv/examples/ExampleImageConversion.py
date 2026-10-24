###
# #%L
# A library that allows the connection of ImageJ and OpenCV.
# %%
# Copyright (C) 2017 - 2026 University of La Rioja
# %%
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as
# published by the Free Software Foundation, either version 3 of the
# License, or (at your option) any later version.
# 
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
# 
# You should have received a copy of the GNU General Public
# License along with this program.  If not, see
# <http://www.gnu.org/licenses/gpl-3.0.html>.
# #L%
###
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
