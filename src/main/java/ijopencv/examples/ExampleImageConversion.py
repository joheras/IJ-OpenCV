###
# #%L
# A library that allows the connection of ImageJ and OpenCV.
# %%
# Copyright (C) 2017 - 2021 University of La Rioja
# %%
# Redistribution and use in source and binary forms, with or without
# modification, are permitted provided that the following conditions are met:
# 
# 1. Redistributions of source code must retain the above copyright notice,
#    this list of conditions and the following disclaimer.
# 2. Redistributions in binary form must reproduce the above copyright notice,
#    this list of conditions and the following disclaimer in the documentation
#    and/or other materials provided with the distribution.
# 
# THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
# AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
# IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
# ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
# LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
# CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
# SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
# INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
# CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
# ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
# POSSIBILITY OF SUCH DAMAGE.
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
