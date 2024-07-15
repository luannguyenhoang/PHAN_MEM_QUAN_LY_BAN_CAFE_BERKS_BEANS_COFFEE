/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewmodel;

import java.util.List;

/**
 *
 * @author trant
 */
public class Area {

    private String idArea;
    private String codeArea;
    private List<Table> ListTable;

    public Area() {
    }

    public Area(String idArea, String codeArea, List<Table> ListTable) {
        this.idArea = idArea;
        this.codeArea = codeArea;
        this.ListTable = ListTable;
    }

    public String getIdArea() {
        return idArea;
    }

    public void setIdArea(String idArea) {
        this.idArea = idArea;
    }

    public String getCodeArea() {
        return codeArea;
    }

    public void setCodeArea(String codeArea) {
        this.codeArea = codeArea;
    }

    public List<Table> getListTable() {
        return ListTable;
    }

    public void setListTable(List<Table> ListTable) {
        this.ListTable = ListTable;
    }

    @Override
    public String toString() {
        return codeArea;
    }

}
