import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.time.Year;
import java.util.List;
import java.util.Arrays;

public class Menu extends JFrame{
    public static void main(String[] args) {
        Menu window = new Menu();

        window.setSize(580, 660);

        window.setLocationRelativeTo(null);

        window.setContentPane(window.mainPanel);

        window.getContentPane().setBackground(Color.white);

        window.setVisible(true);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // index baris yang diklik
    private int selectedIndex = -1;
    // list untuk menampung semua mahasiswa
    private ArrayList<Mahasiswa> listMahasiswa;

    private JPanel mainPanel;
    private JTextField nimField;
    private JTextField namaField;
    private JTable mahasiswaTable;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JComboBox jenisKelaminComboBox;
    private JButton deleteButton;
    private JLabel titleLabel;
    private JLabel nimLabel;
    private JLabel namaLabel;
    private JLabel jenisKelaminLabel;
    private JComboBox<Integer> tahunMasukComboBox;
    private JComboBox<Integer> semesterComboBox;
    private JList<String> mataKuliahList;


    // constructor
    public Menu() {
        // inisialisasi listMahasiswa
        listMahasiswa = new ArrayList<>();

        // isi listMahasiswa
        populateList();

        // isi tabel mahasiswa
        mahasiswaTable.setModel(setTable());

        // ubah styling title
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));

        // atur isi combo box
        String[] jenisKelaminData = {"Laki-laki", "Perempuan"};
        jenisKelaminComboBox.setModel(new DefaultComboBoxModel(jenisKelaminData));

        // atur isi tahun masuk
        Integer[] tahun = new Integer[10];
        int currentYear = Year.now().getValue();
        for (int i = 0; i < tahun.length; i++) {
            tahun[i] = currentYear - i;
        }
        tahunMasukComboBox.setModel(new DefaultComboBoxModel(tahun));
        tahunMasukComboBox.setSelectedItem(currentYear);

        // atur isi semester
        Integer[] semesterNumbers = {1, 2, 3, 4, 5, 6, 7, 8};
        semesterComboBox.setModel(new DefaultComboBoxModel(semesterNumbers));
        semesterComboBox.setSelectedIndex(0);

        // atur isi mata kuliah
        DefaultListModel<String> mataKuliahModel = new DefaultListModel<>();
        List<String> daftarMataKuliah = Arrays.asList("Ilmu komputer", "Fisika", "Kimia", "Biologi", "Matematika");
        for (String mk : daftarMataKuliah) {
            mataKuliahModel.addElement(mk);
        }
        mataKuliahList.setModel(mataKuliahModel);
        mataKuliahList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // saat tombol add/update ditekan
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1) {
                    insertData();
                } else {
                    updateData();
                }
            }
        });

        // saat tombol delete ditekan
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex > 0) {
                    deleteData();
                }
            }
        });

        // saat tombol cancel ditekan
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });
        // saat salah satu baris tabel ditekan
        mahasiswaTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // ubah selected index menjadi baris tabel yang diklik
                selectedIndex = mahasiswaTable.getSelectedRow();

                // simpan value textfield dan combo box
                String selectedNim = mahasiswaTable.getModel().getValueAt(selectedIndex, 1).toString();
                String selectedNama = mahasiswaTable.getModel().getValueAt(selectedIndex, 2).toString();
                String selectedJenisKelamin = mahasiswaTable.getModel().getValueAt(selectedIndex,  3).toString();

                // ubah isi textfield dan combo box
                nimField.setText(selectedNim);
                namaField.setText(selectedNama);
                jenisKelaminComboBox.setSelectedItem(selectedJenisKelamin);

                // ubah button "Add" menjadi "Update"
                addUpdateButton.setText("Update");
                // tampilkan button delete
                deleteButton.setVisible(true);
            }
        });
    }

    public final DefaultTableModel setTable() {
        // tentukan kolom tabel
        Object[] column = {"No", "NIM", "Nama", "Jenis Kelamin", "Tahun Masuk", "Semester", "Mata Kuliah"};

        // buat objek tabel dengan kolom yang sudah dibuat
        DefaultTableModel temp = new DefaultTableModel(null, column);

        // isi tabel dengan ListMahasiswa
        for (int i = 0; i < listMahasiswa.size(); i++) {
            Mahasiswa mahasiswa = listMahasiswa.get(i);
            List<String> mataKuliah = mahasiswa.getMataKuliah();
            String mataKuliahStr = String.join(", ", mataKuliah);
            Object[] row = new Object[7];
            row[0] = i + 1;
            row[1] = mahasiswa.getNim();
            row[2] = mahasiswa.getNama();
            row[3] = mahasiswa.getJenisKelamin();
            row[4] = mahasiswa.getTahunMasuk();
            row[5] = mahasiswa.getSemester();
            row[6] = mataKuliahStr;

            temp.addRow(row);
        }

        return temp;
    }

    public void insertData() {
        // ambil value dari textfield dan combobox
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();
        int tahunMasuk = (int) tahunMasukComboBox.getSelectedItem();
        int semester = (int) semesterComboBox.getSelectedItem();
        List<String> selectedMataKuliah = mataKuliahList.getSelectedValuesList();

        // tambahkan data ke dalam list
        listMahasiswa.add(new Mahasiswa(nim, nama, jenisKelamin, tahunMasuk, semester, selectedMataKuliah));

        // update tabel
        mahasiswaTable.setModel(setTable());

        // bersihkan form
        clearForm();

        // feedback
        System.out.println("Insert berhasil!");
        JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan");
    }

    public void updateData() {
        // ambil data dari form
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();
        int tahunMasuk = (int) tahunMasukComboBox.getSelectedItem();
        int semester = (int) semesterComboBox.getSelectedItem();
        List<String> selectedMataKuliah = mataKuliahList.getSelectedValuesList();

        // ubah data mahasiswa di list
        listMahasiswa.get(selectedIndex).setNim(nim);
        listMahasiswa.get(selectedIndex).setNama(nama);
        listMahasiswa.get(selectedIndex).setJenisKelamin(jenisKelamin);
        listMahasiswa.get(selectedIndex).setTahunMasuk(tahunMasuk);
        listMahasiswa.get(selectedIndex).setSemester(semester);
        listMahasiswa.get(selectedIndex).setMataKuliah(selectedMataKuliah);

        // update tabel
        mahasiswaTable.setModel(setTable());

        // bersihkan form
        clearForm();

        // feedback
        System.out.println("Update Berhasil!");
        JOptionPane.showMessageDialog(null, "Data berhasil diubah");
    }

    public void deleteData() {
        int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus data ini?", "Konfirmasi Penghapusan", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            // hapus data dari list
            listMahasiswa.remove(selectedIndex);

            // update tabel
            mahasiswaTable.setModel(setTable());

            // bersihkan form
            clearForm();

            // feedback
            System.out.println("Delete berhasil!");
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
        }
    }

    public void clearForm() {
        // kosongkan semua textfield dan combo box
        nimField.setText("");
        namaField.setText("");
        jenisKelaminComboBox.setSelectedItem("");

        // ubah button "Update" menjadi "Add"
        addUpdateButton.setText("Add");
        // sembunyikan button delete
        deleteButton.setVisible(false);
        // ubah selectedIndex menjadi -1 (tidak ada baris yang dipilih)
        selectedIndex = -1;
    }

    private void populateList() {
        listMahasiswa.add(new Mahasiswa("2203999", "Amelia Zalfa Julianti", "Perempuan", 2022, 4, Arrays.asList("Kalkulus", "Algoritma dan Pemrograman")));
        listMahasiswa.add(new Mahasiswa("2202292", "Muhammad Iqbal Fadhilah", "Laki-laki",2022, 4, Arrays.asList("Kalkulus", "Algoritma dan Pemrograman")));
        listMahasiswa.add(new Mahasiswa("2202346", "Muhammad Rifky Afandi", "Laki-laki",2022, 4, Arrays.asList("Kalkulus", "Algoritma dan Pemrograman")));
        listMahasiswa.add(new Mahasiswa("2210239", "Muhammad Hanif Abdillah", "Laki-laki",2022, 4, Arrays.asList("Kalkulus", "Algoritma dan Pemrograman")));
        listMahasiswa.add(new Mahasiswa("2202046", "Nurainun", "Perempuan",2022, 4, Arrays.asList("Kalkulus", "Algoritma dan Pemrograman")));
        listMahasiswa.add(new Mahasiswa("2205101", "Kelvin Julian Putra", "Laki-laki",2022, 4, Arrays.asList("Kalkulus", "Algoritma dan Pemrograman")));
        listMahasiswa.add(new Mahasiswa("2200163", "Rifanny Lysara Annastasya", "Perempuan",2022, 4, Arrays.asList("Kalkulus", "Algoritma dan Pemrograman")));
        listMahasiswa.add(new Mahasiswa("2202869", "Revana Faliha Salma", "Perempuan",2022, 4, Arrays.asList("Kalkulus", "Algoritma dan Pemrograman")));
        listMahasiswa.add(new Mahasiswa("2209489", "Rakha Dhifiargo Hariadi", "Laki-laki",2022, 4, Arrays.asList("Kalkulus", "Algoritma dan Pemrograman")));
        listMahasiswa.add(new Mahasiswa("2203142", "Roshan Syalwan Nurilham", "Laki-laki",2022, 4, Arrays.asList("Kalkulus", "Algoritma dan Pemrograman")));
        listMahasiswa.add(new Mahasiswa("2200311", "Raden Rahman Ismail", "Laki-laki",2022, 4, Arrays.asList("Kalkulus", "Algoritma dan Pemrograman")));
        listMahasiswa.add(new Mahasiswa("2200978", "Ratu Syahirah Khairunnisa", "Perempuan",2022, 4, Arrays.asList("Kalkulus", "Algoritma dan Pemrograman")));
        listMahasiswa.add(new Mahasiswa("2204509", "Muhammad Fahreza Fauzan", "Laki-laki",2022, 4, Arrays.asList("Kalkulus", "Algoritma dan Pemrograman")));
        listMahasiswa.add(new Mahasiswa("2205027", "Muhammad Rizki Revandi", "Laki-laki",2022, 4, Arrays.asList("Kalkulus", "Algoritma dan Pemrograman")));
        listMahasiswa.add(new Mahasiswa("2203484", "Arya Aydin Margono", "Laki-laki",2022, 4, Arrays.asList("Kalkulus", "Algoritma dan Pemrograman")));
        listMahasiswa.add(new Mahasiswa("2200481", "Marvel Ravindra Dioputra", "Laki-laki",2022, 4, Arrays.asList("Kalkulus", "Algoritma dan Pemrograman")));
        listMahasiswa.add(new Mahasiswa("2209889", "Muhammad Fadlul Hafiizh", "Laki-laki",2022, 4, Arrays.asList("Kalkulus", "Algoritma dan Pemrograman")));
        listMahasiswa.add(new Mahasiswa("2206697", "Rifa Sania", "Perempuan",2022, 4, Arrays.asList("Kalkulus", "Algoritma dan Pemrograman")));
        listMahasiswa.add(new Mahasiswa("2207260", "Imam Chalish Rafidhul Haque", "Laki-laki",2022, 4, Arrays.asList("Kalkulus", "Algoritma dan Pemrograman")));
        listMahasiswa.add(new Mahasiswa("2204343", "Meiva Labibah Putri", "Perempuan",2022, 4, Arrays.asList("Kalkulus", "Algoritma dan Pemrograman")));
    }
}
