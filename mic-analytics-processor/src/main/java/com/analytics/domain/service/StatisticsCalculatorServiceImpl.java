package com.analytics.domain.service;

import com.analytics.domain.entities.DataPoint;
import com.analytics.domain.entities.Stream;
import com.analytics.domain.entities.persistence.Stats;
import com.analytics.domain.service.StatisticsCalculatorService;
import com.analytics.infrastructure.mapper.persistence.StatisticsMapper;
import com.analytics.infrastructure.persistence.model.StatisticsModel;
import com.analytics.infrastructure.persistence.repository.StatisticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
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
    public double calculateMean(List<Stream> feed) {
        return getAllDataPoints(feed)
                .stream()
                .mapToInt(DataPoint::value)
                .average()
                .orElse(ZERO);
    }


    @Override
    public double getMedian(List<Stream> feed) {
        return getAllDataPoints(feed)
                .stream()
                .sorted(Comparator.comparingInt(DataPoint::value))
                .collect(Collectors.collectingAndThen(Collectors.toList(), this::calculateMedian));
    }

    @Override
    public List<Integer> getMode(List<Stream> feed) {
        List<Integer> numberValues = getAllDataPoints(feed)
                .stream()
                .map(DataPoint::value)
                .toList();
        return calculateMode(numberValues);
    }

    @Override
    public double calculateMaxValue(List<Stream> feed) {
        return getAllDataPoints(feed)
                .stream()
                .mapToInt(DataPoint::value)
                .max()
                .orElse(ZERO);
    }

    @Override
    public double calculateMinValue(List<Stream> feed) {
        return getAllDataPoints(feed)
                .stream()
                .mapToInt(DataPoint::value)
                .min()
                .orElse(ZERO);
    }

    @Override
    public double calculateStandardDeviation(List<Stream> feed) {
        List<DataPoint> dataPoints = getAllDataPoints(feed);
        double media = this.calculateMean(feed);
        double sumOfSquaredDifferences = getAllDataPoints(feed).stream()
                .mapToDouble(dataPoint -> Math.pow(dataPoint.value() - media, TWO))
                .sum();

        return Math.sqrt(sumOfSquaredDifferences / dataPoints.size());
    }

    @Override
    public double calculateFirstQuartile(List<Stream> feed) {
        List<Integer> sortedValues = getSortedValues(feed);
        int size = sortedValues.size();
        int indexQuartile = size / FOUR;
        return calculateQuartile(sortedValues, size, indexQuartile);

    }

    @Override
    public double calculateThirdQuartile(List<Stream> feed) {
        List<Integer> sortedValues = getSortedValues(feed);
        int size = sortedValues.size();
        int indexQuartile = 3 * size / FOUR;
        ;
        return calculateQuartile(sortedValues, size, indexQuartile);
    }

    private List<DataPoint> getAllDataPoints(List<Stream> feed) {
        return feed.stream()
                .flatMap(stream -> stream.dataStreams().stream())
                .flatMap(dataStream -> dataStream.dataPoints().stream())
                .filter(Objects::nonNull)
                .toList();
    }

    private double calculateMedian(List<DataPoint> dataPoints) {
        var size = dataPoints.size();
        var middleValue = dataPoints.get(size / TWO);
        var middleValue2 = dataPoints.get(size / TWO - ONE);
        return isEvenNumber(size) ? (middleValue2.value() + middleValue.value()) / 2.0 :
                middleValue.value();
    }

    private List<Integer> calculateMode(List<Integer> numberValues) {
        Map<Integer, Integer> frequencies = getFrequencies(numberValues);
        int maxFrequency = getMaxFrequency(frequencies);
        return frequencies.entrySet().stream()
                .filter(entry -> entry.getValue() == maxFrequency)
                .map(Map.Entry::getKey)
                .toList();
    }

    private Integer getMaxFrequency(Map<Integer, Integer> frequencies) {
        return frequencies.values().stream()
                .max(Integer::compareTo)
                .orElse(ZERO);
    }

    private Map<Integer, Integer> getFrequencies(List<Integer> numberValues) {
        Map<Integer, Integer> frequencies = new HashMap<>();
        for (int number : numberValues) {
            frequencies.put(number, frequencies.getOrDefault(number, ZERO) + ONE);
        }
        return frequencies;
    }

    private List<Integer> getSortedValues(List<Stream> feed) {
        return getAllDataPoints(feed)
                .stream()
                .map(DataPoint::value)
                .sorted()
                .toList();
    }

    private double calculateQuartile(List<Integer> sortedValues, int size, int indexQuartile) {
        return isEvenNumber(size) ?
                (sortedValues.get(indexQuartile - ONE) + sortedValues.get(indexQuartile)) / 2.0 :
                sortedValues.get(indexQuartile);
    }

    private static boolean isEvenNumber(int size) {
        return size % TWO == ZERO;
    }
}
