/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

/**
 *
 * @author hoang
 */
public interface QuenMatKhauService<E, K> {

    void update(E e);

    E selectById(K maNV);
}
