package com.skirpsi.api.posyandu.misc;

import java.util.Date;

import lombok.Data;

@Data
public class CreateBalitaEntity {
	private Integer idbalita;
    private String namaBalita;
    private String nikBalita;
    private String tempatLahirBalita;
    private Date tanggalLahirBalita;
    private String namaOrangTua;
    private String nikOrangTua;
    private String jenisKelaminBalita;
    private Float beratSaatLahirBalita;
    private Float tinggiSaatLahirBalita;
}
