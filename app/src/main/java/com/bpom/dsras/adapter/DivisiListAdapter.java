package com.bpom.dsras.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bpom.dsras.R;
import com.bpom.dsras.callback.Callback;
import com.bpom.dsras.object.DivisiList;
import com.bpom.dsras.object.Menus;
import com.bpom.dsras.object.Reports;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Created by apridosandyasa on 8/3/16.
 */
public class DivisiListAdapter extends RecyclerView.Adapter<DivisiListAdapter.DivisiListViewHolder> {

    private Context context;
    private HashMap<String, HashMap<String, Object>> divisiListList;
    private Serializable data;
    private Callback callback;

    public DivisiListAdapter(Context context, HashMap<String, HashMap<String, Object>> objects, Serializable object, Callback listener) {
        this.divisiListList = objects;
        this.context = context;
        this.data = object;
        this.callback = listener;
    }

    @Override
    public DivisiListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_divisilist, parent, false);
        DivisiListViewHolder divisiListViewHolder = new DivisiListViewHolder(view);
        return divisiListViewHolder;
    }

    @Override
    public void onBindViewHolder(DivisiListViewHolder holder, int position) {
        if (this.divisiListList.size() > 0) {
            if (((Reports)this.data).getReportUrl().contains("sertifikat_cdob")) {
                holder.tv_po_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("nomor_sertifikat"));
                holder.tv_date_item_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("tanggal_sertifikat"));
                holder.tv_timport_item_divisilist.setText("Nama Sarana Distribusi");
                holder.tv_texport_item_divisilist.setText("Alamat");
                holder.tv_tproduk_item_divisilist.setText("Aktif");
                holder.tv_vimport_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("nama_sarana_distribusi"));
                holder.tv_vexport_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("alamat"));
                holder.tv_vproduk_item_divisilist.setText(((String) this.divisiListList.get(String.valueOf(position)).get("aktif")).equals("") ? "-" :
                        (String) this.divisiListList.get(String.valueOf(position)).get("aktif"));
            }else if (((Reports)this.data).getReportUrl().contains("daftar_obat_komparator_di_setujui")) {
                holder.tv_po_item_divisilist.setText(String.valueOf(this.divisiListList.get(String.valueOf(position)).get("nomor_registrasi")));
                holder.rl_date_item_divisilist.setVisibility(View.INVISIBLE);
                holder.tv_timport_item_divisilist.setText("Nama Industri Produsen Obat Komparator");
                holder.tv_texport_item_divisilist.setText("Nama Obat Komparator");
                holder.tv_tproduk_item_divisilist.setText("Nama Zat Aktif");
                holder.tv_vimport_item_divisilist.setText(String.valueOf(this.divisiListList.get(String.valueOf(position)).get("nama_industri_produsen_obat_komparator")));
                holder.tv_vexport_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("nama_obat_komparator"));
                holder.tv_vproduk_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("nama_zat_aktif"));
            }else if (((Reports)this.data).getReportUrl().contains("sanksi_sarana_distribusi_obat")) {
                holder.tv_po_item_divisilist.setText(String.valueOf(this.divisiListList.get(String.valueOf(position)).get("nomor_surat")) );
                holder.tv_date_item_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("tanggal_surat"));
                holder.tv_timport_item_divisilist.setText("Nama Sarana Distribusi");
                holder.tv_texport_item_divisilist.setText("Alamat");
                holder.tv_tproduk_item_divisilist.setText("Jenis Sanksi");
                holder.tv_vimport_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("nama_sarana_distribusi"));
                holder.tv_vexport_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("alamat"));
                holder.tv_vproduk_item_divisilist.setText(((String) this.divisiListList.get(String.valueOf(position)).get("jenis_sanksi")).equals("") ? "-" :
                        (String) this.divisiListList.get(String.valueOf(position)).get("jenis_sanksi"));
            }else if (((Reports)this.data).getReportUrl().contains("pengembalian_nie_format_pkpo")) {
                holder.tv_po_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("nomor_surat"));
                holder.tv_date_item_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("tanggal_surat"));
                holder.tv_timport_item_divisilist.setText("Nama Produsen");
                holder.tv_texport_item_divisilist.setText("Bentuk Sediaan");
                holder.tv_tproduk_item_divisilist.setText("Status");
                holder.tv_vimport_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("nama_produsen"));
                holder.tv_vexport_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("bentuk_sediaan"));
                holder.tv_vproduk_item_divisilist.setText(String.valueOf(this.divisiListList.get(String.valueOf(position)).get("status")));
            }else if (((Reports)this.data).getReportUrl().contains("hasil_uji_be")) {
                holder.tv_po_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("nama_pendaftar"));
                holder.tv_date_item_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("tanggal_penerimaan"));
                holder.tv_timport_item_divisilist.setText("Nama Obat Uji Be");
                holder.tv_texport_item_divisilist.setText("Lab Uji Be");
                holder.tv_tproduk_item_divisilist.setText("Status");
                holder.tv_vimport_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("nama_obat_uji_be"));
                holder.tv_vexport_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("lab_uji_be"));
                holder.tv_vproduk_item_divisilist.setText(((String) this.divisiListList.get(String.valueOf(position)).get("status")).equals("") ? "-" :
                        (String) this.divisiListList.get(String.valueOf(position)).get("status"));
            }else if (((Reports)this.data).getReportUrl().contains("sertifikat_cpob_dicabut")) {
                holder.tv_po_item_divisilist.setText(String.valueOf(this.divisiListList.get(String.valueOf(position)).get("nomor_izin_edar")));
                holder.tv_date_item_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("tanggal_terbit_nie"));
                holder.tv_timport_item_divisilist.setText("Bulan Terakhir Produksi");
                holder.tv_texport_item_divisilist.setText("Tanggal Ed Nie");
                holder.tv_tproduk_item_divisilist.setText("Aktif");
                holder.tv_vimport_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("bulan_terakhir_produksi"));
                holder.tv_vexport_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("tanggal_ed_nie"));
                holder.tv_vproduk_item_divisilist.setText(((String) this.divisiListList.get(String.valueOf(position)).get("aktif")).equals("") ? "-" :
                        (String) this.divisiListList.get(String.valueOf(position)).get("aktif"));
            }else if (((Reports)this.data).getReportUrl().contains("sertifikat_cpob_terbit")) {
                holder.tv_po_item_divisilist.setText(String.valueOf(this.divisiListList.get(String.valueOf(position)).get("nomor_izin_edar")));
                holder.tv_date_item_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("tanggal_terbit_nie"));
                holder.tv_timport_item_divisilist.setText("Bulan Terakhir Produksi");
                holder.tv_texport_item_divisilist.setText("Tanggal Ed Nie");
                holder.tv_tproduk_item_divisilist.setText("Aktif");
                holder.tv_vimport_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("bulan_terakhir_produksi"));
                holder.tv_vexport_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("tanggal_ed_nie"));
                holder.tv_vproduk_item_divisilist.setText(((String) this.divisiListList.get(String.valueOf(position)).get("aktif")).equals("") ? "-" :
                        (String) this.divisiListList.get(String.valueOf(position)).get("aktif"));
            }else if (((Reports)this.data).getReportUrl().contains("data_realisasi_impor_ekspor_npp")) {
                holder.tv_po_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("nama_produk"));
                holder.tv_date_item_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("tanggal_realisasi"));
                holder.tv_timport_item_divisilist.setText("Bets Produk");
                holder.tv_texport_item_divisilist.setText("Jumlah Realisasi");
                holder.tv_tproduk_item_divisilist.setText("Aktif");
                holder.tv_vimport_item_divisilist.setText((this.divisiListList.get(String.valueOf(position)).get("bets_produk") != null) ? String.valueOf(this.divisiListList.get(String.valueOf(position)).get("bets_produk")) : "-");
                holder.tv_vexport_item_divisilist.setText(String.valueOf(this.divisiListList.get(String.valueOf(position)).get("jumlah_realisasi")));
                holder.tv_vproduk_item_divisilist.setText(((String) this.divisiListList.get(String.valueOf(position)).get("aktif")).equals("") ? "-" :
                        (String) this.divisiListList.get(String.valueOf(position)).get("aktif"));
            }else if (((Reports)this.data).getReportUrl().contains("sanksi_sarana_npp_oot")) {
                holder.tv_po_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("sarana_yang_diperiksa"));
                holder.tv_date_item_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("tanggal_pemeriksaan"));
                holder.tv_timport_item_divisilist.setText("Kota Sarana Yang Diperiksa");
                holder.tv_texport_item_divisilist.setText("Tanggal Tindak Lanjut");
                holder.tv_tproduk_item_divisilist.setText("Aktif");
                holder.tv_vimport_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("kota_sarana_yang_diperiksa"));
                holder.tv_vexport_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("tanggal_tindak_lanjut"));
                holder.tv_vproduk_item_divisilist.setText(((String) this.divisiListList.get(String.valueOf(position)).get("aktif")).equals("") ? "-" :
                        (String) this.divisiListList.get(String.valueOf(position)).get("aktif"));
            }else if (((Reports)this.data).getReportUrl().contains("ppo")) {
                holder.tv_po_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("nama_zat_aktif"));
                holder.tv_date_item_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("tanggal_disetujui_ppo"));
                holder.tv_timport_item_divisilist.setText("Golongan Semula");
                holder.tv_texport_item_divisilist.setText("Golongan Baru");
                holder.tv_tproduk_item_divisilist.setText("Pembatasan");
                holder.tv_vimport_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("golongan_semula"));
                holder.tv_vexport_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("golongan_baru"));
                holder.tv_vproduk_item_divisilist.setText((this.divisiListList.get(String.valueOf(position)).get("pembatasan") != null) ? String.valueOf(this.divisiListList.get(String.valueOf(position)).get("pembatasan")) : "-");
            }else if (((Reports)this.data).getReportUrl().contains("informasi_obat")) {
                holder.tv_po_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("nama_zat_aktif"));
                holder.tv_date_item_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("golongan_obat"));
                holder.tv_timport_item_divisilist.setText("Posologi");
                holder.tv_texport_item_divisilist.setText("Kontraindikasi");
                holder.tv_tproduk_item_divisilist.setText("Aktif");
                holder.tv_vimport_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("posologi"));
                holder.tv_vexport_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("kontraindikasi"));
                holder.tv_vproduk_item_divisilist.setText(((String) this.divisiListList.get(String.valueOf(position)).get("aktif")).equals("") ? "-" :
                        (String) this.divisiListList.get(String.valueOf(position)).get("aktif"));
            }else if (((Reports)this.data).getReportUrl().contains("bbo")) {
                holder.tv_po_item_divisilist.setText(String.valueOf(this.divisiListList.get(String.valueOf(position)).get("hs_code")));
                holder.tv_date_item_item_divisilist.setText(String.valueOf(this.divisiListList.get(String.valueOf(position)).get("bentuk_sediaan")));
                holder.tv_timport_item_divisilist.setText("Nama Bahan Baku");
                holder.tv_texport_item_divisilist.setText("Nama Produsen");
                holder.tv_tproduk_item_divisilist.setText("Aktif");
                holder.tv_vimport_item_divisilist.setText(String.valueOf(this.divisiListList.get(String.valueOf(position)).get("nama_bahan_baku")));
                holder.tv_vexport_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("nama_produsen"));
                holder.tv_vproduk_item_divisilist.setText(((String) this.divisiListList.get(String.valueOf(position)).get("aktif")).equals("") ? "-" :
                        (String) this.divisiListList.get(String.valueOf(position)).get("aktif"));
            }else if (((Reports)this.data).getReportUrl().contains("sanksi_sarana_produksi_obat")) {
                holder.tv_po_item_divisilist.setText(String.valueOf(this.divisiListList.get(String.valueOf(position)).get("nama_industri_farmasi")));
                holder.tv_date_item_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("aktif"));
                holder.tv_timport_item_divisilist.setText("Alamat");
                holder.tv_texport_item_divisilist.setText("Sanksi");
                holder.tv_tproduk_item_divisilist.setText("Fasilitas");
                holder.tv_vimport_item_divisilist.setText((this.divisiListList.get(String.valueOf(position)).get("alamat") != null) ? String.valueOf(this.divisiListList.get(String.valueOf(position)).get("alamat")) : "-");
                holder.tv_vexport_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("sanksi"));
                holder.tv_vproduk_item_divisilist.setText((this.divisiListList.get(String.valueOf(position)).get("fasilitas") != null) ? String.valueOf(this.divisiListList.get(String.valueOf(position)).get("fasilitas")) : "-");
            }else if (((Reports)this.data).getReportUrl().contains("recall_obat")) {
                holder.tv_po_item_divisilist.setText(String.valueOf(this.divisiListList.get(String.valueOf(position)).get("nomor_surat_recall")));
                holder.tv_date_item_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("tanggal_surat_recall"));
                holder.tv_timport_item_divisilist.setText("Nama Industri Farmasi");
                holder.tv_texport_item_divisilist.setText("Kategori Produk");
                holder.tv_tproduk_item_divisilist.setText("Nama Obat");
                holder.tv_vimport_item_divisilist.setText((this.divisiListList.get(String.valueOf(position)).get("nama_industri_farmasi") != null) ? String.valueOf(this.divisiListList.get(String.valueOf(position)).get("nama_industri_farmasi")) : "-");
                holder.tv_vexport_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("kategori_produk"));
                holder.tv_vproduk_item_divisilist.setText((this.divisiListList.get(String.valueOf(position)).get("nama_obat") != null) ? String.valueOf(this.divisiListList.get(String.valueOf(position)).get("nama_obat")) : "-");
            }else if (((Reports)this.data).getReportUrl().contains("pengawasan_penandaan_obat")) {
                holder.tv_po_item_divisilist.setText(String.valueOf(this.divisiListList.get(String.valueOf(position)).get("nomor_surat_keluar")));
                holder.tv_date_item_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("tanggal_surat_keluar"));
                holder.tv_timport_item_divisilist.setText("Industri Farmasi");
                holder.tv_texport_item_divisilist.setText("Peringatan");
                holder.tv_tproduk_item_divisilist.setText("Jenis Peringatan");
                holder.tv_vimport_item_divisilist.setText((this.divisiListList.get(String.valueOf(position)).get("industri_farmasi") != null) ? String.valueOf(this.divisiListList.get(String.valueOf(position)).get("industri_farmasi")) : "-");
                holder.tv_vexport_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("peringatan"));
                holder.tv_vproduk_item_divisilist.setText((this.divisiListList.get(String.valueOf(position)).get("jenis_peringatan") != null) ? String.valueOf(this.divisiListList.get(String.valueOf(position)).get("jenis_peringatan")) : "-");
            }else if (((Reports)this.data).getReportUrl().contains("kajian_obat_beredar")) {
                holder.tv_po_item_divisilist.setText(String.valueOf(this.divisiListList.get(String.valueOf(position)).get("nomor")));
                holder.tv_date_item_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("tanggal_surat_keluar"));
                holder.tv_timport_item_divisilist.setText("Nama Obat");
                holder.tv_texport_item_divisilist.setText("Isu Keamanan");
                holder.tv_tproduk_item_divisilist.setText("Zat Aktif");
                holder.tv_vimport_item_divisilist.setText((this.divisiListList.get(String.valueOf(position)).get("nama_obat") != null) ? String.valueOf(this.divisiListList.get(String.valueOf(position)).get("nama_obat")) : "-");
                holder.tv_vexport_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("isu_keamanan"));
                holder.tv_vproduk_item_divisilist.setText((this.divisiListList.get(String.valueOf(position)).get("zat_aktif") != null) ? String.valueOf(this.divisiListList.get(String.valueOf(position)).get("zat_aktif")) : "-");
            }else if (((Reports)this.data).getReportUrl().contains("perubahan_regulasi_napza")) {
                holder.tv_po_item_divisilist.setText(String.valueOf(this.divisiListList.get(String.valueOf(position)).get("nomor_peraturan")));
                holder.tv_date_item_item_divisilist.setText(String.valueOf(this.divisiListList.get(String.valueOf(position)).get("tahun_peraturan")));
                holder.tv_timport_item_divisilist.setText("Judul Peraturan");
                holder.tv_texport_item_divisilist.setText("Jenis Peraturan");
                holder.tv_tproduk_item_divisilist.setText("Aktif");
                holder.tv_vimport_item_divisilist.setText((this.divisiListList.get(String.valueOf(position)).get("judul_peraturan") != null) ? String.valueOf(this.divisiListList.get(String.valueOf(position)).get("judul_peraturan")) : "-");
                holder.tv_vexport_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("jenis_peraturan"));
                holder.tv_vproduk_item_divisilist.setText((this.divisiListList.get(String.valueOf(position)).get("aktif") != null) ? String.valueOf(this.divisiListList.get(String.valueOf(position)).get("aktif")) : "-");
            }else if (((Reports)this.data).getReportUrl().contains("data_import_bahan_obat")) {
                holder.tv_po_item_divisilist.setText(String.valueOf(this.divisiListList.get(String.valueOf(position)).get("nomor_ski")));
                holder.tv_date_item_item_divisilist.setText(String.valueOf(this.divisiListList.get(String.valueOf(position)).get("tanggal_ski")));
                holder.tv_timport_item_divisilist.setText("Nama Produk");
                holder.tv_texport_item_divisilist.setText("Negara Produsen");
                holder.tv_tproduk_item_divisilist.setText("Kemasan");
                holder.tv_vimport_item_divisilist.setText((this.divisiListList.get(String.valueOf(position)).get("nama_produk") != null) ? String.valueOf(this.divisiListList.get(String.valueOf(position)).get("nama_produk")) : "-");
                holder.tv_vexport_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("negara_produsen"));
                holder.tv_vproduk_item_divisilist.setText((this.divisiListList.get(String.valueOf(position)).get("kemasan") != null) ? String.valueOf(this.divisiListList.get(String.valueOf(position)).get("kemasan")) : "-");
            }else if (((Reports)this.data).getReportUrl().contains("data_pemeriksaan_pbf")) {
                holder.tv_po_item_divisilist.setText(String.valueOf(this.divisiListList.get(String.valueOf(position)).get("saran_tindak_lanjut")));
                holder.tv_date_item_item_divisilist.setText(String.valueOf(this.divisiListList.get(String.valueOf(position)).get("tanggal_pemeriksaan")));
                holder.tv_timport_item_divisilist.setText("Nama Sarana");
                holder.tv_texport_item_divisilist.setText("Alamat");
                holder.tv_tproduk_item_divisilist.setText("Tanggal Tindakan");
                holder.tv_vimport_item_divisilist.setText((this.divisiListList.get(String.valueOf(position)).get("nama_sarana") != null) ? String.valueOf(this.divisiListList.get(String.valueOf(position)).get("nama_sarana")) : "-");
                holder.tv_vexport_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("alamat"));
                holder.tv_vproduk_item_divisilist.setText((this.divisiListList.get(String.valueOf(position)).get("tanggal_tindakan") != null) ? String.valueOf(this.divisiListList.get(String.valueOf(position)).get("tanggal_tindakan")) : "-");
            }else if (((Reports)this.data).getReportUrl().contains("data_pengawasan_iklan_obat")) {
                holder.tv_po_item_divisilist.setText(String.valueOf(this.divisiListList.get(String.valueOf(position)).get("nomor_surat_tindak_lanjut")));
                holder.tv_date_item_item_divisilist.setText(String.valueOf(this.divisiListList.get(String.valueOf(position)).get("tanggal_surat_tindak_lanjut")));
                holder.tv_timport_item_divisilist.setText("Nama Produk");
                holder.tv_texport_item_divisilist.setText("Nama Industri Farmasi");
                holder.tv_tproduk_item_divisilist.setText("Status Tindak Lanjut");
                holder.tv_vimport_item_divisilist.setText((this.divisiListList.get(String.valueOf(position)).get("nama_produk") != null) ? String.valueOf(this.divisiListList.get(String.valueOf(position)).get("nama_produk")) : "-");
                holder.tv_vexport_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("nama_industri_farmasi"));
                holder.tv_vproduk_item_divisilist.setText((this.divisiListList.get(String.valueOf(position)).get("status_tindak_lanjut") != null) ? String.valueOf(this.divisiListList.get(String.valueOf(position)).get("status_tindak_lanjut")) : "-");
            }else if (((Reports)this.data).getReportUrl().contains("nie_obat_tidak_diproduksi")) {
                holder.tv_po_item_divisilist.setText(String.valueOf(this.divisiListList.get(String.valueOf(position)).get("nomor_izin_edar")));
                holder.tv_date_item_item_divisilist.setText(String.valueOf(this.divisiListList.get(String.valueOf(position)).get("tanggal_ed_nie")));
                holder.tv_timport_item_divisilist.setText("Nama Obat");
                holder.tv_texport_item_divisilist.setText("Bulan Terakhir Produksi");
                holder.tv_tproduk_item_divisilist.setText("Tanggal Terbit Nie");
                holder.tv_vimport_item_divisilist.setText((this.divisiListList.get(String.valueOf(position)).get("nama_obat") != null) ? String.valueOf(this.divisiListList.get(String.valueOf(position)).get("nama_obat")) : "-");
                holder.tv_vexport_item_divisilist.setText((String) this.divisiListList.get(String.valueOf(position)).get("bulan_terakhir_produksi"));
                holder.tv_vproduk_item_divisilist.setText((this.divisiListList.get(String.valueOf(position)).get("tanggal_terbit_nie") != null) ? String.valueOf(this.divisiListList.get(String.valueOf(position)).get("tanggal_terbit_nie")) : "-");
            }
            holder.tv_po_item_divisilist.setTextColor(Color.parseColor(((Reports) this.data).getColor()));
            ((GradientDrawable) holder.rl_date_item_divisilist.getBackground()).setColor(Color.parseColor(((Reports) this.data).getColor()));
            holder.ll_container_item_divisilist.setOnClickListener(new ActionClick(this.data, position));
        }
    }

    @Override
    public int getItemCount() {
        return this.divisiListList.size();
    }

    private class ActionClick implements View.OnClickListener {

        private Serializable data;
        private int mPosition;

        public ActionClick(Serializable object, int pos) {
            this.data = object;
            this.mPosition = pos;
        }

        @Override
        public void onClick(View v) {
            try {
                ((Reports) this.data).setType("detaildivisilist");
                ((Reports) this.data).setDivisiId(Integer.parseInt(String.valueOf(divisiListList.get(String.valueOf(mPosition)).get("id"))));
                callback.onCallback(this.data);
            } catch (Exception e) {
                Log.d(DivisiListAdapter.class.getSimpleName(), e.getMessage());
            }
        }
    }

    public static class DivisiListViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout ll_container_item_divisilist;
        private RelativeLayout rl_po_item_divisilist, rl_date_item_divisilist;
        private TextView tv_po_item_divisilist, tv_date_item_item_divisilist,
                        tv_vimport_item_divisilist, tv_vexport_item_divisilist,
                        tv_vproduk_item_divisilist, tv_timport_item_divisilist,
                        tv_texport_item_divisilist, tv_tproduk_item_divisilist;

        DivisiListViewHolder(View view) {
            super(view);
            this.ll_container_item_divisilist = (LinearLayout) view.findViewById(R.id.ll_container_item_divisilist);
            this.rl_po_item_divisilist = (RelativeLayout) view.findViewById(R.id.rl_po_item_divisilist);
            this.rl_date_item_divisilist = (RelativeLayout) view.findViewById(R.id.rl_date_item_divisilist);
            this.tv_po_item_divisilist = (TextView) view.findViewById(R.id.tv_po_item_divisilist);
            this.tv_date_item_item_divisilist = (TextView) view.findViewById(R.id.tv_date_item_divisilist);
            this.tv_timport_item_divisilist = (TextView) view.findViewById(R.id.tv_timport_item_divisilist);
            this.tv_texport_item_divisilist = (TextView) view.findViewById(R.id.tv_texport_item_divisilist);
            this.tv_tproduk_item_divisilist = (TextView) view.findViewById(R.id.tv_tproduk_item_divisilist);
            this.tv_vimport_item_divisilist = (TextView) view.findViewById(R.id.tv_vimport_item_divisilist);
            this.tv_vexport_item_divisilist = (TextView) view.findViewById(R.id.tv_vexport_item_divisilist);
            this.tv_vproduk_item_divisilist = (TextView) view.findViewById(R.id.tv_vproduk_item_divisilist);
        }
    }
}
