package com.aera.hotwire.test;

import com.aera.hotwire.application.Hotwire;
import com.aera.hotwire.testInitializer.BrowserConfiguration;
import org.testng.annotations.Test;

public class HotwireTest extends BrowserConfiguration {

    @Test
    public void searchResult() throws Exception{
        Hotwire hotwire = new Hotwire(getDriver());
        hotwire.searchVacationBundle();
    }
}