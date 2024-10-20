package com.MyProduct.service;

import com.MyProduct.exception.InvalidInputException; // Import your custom exception
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
        // Check for null or empty input and throw InvalidInputException
        if (inputData == null || inputData.trim().isEmpty()) {
            throw new InvalidInputException("Input data cannot be null or empty");
        }

        // Parse the input data
        List<CustomerData> customerDataList = dataParserFactory.getParser("csv").parse(inputData);

        // Generate the report
        return reportStrategy.generateReport(customerDataList);
    }
}
