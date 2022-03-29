import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;

public class Listener extends Method_container implements ITestListener
{

    int i=1;
    @Override
    public void onTestStart(ITestResult result)
    {
        System.out.println("**Test case "+i+" started**");
    }
    @Override
    public void onTestSuccess(ITestResult result)
    {
        try {
            getScreenShotPass();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onTestFailure(ITestResult result) {
        try {
            getScreenShotFail();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}