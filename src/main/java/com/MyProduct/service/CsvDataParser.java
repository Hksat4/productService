package com.MyProduct.service;



import com.MyProduct.model.CustomerData;

import java.util.ArrayList;
import java.util.List;

public class CsvDataParser implements DataParser {

    @Override
    public List<CustomerData> parse(String inputData) {
        List<CustomerData> customerDataList = new ArrayList<>();
        String[] lines = inputData.split("\n");

        for (String line : lines) {
            String[] fields = line.split(",");

            String customerId = fields[0];
            String contractId = fields[1];
            String geozone = fields[2];
            String teamcode = fields[3];
            String projectcode = fields[4];
            int buildDuration = Integer.parseInt(fields[5].replace("s", ""));

            CustomerData customerData = new CustomerData(customerId, contractId, geozone, teamcode, projectcode, buildDuration);
            customerDataList.add(customerData);
        }

        return customerDataList;
    }
}
