package com.testautomation.TestRunner;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;

@CucumberOptions (
		  features = "./features/"
        ,glue = {"com.testautomation.StepDef"},snippets = SnippetType.CAMELCASE
        ,plugin = {"pretty","html:target/site/cucumber-pretty","json:target/cucumber.json"}
        ,monochrome = true)
@Listeners(com.testautomation.Listners.ITestListenerImpl.class)
public class TestRunner{
    private TestNGCucumberRunner testNGCucumberRunner;
    
    @BeforeClass(alwaysRun = true)
    public void setUpClass() throws Exception {    	
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    @Test(groups = "cucumber",description = "Runs Cucumber Feature" ,dataProvider = "features")    
    public void feature(CucumberFeatureWrapper cucumberFeature) {
    	testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
    }
    
    @DataProvider
    public Object[][] features() {	
    	 return testNGCucumberRunner.provideFeatures();
    }
    
    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {    	
        testNGCucumberRunner.finish();        
    }
    
  
}