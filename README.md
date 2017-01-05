IJ-OpenCV is a Java library that allows the communication of ImageJ/Fiji - a software for image analysis that is widely employed in life sciences - and OpenCV - a well-known computer vision library.

There are several benefits provided by the communication of ImageJ/Fiji and OpenCV. From the ImageJ/Fiji perspective, this tool is enhanced with several computer vision and machine learning algorithms, avoiding the re-implementation of methods and the connection with several external libraries. From the OpenCV point of view, this library is improved with a simple-to-use GUI and with the functionality to manage regions of interest.

IJ-OpenCV is free to use and licensed under the license GNU GPL 3.0.

### Download
The [IJ-OpenCV library](https://github.com/joheras/IJ-OpenCV/releases/download/1.0/IJOpenCV.jar).
Plugins developed with the IJ-OpenCV library: [plugins](https://github.com/joheras/IJ-OpenCV/raw/master/Plugins/IJ-OpenCV.zip)

### Dependencies
[ImageJ](http://rsb.info.nih.gov/ij/index.html) or [Fiji](http://fiji.sc/Fiji).
Required [javacv](https://github.com/bytedeco/javacv) library files: [javacv.jar](https://drive.google.com/open?id=0B-ty45hB_mC7V2hWNTJxT1F3R1k), [javacpp.jar](https://drive.google.com/open?id=0B-ty45hB_mC7d0lZUEJXLUZNR2c), [opencv.jar](https://drive.google.com/open?id=0B-ty45hB_mC7bFI1Ml9tY3JVMmc).
And platform-dependent library files:
* [32-bit Windows](https://drive.google.com/open?id=0B-ty45hB_mC7SmhTQ3hjZmw4MkU)
* [64-bit Windows](https://drive.google.com/open?id=0B-ty45hB_mC7d0ZPSzRSdHpLLWM)
* [64-bit Mac](https://drive.google.com/open?id=0B-ty45hB_mC7NFpDYkJPU3FsdUk)
* [32-bit Linux](https://drive.google.com/open?id=0B-ty45hB_mC7MDFiOG9ZLXByU28)
* [64-bit Linux](https://drive.google.com/open?id=0B-ty45hB_mC7T1E5bmJYZDJCaE0)

### Installation
1. Download and install either ImageJ or Fiji (check that the downloaded version is working with Java 8).
2. Unzip the [file](https://github.com/joheras/IJ-OpenCV/raw/master/Plugins/IJ-OpenCV.zip) containing the plugins developed with the IJ-OpenCV library, and copy the generated folder in the plugins folder of ImageJ/Fiji.
3. Put above jar files ([IJOpenCV.jar](https://github.com/joheras/IJ-OpenCV/releases/download/1.0/IJOpenCV.jar), [javacv.jar](https://drive.google.com/open?id=0B-ty45hB_mC7V2hWNTJxT1F3R1k), [javacpp.jar](https://drive.google.com/open?id=0B-ty45hB_mC7d0lZUEJXLUZNR2c), [opencv.jar](https://drive.google.com/open?id=0B-ty45hB_mC7bFI1Ml9tY3JVMmc), [32-bit Windows](https://drive.google.com/open?id=0B-ty45hB_mC7SmhTQ3hjZmw4MkU), [64-bit Windows](https://drive.google.com/open?id=0B-ty45hB_mC7d0ZPSzRSdHpLLWM), [64-bit Mac](https://drive.google.com/open?id=0B-ty45hB_mC7NFpDYkJPU3FsdUk), [32-bit Linux](https://drive.google.com/open?id=0B-ty45hB_mC7MDFiOG9ZLXByU28), [64-bit Linux](https://drive.google.com/open?id=0B-ty45hB_mC7T1E5bmJYZDJCaE0)) into your ImageJ/Fiji plugins folder.

After restarting ImageJ/Fiji, you will find the option IJ-OpenCV under the plugins menu. That submenu contains the plugins developed with this library.

### System requirements
IJ-OpenCV requires that ImageJ/Fiji works with Java 8.

### Documentation
The API of the IJ-OpenCV library is available at the following [link](http://joheras.github.io/IJ-OpenCV/javadoc/).

### Source files
The source files of the IJ-OpenCV library are available at the following [link](https://github.com/joheras/IJ-OpenCV).
In addition, the source files of the plugins developed with the IJ-OpenCV library are available [here](https://github.com/joheras/IJ-OpenCV/tree/master/Plugins/src).

### List of IJ-OpenCV plugins
* Adaptive threshold: Given an image, it applies either the adaptive Gaussian threshold or the adaptive mean threshold. [OpenCV link to the topic](http://docs.opencv.org/2.4/modules/imgproc/doc/miscellaneous_transformations.html?highlight=adaptive%20threshold#adaptivethreshold).
* BGR histogram comparison: Given a stack of images, it compares the images using the BGR histogram and employing different measures . [OpenCV link to the topic](http://docs.opencv.org/2.4/doc/tutorials/imgproc/histograms/histogram_calculation/histogram_calculation.html?highlight=histogram).
* Black hat: Given a grayscale image, it returns such an image after applying the black hat morphological operation. [OpenCV link to the topic](http://docs.opencv.org/2.4/doc/tutorials/imgproc/opening_closing_hats/opening_closing_hats.html?highlight=black%20hat#black-hat).
* Canny edge detection: Given an image, it detects its using the Canny edge detection algorithm. [OpenCV link to the topic](http://docs.opencv.org/2.4/modules/imgproc/doc/feature_detection.html?highlight=canny%20edge%20detection#void%20Canny%28InputArray%20image,%20OutputArray%20edges,%20double%20threshold1,%20double%20threshold2,%20int%20apertureSize,%20bool%20L2gradient%29).
* Convex hull from polygon: Given a polygonal selection, it returns its convex hull . [OpenCV link to the topic](http://docs.opencv.org/2.4/doc/tutorials/imgproc/shapedescriptors/hull/hull.html?highlight=convex%20hull).
* Detect circles: Given an image, it detects the circles of such an image using the Hough Circle transform. [OpenCV link to the topic](http://docs.opencv.org/2.4/doc/tutorials/imgproc/imgtrans/hough_circle/hough_circle.html?highlight=hough%20circles).
* Face detection: Given an image, it detects the faces of the image. [OpenCV link to the topic](http://docs.opencv.org/2.4/modules/contrib/doc/facerec/facerec_tutorial.html).
* Find contours: Given an image, it detects the contours of the objects of such an image. [OpenCV link to the topic](http://docs.opencv.org/2.4/doc/tutorials/imgproc/shapedescriptors/find_contours/find_contours.html?highlight=find%20contours).
* High dynamic range imaging: Given a stack of images, it creates a high dynamic range image using the exposure fusion. [OpenCV link to the topic](http://docs.opencv.org/3.0-beta/doc/tutorials/photo/hdr_imaging/hdr_imaging.html).
* Hough Lines: Given an image, it computes the lines of the image using the Hough transform. [OpenCV link to the topic](http://docs.opencv.org/2.4/doc/tutorials/imgproc/imgtrans/hough_lines/hough_lines.html?highlight=hough%20lines).
* HSV histogram comparison: Given a stack of images, it compares the images using the HSV histogram and employing different measures . [OpenCV link to the topic](http://docs.opencv.org/2.4/doc/tutorials/imgproc/histograms/histogram_calculation/histogram_calculation.html?highlight=histogram).
* Keypoint detector: Given an image, it detects its keypoints using different algorithms. [OpenCV link to the topic](http://docs.opencv.org/2.4/modules/features2d/doc/common_interfaces_of_feature_detectors.html?highlight=keypoint).
* Kmeans clustering: Given a stack of images, it groups them using the BGR histogram as features and the Kmeans clustering algorithm. [OpenCV link to the topic](http://docs.opencv.org/2.4/modules/core/doc/clustering.html?highlight=kmeans).
* Non local means denoising: Given an image, it applies non local means denoising. [OpenCV link to the topic](http://docs.opencv.org/2.4/modules/photo/doc/denoising.html).
* RotatedRect from a polygon ROI: Given a polygonal selection, it returns the associated rotated rectangle. [OpenCV link to the topic](http://docs.opencv.org/2.4/modules/core/doc/basic_structures.html?highlight=rotated%20rect#RotatedRect).
* Stitching: Given an array of images, it stitches them creating a panoramic view. [OpenCV link to the topic](http://docs.opencv.org/2.4/modules/stitching/doc/introduction.html?highlight=stitching).
* Template Matching: Given an image, and a selection (template) in that image, it finds all the other regions of the image that match with the template. [OpenCV link to the topic](http://docs.opencv.org/2.4/doc/tutorials/imgproc/histograms/template_matching/template_matching.html?highlight=template%20matching).
* White Hat: Given a grayscale image, it returns such an image after applying the white hat morphological operation. [OpenCV link to the topic](http://docs.opencv.org/2.4/doc/tutorials/imgproc/opening_closing_hats/opening_closing_hats.html?highlight=black%20hat#top-hat).

### Videos
We include several videos showing how the plugins developed with the IJ-OpenCV library work.

#### Video 1
This first video shows how the "adaptive thresholding", "BGR Histogram Comparison" and "HSV Histogram comparison" plugins work.

[![Everything Is AWESOME](http://www.mailpoet.com/wp-content/uploads/2012/01/wysija-youtube-color-player.png)](https://www.youtube.com/watch?v=ERoqTnUOayA)

#### Video 2
This second video shows how the following plugins work:
* BlackHat and White Hat morphological operators
* Canny edge detection
* Convex hull from polygon ROI
* Circle detection
* Measure oxidation
* Face detection
* Find Contours
* High Dynamic Range Imaging

[![Everything Is AWESOME](http://www.mailpoet.com/wp-content/uploads/2012/01/wysija-youtube-color-player.png)](https://www.youtube.com/watch?v=BMGhKQZWhs8)


### Contact
Jónathan Heras (joheras@gmail.com)
