package com.analytics.infrastructure.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Document(collection = "statistics")
public record StatisticsModel (
    @Id
     String id,

     long at,

     double mean,

     double median,

     List<Integer> mode,

     double standardDeviation,

     double firstQuartile,

     double thirdQuartile,

     double maxValue,

     double minValue

){

}
