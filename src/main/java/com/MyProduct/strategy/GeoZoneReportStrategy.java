package com.MyProduct.strategy;


import com.MyProduct.model.CustomerData;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GeoZoneReportStrategy implements ReportStrategy {

    @Override
    public Map<String, Object> generateReport(List<CustomerData> customerDataList) {
        Map<String, Object> report = new HashMap<>();

        // Unique customers per contract
        report.put("uniqueCustomersPerContract", getUniqueCustomersPerContract(customerDataList));

        // Unique customers per geo-zone
        report.put("uniqueCustomersPerGeozone", getUniqueCustomersPerGeozone(customerDataList));

        // Average build duration per geo-zone
        report.put("averageBuildDurationPerGeozone", getAverageBuildDurationPerGeozone(customerDataList));

        // List of unique customers per geo-zone
        report.put("uniqueCustomersListPerGeozone", getUniqueCustomersListPerGeozone(customerDataList));

        return report;
    }

    private Map<String, Long> getUniqueCustomersPerContract(List<CustomerData> data) {
        return data.stream()
                .collect(Collectors.groupingBy(CustomerData::getContractId,
                        Collectors.mapping(CustomerData::getCustomerId, Collectors.toSet())))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> (long) e.getValue().size()));
    }

    private Map<String, Long> getUniqueCustomersPerGeozone(List<CustomerData> data) {
        return data.stream()
                .collect(Collectors.groupingBy(CustomerData::getGeozone,
                        Collectors.mapping(CustomerData::getCustomerId, Collectors.toSet())))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> (long) e.getValue().size()));
    }

    private Map<String, Double> getAverageBuildDurationPerGeozone(List<CustomerData> data) {
        return data.stream()
                .collect(Collectors.groupingBy(CustomerData::getGeozone,
                        Collectors.averagingInt(CustomerData::getBuildDuration)));
    }

    private Map<String, Set<String>> getUniqueCustomersListPerGeozone(List<CustomerData> data) {
        return data.stream()
                .collect(Collectors.groupingBy(CustomerData::getGeozone,
                        Collectors.mapping(CustomerData::getCustomerId, Collectors.toSet())));
    }
}
