package com.example.appbanxe.model;

import java.io.Serializable;

public class SanPhamMoi implements Serializable {
   int id;
   String tensp;
   String hinhanh;
   String giasp;
   String motasp;
   int loai;

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getTensp() {
      return tensp;
   }

   public void setTensp(String tensp) {
      this.tensp = tensp;
   }

   public String getHinhanh() {
      return hinhanh;
   }

   public void setHinhanh(String hinhanh) {
      this.hinhanh = hinhanh;
   }

   public String getGiasp() {
      return giasp;
   }

   public void setGiasp(String giasp) {
      this.giasp = giasp;
   }

   public String getMotasp() {
      return motasp;
   }

   public void setMotasp(String motasp) {
      this.motasp = motasp;
   }

   public int getLoai() {
      return loai;
   }

   public void setLoai(int loai) {
      this.loai = loai;
   }
}
