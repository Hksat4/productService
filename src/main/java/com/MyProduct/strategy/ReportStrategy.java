package com.MyProduct.strategy;


import com.MyProduct.model.CustomerData;

import java.util.List;
import java.util.Map;

public interface ReportStrategy {
    Map<String, Object> generateReport(List<CustomerData> data);
}
