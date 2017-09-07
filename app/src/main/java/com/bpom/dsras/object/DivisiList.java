package com.bpom.dsras.object;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apridosandyasa on 8/3/16.
 */
public class DivisiList {

    private String po;
    private String date;
    private String companyImport;
    private String companyExport;
    private String productName;
    private List<DivisiList> divisiListList;

    public DivisiList() {
        initializeDivisiList();
    }

    public DivisiList(String po, String date, String companyImport, String companyExport, String productName) {
        this.po = po;
        this.date = date;
        this.companyImport = companyImport;
        this.companyExport = companyExport;
        this.productName = productName;
    }

    public void setPo(String po) {
        this.po = po;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCompanyImport(String companyImport) {
        this.companyImport = companyImport;
    }

    public void setCompanyExport(String companyExport) {
        this.companyExport = companyExport;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPo() {
        return this.po;
    }

    public String getDate() {
        return this.date;
    }

    public String getCompanyImport() {
        return this.companyImport;
    }

    public String getCompanyExport() {
        return this.companyExport;
    }

    public String getProductName() {
        return this.productName;
    }

    public List<DivisiList> getDivisiListList() {
        return this.divisiListList;
    }

    private void initializeDivisiList() {
        this.divisiListList = new ArrayList<>();
        this.divisiListList.add(new DivisiList("PO.03.01.321.1.1.102562", "02/03/2016", "PT. Sumber Rejeki", "Porter Corp PTY Ltd", "PPPP Microxxx"));
        this.divisiListList.add(new DivisiList("PO.01.01.001.1.1.100000", "01/03/2016", "PT. Nama Perusahaan", "Company PTY Ltd", "Product Name"));
        this.divisiListList.add(new DivisiList("PO.01.01.001.1.1.100000", "01/03/2016", "PT. Nama Perusahaan", "Company PTY Ltd", "Product Name"));
    }
}
