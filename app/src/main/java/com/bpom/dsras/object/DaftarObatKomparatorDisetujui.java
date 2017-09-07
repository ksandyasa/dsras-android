package com.bpom.dsras.object;

import java.io.Serializable;

/**
 * Created by apridosandyasa on 8/23/16.
 */
public class DaftarObatKomparatorDisetujui implements Serializable {

    private int id;
    private String namaZatAktif;
    private String bentukSediaan;
    private String kekuatan;
    private String namaIndustriPendaftar;
    private String namaObatKomparator;
    private String namaIndustriProdusenObatKomparator;
    private String aktif;
    private String files;

    public DaftarObatKomparatorDisetujui() {

    }

    public DaftarObatKomparatorDisetujui(int i, String namaZat, String bentuk, String kekuatan, String namaIndustriPendaftar,
                                         String namaObat, String namaIndustriProdusen, String aktif, String files) {
        this.id = i;
        this.namaZatAktif = namaZat;
        this.bentukSediaan = bentuk;
        this.kekuatan = kekuatan;
        this.namaIndustriPendaftar = namaIndustriPendaftar;
        this.namaObatKomparator = namaObat;
        this.namaIndustriProdusenObatKomparator = namaIndustriProdusen;
        this.aktif = aktif;
        this.files = files;
    }

    public int getId() {
        return this.id;
    }

    public String getNamaZatAktif() {
        return this.namaZatAktif;
    }

    public String getBentukSediaan() {
        return this.bentukSediaan;
    }

    public String getKekuatan() {
        return this.kekuatan;
    }

    public String getNamaIndustriPendaftar() {
        return this.namaIndustriPendaftar;
    }

    public String getNamaObatKomparator() {
        return this.namaObatKomparator;
    }

    public String getNamaIndustriProdusenObatKomparator() {
        return this.namaIndustriProdusenObatKomparator;
    }

    public String getAktif() {
        return this.aktif;
    }

    public String getFiles() {
        return this.files;
    }
}
