package com.analytics.domain.service;

import com.analytics.domain.entities.Stream;

import java.util.List;

public interface StatisticsCalculatorService {
    double calculateMean(List<Stream> data);

    double getMedian(List<Stream> data);

    List<Integer> getMode(List<Stream> data);

    double calculateStandardDeviation(List<Stream> data);

    double calculateFirstQuartile(List<Stream> data);

    double calculateThirdQuartile(List<Stream> data);

    double calculateMaxValue(List<Stream> data);

    double calculateMinValue(List<Stream> data);
}
