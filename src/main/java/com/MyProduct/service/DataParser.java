package com.MyProduct.service;


import com.MyProduct.model.CustomerData;

import java.util.List;

public interface DataParser {
    List<CustomerData> parse(String inputData);
}
