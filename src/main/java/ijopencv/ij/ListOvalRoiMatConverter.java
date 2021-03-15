/*-
 * #%L
 * A library that allows the connection of ImageJ and OpenCV.
 * %%
 * Copyright (C) 2017 - 2021 University of La Rioja
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ijopencv.ij;

import ij.gui.OvalRoi;
import ijopencv.utils.CircleCV;
import java.awt.Rectangle;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;
import static org.bytedeco.javacpp.opencv_core.merge;
import org.scijava.Prioritized;
import org.scijava.Priority;
import org.scijava.convert.AbstractConverter;
import org.scijava.convert.Converter;
import org.scijava.log.LogService;
import org.scijava.plugin.Plugin;

@Plugin(type = Converter.class, priority = Priority.LOW_PRIORITY)
public class ListOvalRoiMatConverter extends AbstractConverter<List<OvalRoi>, Mat> {

    @Override
    public int compareTo(Prioritized o) {
        return super.compareTo(o);
    }

    @Override
    public LogService log() {
        return super.log();
    }

    @Override
    public String getIdentifier() {
        return super.getIdentifier();
    }

    @Override
    public < T> T convert(Object o, Class< T> type) {
        ArrayList<OvalRoi> alor = (ArrayList<OvalRoi>) o;
        float[] xpoints = new float[alor.size()];
        float[] ypoints = new float[alor.size()];
        float[] radiuses = new float[alor.size()];

        for (int i = 0; i < alor.size(); i++) {
            Rectangle rect = alor.get(i).getBounds();
            CircleCV c = new CircleCV(new opencv_core.Point(rect.x + rect.width / 2, rect.y + rect.height / 2), rect.width / 2);

            xpoints[i] = rect.x + rect.width / 2;
            ypoints[i] = rect.y + rect.height / 2;
            radiuses[i] = rect.width / 2;
        }

        Mat mx = new Mat(xpoints);
        Mat my = new Mat(ypoints);
        Mat mr = new Mat(radiuses);
        opencv_core.MatVector mxyr = new opencv_core.MatVector(3);
        mxyr.put(0, mx);
        mxyr.put(1, my);
        mxyr.put(2, mr);
        Mat m = new Mat();
        merge(mxyr, m);
        return (T) m;

    }

    @Override
    public Class< List<OvalRoi>> getInputType() {
        return (Class<List<OvalRoi>>) (Object) List.class;
    }

    @Override
    public Class< Mat> getOutputType() {
        return Mat.class;
    }

    @Override
    public boolean canConvert(Object src, Type dest) {
        
        if(!(src instanceof ArrayList<?>)){
            return false;
        }
        
        
        ArrayList<OvalRoi> alor = (ArrayList<OvalRoi>) src;

        for (int i = 0; i < alor.size(); i++) {
            Rectangle rect = alor.get(i).getBounds();
            if (rect.width != rect.height) {
                return false;
            }
        }
        return true;
    }

}
