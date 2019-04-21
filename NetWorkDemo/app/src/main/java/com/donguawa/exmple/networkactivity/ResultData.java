package com.donguawa.exmple.networkactivity;

import java.util.List;

public class ResultData {
    private int mcode;
    private List<Test> mTest;

    public int getMcode() {
        return mcode;
    }

    public void setMcode(int mcode) {
        this.mcode = mcode;
    }

    public List<Test> getmTest() {
        return mTest;
    }

    public void setmTest(List<Test> mTest) {
        this.mTest = mTest;
    }

    public static class Test{
        private int mSid;
        private String mText;
        private String mType;
        private int uid;
        private String mName;

        public int getmSid() {
            return mSid;
        }

        public void setmSid(int mSid) {
            this.mSid = mSid;
        }

        public String getmText() {
            return mText;
        }

        public void setmText(String mText) {
            this.mText = mText;
        }

        public String getmType() {
            return mType;
        }

        public void setmType(String mType) {
            this.mType = mType;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getmName() {
            return mName;
        }

        public void setmName(String mName) {
            this.mName = mName;
        }

        @Override
        public String toString() {
            return "Test{" +
                    "mSid=" + mSid +
                    ", mText='" + mText + '\'' +
                    ", mType='" + mType + '\'' +
                    ", uid=" + uid +
                    ", mName='" + mName + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ResultData{" +
                "mcode=" + mcode +
                ", mTest=" + mTest +
                '}';
    }
}
