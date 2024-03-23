import java.util.List;
import java.util.ArrayList;

public class Mahasiswa {
    private String nim;
    private String nama;
    private String jenisKelamin;
    private int tahunMasuk;
    private int semester;
    private List<String> mataKuliah;

    public Mahasiswa(String nim, String nama, String jenisKelamin, int tahunMasuk, int semester, List<String> mataKuliah) {
        this.nim = nim;
        this.nama = nama;
        this.jenisKelamin = jenisKelamin;
        this.tahunMasuk = tahunMasuk;
        this.semester = semester;
        this.mataKuliah = new ArrayList<>(mataKuliah);
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public void setTahunMasuk(int tahunMasuk) {
        this.tahunMasuk = tahunMasuk;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public void setMataKuliah(List<String> mataKuliah) {
        this.mataKuliah = mataKuliah;
    }

    public String getNim() {
        return this.nim;
    }

    public String getNama() {
        return this.nama;
    }

    public String getJenisKelamin() {
        return this.jenisKelamin;
    }

    public int getTahunMasuk() {
        return this.tahunMasuk;
    }

    public int getSemester() {
        return this.semester;
    }

    public List<String> getMataKuliah() {
        return this.mataKuliah;
    }

    public void tambahMataKuliah(String mataKuliah) {
        if (this.mataKuliah == null) {
            this.mataKuliah = new ArrayList<>();
        }
        this.mataKuliah.add(mataKuliah);
    }

    public void hapusMataKuliah(String mataKuliah) {
        if (this.mataKuliah != null) {
            this.mataKuliah.remove(mataKuliah);
        }
    }
}
