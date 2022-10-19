package labb4v2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TollFreeCalculatorTest {
    TollFeeCalculator TFC = new TollFeeCalculator("src/test/java/labb4v2/Lab4.txt");
    String[] testStringArr = {
        "2020-06-30 00:05", 
        "2020-06-30 06:34", 
        "2020-06-30 08:52", 
        "2020-06-30 10:13", 
        "2020-06-30 10:25", 
        "2020-06-30 11:04", 
        "2020-06-30 16:50", 
        "2020-06-30 18:00", 
        "2020-06-30 21:30", 
        "2020-07-01 00:00", 
        "2020-07-01 10:00"
    };

    
    
    @Test
    void expectStringArrDatesToBe_sameAs_LocalDateTimesArr(){
        TFC.setDates(testStringArr);
        assertEquals(11, TFC.dates.length);
    }

    @Test
    void CheckThat_getTotalFeeCost_ReturnsSmallerValueThen60(){
        //case 1
        TFC.setDates(testStringArr);
        int Result = TFC.getTotalFeeCost(TFC.dates);
        assertEquals(55, Result);
    }

    @Test
    void CheckThat_getTotalFeeCost_returns60IfValueIsGreatherThan60(){
        TollFeeCalculator totalValueOver60 = new TollFeeCalculator("src/test/java/labb4v2/Lab4ValueOver60.txt");
        int result = totalValueOver60.getTotalFeeCost(totalValueOver60.dates);
        assertEquals(60, result);
    }

    
}
