package com.dokter.yacob.dokter;

/**
 * Created by yacob on 3/13/2018.
 */

public class ICD {
    int Index;
    String Diagnosa;
    public ICD(){

    }
    public ICD(int index,String diagnosa) {
        this.Index = index;
        this.Diagnosa = diagnosa;
    }

    public int getIndex() {
        return Index;
    }

    public void setIndex(int index) {
        Index = index;
    }

    public String getDiagnosa() {
        return Diagnosa;
    }

    public void setDiagnosa(String diagnosa) {
        Diagnosa = diagnosa;
    }
}
