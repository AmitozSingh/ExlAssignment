package com.testautomation.Listners;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.testautomation.StepDef.mapsSearchStepDef;

public class ITestListenerImpl  implements ITestListener
{


	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestSuccess(ITestResult result) {
		System.out.println("PASS");
		mapsSearchStepDef.tearDown();
		
	}

	public void onTestFailure(ITestResult result) {
		System.out.println("FAIL");
		File src =  mapsSearchStepDef.takeScreenShotAndReturnFile();
		System.out.println(" SRC Location is "+ src);
		try {
			FileUtils.copyFile(src, new File(System.getProperty("user.dir")+"\\test-output\\"+"SearchDirectionFailed.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		mapsSearchStepDef.tearDown();
	}

	public void onTestSkipped(ITestResult result) {
		System.out.println("SKIP");
		mapsSearchStepDef.tearDown();
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {
		System.out.println("****************Execution started on TEST ENV***************");
		
	}

	public void onFinish(ITestContext context) {
		System.out.println("****************Execution Completed on TEST ENV***************");
		
	}
	
	
}
