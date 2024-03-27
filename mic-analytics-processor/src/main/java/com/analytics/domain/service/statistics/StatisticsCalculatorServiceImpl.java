package com.analytics.domain.service.statistics;

import com.analytics.domain.entities.Messaging.DataPoint;
import com.analytics.domain.entities.Messaging.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticsCalculatorServiceImpl implements StatisticsCalculatorService {

    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int FOUR = 4;


    @Override
    public double calculateMean(final List<Stream> feed) {
        return getAllDataPoints(feed)
                .stream()
                .mapToInt(DataPoint::value)
                .average()
                .orElse(ZERO);
    }


    @Override
    public double getMedian(final List<Stream> feed) {
        return getAllDataPoints(feed)
                .stream()
                .sorted(Comparator.comparingInt(DataPoint::value))
                .collect(Collectors.collectingAndThen(Collectors.toList(), this::calculateMedian));
    }

    @Override
    public List<Integer> getMode(final List<Stream> feed) {
        List<Integer> numberValues = getAllDataPoints(feed)
                .stream()
                .map(DataPoint::value)
                .toList();
        return calculateMode(numberValues);
    }

    @Override
    public double calculateMaxValue(final List<Stream> feed) {
        return getAllDataPoints(feed)
                .stream()
                .mapToInt(DataPoint::value)
                .max()
                .orElse(ZERO);
    }

    @Override
    public double calculateMinValue(final List<Stream> feed) {
        return getAllDataPoints(feed)
                .stream()
                .mapToInt(DataPoint::value)
                .min()
                .orElse(ZERO);
    }

    @Override
    public double calculateStandardDeviation(final List<Stream> feed) {
        List<DataPoint> dataPoints = getAllDataPoints(feed);
        double media = this.calculateMean(feed);
        double sumOfSquaredDifferences = getAllDataPoints(feed).stream()
                .mapToDouble(dataPoint -> Math.pow(dataPoint.value() - media, TWO))
                .sum();

        return Math.sqrt(sumOfSquaredDifferences / dataPoints.size());
    }

    @Override
    public double calculateFirstQuartile(final List<Stream> feed) {
        List<Integer> sortedValues = getSortedValues(feed);
        int size = sortedValues.size();
        int indexQuartile = size / FOUR;
        return calculateQuartile(sortedValues, size, indexQuartile);

    }

    @Override
    public double calculateThirdQuartile(final List<Stream> feed) {
        List<Integer> sortedValues = getSortedValues(feed);
        int size = sortedValues.size();
        int indexQuartile = 3 * size / FOUR;
        ;
        return calculateQuartile(sortedValues, size, indexQuartile);
    }

    private List<DataPoint> getAllDataPoints(final List<Stream> feed) {
        return feed.stream()
                .flatMap(stream -> stream.dataStreams().stream())
                .flatMap(dataStream -> dataStream.dataPoints().stream())
                .filter(Objects::nonNull)
                .toList();
    }

    private double calculateMedian(final List<DataPoint> dataPoints) {
        var size = dataPoints.size();
        var middleValue = dataPoints.get(size / TWO);
        var middleValue2 = dataPoints.get(size / TWO - ONE);
        return isEvenNumber(size) ? (middleValue2.value() + middleValue.value()) / 2.0 :
                middleValue.value();
    }

    private List<Integer> calculateMode(final List<Integer> numberValues) {
        Map<Integer, Integer> frequencies = getFrequencies(numberValues);
        int maxFrequency = getMaxFrequency(frequencies);
        return frequencies.entrySet().stream()
                .filter(entry -> entry.getValue() == maxFrequency)
                .map(Map.Entry::getKey)
                .toList();
    }

    private Integer getMaxFrequency(final Map<Integer, Integer> frequencies) {
        return frequencies.values().stream()
                .max(Integer::compareTo)
                .orElse(ZERO);
    }

    private Map<Integer, Integer> getFrequencies(final List<Integer> numberValues) {
        Map<Integer, Integer> frequencies = new HashMap<>();
        for (int number : numberValues) {
            frequencies.put(number, frequencies.getOrDefault(number, ZERO) + ONE);
        }
        return frequencies;
    }

    private List<Integer> getSortedValues(final List<Stream> feed) {
        return getAllDataPoints(feed)
                .stream()
                .map(DataPoint::value)
                .sorted()
                .toList();
    }

    private double calculateQuartile(final List<Integer> sortedValues, final int size, final int indexQuartile) {
        return isEvenNumber(size) ?
                (sortedValues.get(indexQuartile - ONE) + sortedValues.get(indexQuartile)) / 2.0 :
                sortedValues.get(indexQuartile);
    }

    private static boolean isEvenNumber(final int size) {
        return size % TWO == ZERO;
    }
}
