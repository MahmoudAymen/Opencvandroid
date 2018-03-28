package com.example.aymen.splashactivity;
import android.util.Log;

import ij.*;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
/**
 * Created by aymen on 04/05/2017.
 */
public class Entropy_Threshold  implements PlugInFilter {
    @Override
    public int setup(String s, ImagePlus imagePlus) {
        return PlugInFilter.DOES_8G | PlugInFilter.DOES_STACKS;
    }

    @Override
    public void run(ImageProcessor imageProcessor) {
        int[] hist = imageProcessor.getHistogram();
        int threshold = entropySplit(hist);
        imageProcessor.threshold(threshold);

    }
    private void aymen()
    {

    }
    private int entropySplit(int[] hist) {

        // Normalize histogram, that is makes the sum of all bins equal to 1.
        double sum = 0;
        for (int i = 0; i < hist.length; ++i) {
            sum += hist[i];
        }
        if (sum == 0) {
            // This should not normally happen, but...
            throw new IllegalArgumentException("Empty histogram: sum of all bins is zero.");
        }

        double[] normalizedHist = new double[hist.length];
        for (int i = 0; i < hist.length; i++) {
            normalizedHist[i] = hist[i] / sum;
        }

        //
        double[] pT = new double[hist.length];
        pT[0] = normalizedHist[0];
        for (int i = 1; i < hist.length; i++) {
            pT[i] = pT[i - 1] + normalizedHist[i];
        }

        // Entropy for black and white parts of the histogram
        final double epsilon = Double.MIN_VALUE;
        double[] hB = new double[hist.length];
        double[] hW = new double[hist.length];
        for (int t = 0; t < hist.length; t++) {
            // Black entropy
            if (pT[t] > epsilon) {
                double hhB = 0;
                for (int i = 0; i <= t; i++) {
                    if (normalizedHist[i] > epsilon) {
                        hhB -= normalizedHist[i] / pT[t] * Math.log(normalizedHist[i] / pT[t]);
                    }
                }
                hB[t] = hhB;
            } else {
                hB[t] = 0;
            }

            // White  entropy
            double pTW = 1 - pT[t];
            if (pTW > epsilon) {
                double hhW = 0;
                for (int i = t + 1; i < hist.length; ++i) {
                    if (normalizedHist[i] > epsilon) {
                        hhW -= normalizedHist[i] / pTW * Math.log(normalizedHist[i] / pTW);
                        Log.d("Entropy_Thresold","entropy="+hhW);
                    }
                }
                hW[t] = hhW;
            } else {
                hW[t] = 0;
            }
        }

        // Find histogram index with maximum entropy
        double jMax = hB[0] + hW[0];
        int tMax = 0;
        for (int t = 1; t < hist.length; ++t) {
            double j = hB[t] + hW[t];
            if (j > jMax) {
                jMax = j;
                tMax = t;
            }
        }
   Log.i("ddd","ghh"+tMax);
        return tMax;
    }
}
