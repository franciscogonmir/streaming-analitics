package com.analytics.domain.service;

import com.analytics.domain.entities.Stream;

import java.util.List;

public interface StatisticsRepositoryService {

    void saveStatistics(List<Stream> feed);

}
