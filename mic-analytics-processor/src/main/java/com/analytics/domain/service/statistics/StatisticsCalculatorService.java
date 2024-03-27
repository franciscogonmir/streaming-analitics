package com.analytics.domain.service.statistics;

import com.analytics.domain.entities.Messaging.Stream;

import java.util.List;

public interface StatisticsCalculatorService {
    double calculateMean(final List<Stream> data);

    double getMedian(final List<Stream> data);

    List<Integer> getMode(final List<Stream> data);

    double calculateStandardDeviation(final List<Stream> data);

    double calculateFirstQuartile(final List<Stream> data);

    double calculateThirdQuartile(final List<Stream> data);

    double calculateMaxValue(final List<Stream> data);

    double calculateMinValue(final List<Stream> data);
}
