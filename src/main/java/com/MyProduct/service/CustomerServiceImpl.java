package com.MyProduct.service;

import com.MyProduct.model.CustomerData;
import com.MyProduct.service.DataParserFactory;

import com.MyProduct.strategy.ReportStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final DataParserFactory dataParserFactory;
    private final ReportStrategy reportStrategy;

    @Override
    public Map<String, Object> generateReport(String inputData) {
        if (inputData == null || inputData.trim().isEmpty()) {
            throw new IllegalArgumentException("Input data cannot be null or empty");
        }

        List<CustomerData> customerDataList = dataParserFactory.getParser("csv").parse(inputData);
        return reportStrategy.generateReport(customerDataList);
    }

}
