import com.mysql.cj.protocol.Resultset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.time.Year;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    private Database database;

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

        database = new Database();

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
                int selectedTahunMasuk = Integer.parseInt(mahasiswaTable.getModel().getValueAt(selectedIndex, 4).toString());
                int selectedSemester = Integer.parseInt(mahasiswaTable.getModel().getValueAt(selectedIndex, 5).toString());
                String selectedMataKuliah = mahasiswaTable.getModel().getValueAt(selectedIndex, 6).toString();

                // ubah isi textfield dan combo box
                nimField.setText(selectedNim);
                namaField.setText(selectedNama);
                jenisKelaminComboBox.setSelectedItem(selectedJenisKelamin);
                tahunMasukComboBox.setSelectedItem(selectedTahunMasuk);
                semesterComboBox.setSelectedItem(selectedSemester);
                List<String> selectedMataKuliahList = Arrays.stream(selectedMataKuliah.split(","))
                        .map(String::trim)
                        .collect(Collectors.toList());

                int[] selectedIndices = IntStream.range(0, mataKuliahModel.getSize())
                        .filter(i -> selectedMataKuliahList.contains(mataKuliahModel.getElementAt(i).trim()))
                        .toArray();
                mataKuliahList.setSelectedIndices(selectedIndices);

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

        // isi tabel dengan data dari database
        try {
            ResultSet resultSet = database.selectQuery("SELECT * FROM mahasiswa");
            int i = 0;
            while (resultSet.next()) {
                Object[] row = new Object[7];
                row[0] = i + 1;
                row[1] = resultSet.getString("nim");
                row[2] = resultSet.getString("nama");
                row[3] = resultSet.getString("jenis_kelamin");
                row[4] = resultSet.getInt("tahun_masuk");
                row[5] = resultSet.getInt("semester");
                row[6] = resultSet.getString("mata_kuliah");

                temp.addRow(row);
                i++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
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

        if (nim.isEmpty() || nama.isEmpty() || jenisKelamin.isEmpty() || selectedMataKuliah.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field wajib diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            ResultSet rs = database.selectQuery("SELECT nim FROM mahasiswa WHERE nim = '" + nim + "'");
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "NIM '" + nim + "' sudah digunakan sebelumnya.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saat cek NIM: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String mataKuliahString = String.join(",", selectedMataKuliah);

        // ubah data mahasiswa di list
        String sql = String.format(
                "INSERT INTO mahasiswa (nim, nama, jenis_kelamin, tahun_masuk, semester, mata_kuliah) " +
                        "VALUES ('"+ nim +"', '"+ nama +"', '"+ jenisKelamin +"', "+ tahunMasuk +", "+ semester +", '"+ mataKuliahString +"')"
                );

        try {
            // Melakukan query insert
            database.insertUpdateDeleteQuery(sql);

            // update tabel
            mahasiswaTable.setModel(setTable());

            // bersihkan form
            clearForm();

            // feedback
            System.out.println("Insert berhasil!");
            JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saat mengubah data: " + e.getMessage());
        }
    }

    public void updateData() {
        // ambil data dari form
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();
        int tahunMasuk = (int) tahunMasukComboBox.getSelectedItem();
        int semester = (int) semesterComboBox.getSelectedItem();
        List<String> selectedMataKuliah = mataKuliahList.getSelectedValuesList();

        String mataKuliahString = String.join(",", selectedMataKuliah);

        if (nim.isEmpty() || nama.isEmpty() || jenisKelamin.isEmpty() || selectedMataKuliah.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field wajib diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // ubah data mahasiswa di list
        String sql = "UPDATE mahasiswa SET " +
                "nama = '" + nama + "', " +
                "jenis_kelamin = '" + jenisKelamin + "', " +
                "tahun_masuk = " + tahunMasuk + ", " +
                "semester = " + semester + ", " +
                "mata_kuliah = '" + mataKuliahString + "' " +
                "WHERE nim = '" + nim + "'";

        try {
            // Melakukan query insert
            database.insertUpdateDeleteQuery(sql);

            // update tabel
            mahasiswaTable.setModel(setTable());

            // bersihkan form
            clearForm();

            // feedback
            System.out.println("Update Berhasil!");
            JOptionPane.showMessageDialog(null, "Data berhasil diubah");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saat mengubah data: " + e.getMessage());
        }
    }

    public void deleteData() {
        int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus data ini?", "Konfirmasi Penghapusan", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            // ambil data berdasarkan nim
            String nim = mahasiswaTable.getModel().getValueAt(selectedIndex, 1).toString();

            String sql = "DELETE FROM mahasiswa WHERE nim = '" + nim + "'";

            try {
                // jalankan query delete
                database.insertUpdateDeleteQuery(sql);

                // update tabel dengan memuat ulang dari database
                mahasiswaTable.setModel(setTable());

                // bersihkan form
                clearForm();

                // feedback
                System.out.println("Delete berhasil!");
                JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error saat menghapus data: " + e.getMessage());
            }

        }
    }

    public void clearForm() {
        // kosongkan semua textfield dan combo box
        nimField.setText("");
        namaField.setText("");
        jenisKelaminComboBox.setSelectedIndex(0);
        tahunMasukComboBox.setSelectedIndex(0);
        semesterComboBox.setSelectedIndex(0);
        mataKuliahList.clearSelection();

        // ubah button "Update" menjadi "Add"
        addUpdateButton.setText("Add");
        // sembunyikan button delete
        deleteButton.setVisible(false);
        // ubah selectedIndex menjadi -1 (tidak ada baris yang dipilih)
        selectedIndex = -1;
    }
}
