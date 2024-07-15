/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewmodel;

/**
 *
 * @author ASUS
 */
public class ChiNhanhViewModel_Hoang {
    private String id;
    private String Ma;


    public ChiNhanhViewModel_Hoang() {
    }

    public ChiNhanhViewModel_Hoang(String id, String Ma) {
        this.id = id;
        this.Ma = Ma;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMa() {
        return Ma;
    }

    public void setMa(String Ma) {
        this.Ma = Ma;
    }

    

    @Override
    public String toString() {
        return Ma;
    }
    
}
