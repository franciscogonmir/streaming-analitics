package com.analytics.infrastructure.service;

import com.analytics.domain.entities.DataPoint;
import com.analytics.domain.entities.DataStream;
import com.analytics.domain.entities.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StatisticsCalculatorServiceImplTest {

    List<Stream> streams;
    StatisticsCalculatorServiceImpl service;

    @BeforeEach
    void setUp() {
        this.service = new StatisticsCalculatorServiceImpl();;
        this.streams =  getStreams();
    }

    @Test
    void calculateMean() {
       assertEquals(15,this.service.calculateMean(streams));
    }

    @Test
    void getMedian() {
        assertEquals(15,this.service.getMedian(streams));
    }
    @Test
    void getMode() {
        List<Integer> mode = this.service.getMode(streams);
        assertEquals(2,mode.size());
        assertEquals(20 ,mode.get(0));
        assertEquals(10 ,mode.get(1));


    }
    @Test
    void calculateMaxValue() {
        assertEquals(20 ,this.service.calculateMaxValue(streams));
    }

    @Test
    void calculateMinValue() {
        assertEquals(10 ,this.service.calculateMinValue(streams));

    }

    @Test
    void calculateStandardDeviation() {
        assertEquals(5 ,this.service.calculateStandardDeviation(streams));
    }
    @Test
    void calculateFirstQuartile() {
        assertEquals(10 ,this.service.calculateFirstQuartile(streams));

    }
    @Test
    void calculateThirdQuartile() {
        assertEquals(20 ,this.service.calculateThirdQuartile(streams));
    }
     //TODO test add validation in service

    private List<Stream> getStreams() {
        DataPoint dataPoint1 = DataPoint.builder()
                .at(1431602523123L)
                .value(10)
                .build();
        DataPoint dataPoint2 = DataPoint.builder()
                .at(1431602523123L)
                .value(20)
                .build();

        DataStream dataStream1 = DataStream.builder()
                .id("id")
                .feed("feed")
                .dataPoints(List.of(dataPoint1,dataPoint2))
                .build();

        DataStream dataStream2 = DataStream.builder()
                .id("id")
                .feed("feed")
                .dataPoints(List.of(dataPoint1,dataPoint2))
                .build();

        Stream stream1 = Stream.builder()
                .dataStreams(List.of(dataStream1,dataStream2))
                .version("1.0.0")
                .build();

        Stream stream2 = Stream.builder()
                .dataStreams(List.of(dataStream1,dataStream2))
                .version("1.0.0")
                .build();

        return  List.of(stream1,stream2);
    }
}