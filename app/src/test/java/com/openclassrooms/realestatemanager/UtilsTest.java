package com.openclassrooms.realestatemanager;

import static org.junit.Assert.*;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilsTest {
    @Test
    public void convertDollarToEuroTest(){
        //Given
        int dollars =100;
        int euroConvert = (int) (dollars * Utils.ExchangeDollarsEuros);

        //when
        int euroConvertForTest= Utils.convertDollarToEuro(dollars);

        //then
        assertEquals(euroConvert,euroConvertForTest);
    }

    @Test
    public void convertEuroToDollarTest(){
        //Given
        int euros =100;
        int dollarConvert = (int) (euros / Utils.ExchangeDollarsEuros);

        //when
        int dollarConvertForTest= Utils.convertEuroToDollar(euros);

        //then
        assertEquals(dollarConvert,dollarConvertForTest);
    }

    @Test
    public void getTodayDateTest(){
        //given
        Date dateToDay = new Date();

        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

        String day = dayFormat.format(dateToDay);
        String month = monthFormat.format(dateToDay);
        String year = yearFormat.format(dateToDay);

        //when
        String date = day+"/"+month+"/"+year;
        String dateToTest = Utils.getTodayDate();

        //then
        assertEquals(dateToTest,date);
    }

}
