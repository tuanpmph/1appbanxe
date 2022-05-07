package com.example.appbanxe.retroflit;

import com.example.appbanxe.model.DonHangModel;
import com.example.appbanxe.model.LoaiSpModel;
import com.example.appbanxe.model.SanPhamMoiModel;
import com.example.appbanxe.model.UserModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Apibanhang {
    @GET("heelo1.php")
    Observable<LoaiSpModel> getLoaiSp();
    @GET("heelo2.php")
    Observable<SanPhamMoiModel> getSpMoi();



    @POST("heelo3.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> getSanPham(
            @Field("page") int page,
            @Field("loai") int loai
    );

    @POST("dangki.php")
    @FormUrlEncoded
    Observable<UserModel> dangKi(
            @Field("email") String email,
            @Field("pass") String pass,
            @Field("username") String username,
            @Field("mobile") String mobile
    );

    @POST("dangnhap.php")
    @FormUrlEncoded
    Observable<UserModel> dangNhap(
            @Field("email") String email,
            @Field("pass") String pass
    );

    @POST("reset.php")
    @FormUrlEncoded
    Observable<UserModel> resetPass(
            @Field("email") String email
    );

    @POST("donhang.php")
    @FormUrlEncoded
    Observable<UserModel> createOder(
            @Field("email") String email,
            @Field("sdt") String sdt,
             @Field("tongtien") String tongtien,
            @Field("iduser") String id,
            @Field("diachi") String diachi,
            @Field("soluong") int soluong,
            @Field("chitiet") String chitiet
    );

    @POST("xemdonhang.php")
    @FormUrlEncoded
    Observable<DonHangModel> xemDonHang(
            @Field("iduser") String id
    );
}
