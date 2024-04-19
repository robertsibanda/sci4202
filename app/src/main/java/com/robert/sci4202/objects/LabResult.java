package com.robert.sci4202.objects;

public class LabResult{
    private String testName, testCode, resultName, resultCode, testDate;


    public LabResult(String testName, String testCode, String resultName, String resultCode, String testDate) {
        this.testName = testName;
        this.testCode = testCode;
        this.resultName = resultName;
        this.resultCode = resultCode;
        this.testDate = testDate;
    }


    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestCode() {
        return testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    public String getResultName() {
        return resultName;
    }

    public void setResultName(String resultName) {
        this.resultName = resultName;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getTestDate() {
        return testDate;
    }

    public void setTestDate(String testDate) {
        this.testDate = testDate;
    }
}
