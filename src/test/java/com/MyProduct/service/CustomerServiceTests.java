package com.MyProduct.service;


import com.MyProduct.exception.InvalidInputException;
import com.MyProduct.model.CustomerData;
import com.MyProduct.strategy.GeoZoneReportStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTests {

    @Mock
    private DataParserFactory dataParserFactory;

    @Mock
    private CsvDataParser csvDataParser;

    @Mock
    private GeoZoneReportStrategy geoZoneReportStrategy;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private String inputData;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        inputData = "2343225,2345,us_east,RedTeam,ProjectApple,3445s\n" +
                "1223456,2345,us_west,BlueTeam,ProjectBanana,2211s\n" +
                "3244332,2346,eu_west,YellowTeam3,ProjectCarrot,4322s\n" +
                "1233456,2345,us_west,BlueTeam,ProjectDate,2221s\n" +
                "3244132,2346,eu_west,YellowTeam3,ProjectEgg,4122s";
    }

    @Test
    void testGenerateReport_Success() {
        // Given
        List<CustomerData> customerDataList = List.of(
                new CustomerData("2343225", "2345", "us_east", "RedTeam", "ProjectApple", 3445),
                new CustomerData("1223456", "2345", "us_west", "BlueTeam", "ProjectBanana", 2211),
                new CustomerData("3244332", "2346", "eu_west", "YellowTeam3", "ProjectCarrot", 4322),
                new CustomerData("1233456", "2345", "us_west", "BlueTeam", "ProjectDate", 2221),
                new CustomerData("3244132", "2346", "eu_west", "YellowTeam3", "ProjectEgg", 4122)
        );

        // When
        when(dataParserFactory.getParser("csv")).thenReturn(csvDataParser);
        when(csvDataParser.parse(inputData)).thenReturn(customerDataList);
        when(geoZoneReportStrategy.generateReport(customerDataList)).thenReturn(mockReport());

        // Act
        Map<String, Object> report = customerService.generateReport(inputData);

        // Then
        assertNotNull(report);
        assertEquals(2, ((Map<String, Long>) report.get("uniqueCustomersPerGeozone")).get("us_west"));
        assertEquals(3445.0, ((Map<String, Double>) report.get("averageBuildDurationPerGeozone")).get("us_east"));
        verify(dataParserFactory, times(1)).getParser("csv");
        verify(csvDataParser, times(1)).parse(inputData);
        verify(geoZoneReportStrategy, times(1)).generateReport(customerDataList);
    }

    @Test
    void testGenerateReport_InvalidInput() {
        // Given
        String invalidInput = "";

        // When / Then
        assertThrows(InvalidInputException.class, () -> customerService.generateReport(invalidInput));
        verify(dataParserFactory, never()).getParser(anyString());
        verify(csvDataParser, never()).parse(anyString());
        verify(geoZoneReportStrategy, never()).generateReport(anyList());
    }


    private Map<String, Object> mockReport() {
        return Map.of(
                "uniqueCustomersPerContract", Map.of(
                        "2345", 3L,
                        "2346", 2L
                ),
                "uniqueCustomersPerGeozone", Map.of(
                        "us_east", 1L,
                        "us_west", 2L,
                        "eu_west", 2L
                ),
                "averageBuildDurationPerGeozone", Map.of(
                        "us_east", 3445.0,
                        "us_west", 2216.0,
                        "eu_west", 4222.0
                ),
                "uniqueCustomersListPerGeozone", Map.of(
                        "us_east", List.of("2343225"),
                        "us_west", List.of("1223456", "1233456"),
                        "eu_west", List.of("3244332", "3244132")
                )
        );
    }
}
