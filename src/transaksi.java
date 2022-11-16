import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class transaksi extends JFrame {
    private JPanel panelTransaksi;
    private JButton button1;
    private JTextField tfHarga;
    private JButton addButton;
    private JButton doneButton;
    private JTable table1;

    private JSpinner tfQuantity;
    private JComboBox dataSelect;
    private JTextPane textPane1;

    private Connection conn = connection.getConnection();
    PreparedStatement insert;
    String[] columnNames = {"Id Transaksi", "List Barang", "Quantity", "Total"};
    private List<String> idBarang = new ArrayList<>();
    private List<String> namaBarang = new ArrayList<>();
    private List<Double> harga = new ArrayList<>();
    public transaksi() {
        setContentPane(panelTransaksi);
        setSize(1280, 720);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        int counter;
        initComponents();
        try {
            insert = conn.prepareStatement("select * from stock");
            ResultSet rs = insert.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            counter = rsmd.getColumnCount();

            while (rs.next()) {
                List<String> rsGetter = new ArrayList<>();
                List<String> rsGetterId = new ArrayList<>();
                List<String> rsGetterHarga = new ArrayList<>();
                for(int i = 1; i <= counter; i++) {
                    rsGetter.add(rs.getString("namabarang"));
                    rsGetterId.add(rs.getString("idstock"));
                    rsGetterHarga.add(rs.getString("harga"));
                }
                namaBarang.add(rsGetter.get(0));
                idBarang.add(rsGetterId.get(0));
                harga.add(Double.valueOf(rsGetterHarga.get(0)));
            }
            dataSelect.setModel(new DefaultComboBoxModel<String>(namaBarang.toArray(new String[0])));
            System.out.println(namaBarang);
            System.out.println(harga);

//            State Changed Select Box
//            dataSelect.addItemListener(event -> {
//                // The item affected by the event.
//                String item = (String) event.getItem();
//                textPane1.setText("Affected items: " + item + "\n");
//                if (event.getStateChange() == ItemEvent.SELECTED) {
//                    textPane1.setText(item + " selected\n");
//                }
//                if (event.getStateChange() == ItemEvent.DESELECTED) {
//                    textPane1.setText(item + " deselected\n");
//                }
//            });
            addData();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addData() {
        List<String> namaBarang = new ArrayList<>();
        List<Integer> quantity = new ArrayList<>();
        List<Integer> hargaSembako = new ArrayList<>();

        dataSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nb = (String) dataSelect.getSelectedItem();
                int quantitySembako = Integer.parseInt(tfQuantity.getValue().toString());
                double hargaSembako = Integer.parseInt(tfQuantity.getValue().toString()) * harga.get(nb.equals(namaBarang) ? namaBarang.indexOf(nb) : 0);
                System.out.println(hargaSembako + quantitySembako);
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nb = (String) dataSelect.getSelectedItem();
                int quantitySembako = Integer.parseInt(tfQuantity.getValue().toString());
                double hargaSembako = Integer.parseInt(tfQuantity.getValue().toString()) * harga.get(nb.equals(namaBarang) ? namaBarang.indexOf(nb) : 0);
//                textPane1.setText("nama barang " + nb + "\nquantity : " + quantitySembako + "\nharga : " + hargaSembako);
            }
        });
    }
    private void table() {
        int counter;
        try {
            insert = conn.prepareStatement("select * from transaksi");
            ResultSet rs = insert.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            counter = rsmd.getColumnCount();
            DefaultTableModel tblModel = (DefaultTableModel) table1.getModel();
            tblModel.setColumnIdentifiers(columnNames);
            tblModel.setRowCount(0);

            while (rs.next()) {
                Vector vector = new Vector();

                for(int i = 1; i <= counter; i++) {
                    vector.add(String.valueOf(rs.getInt("idstock")));
                    vector.add(rs.getString("namabarang"));
                    vector.add(String.valueOf(rs.getInt("quantity")));
                    vector.add(String.valueOf(rs.getInt("harga")));
                }
                tblModel.addRow(vector);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initComponents() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int quantitySembako = Integer.parseInt(tfQuantity.getValue().toString());
                double hargaSembako = Double.parseDouble(tfHarga.getText());

                try {
                    insert = conn.prepareStatement("insert into stock(namabarang, quantity, harga)values(?,?,?)");
                    insert.setString(1, (String) dataSelect.getSelectedItem());
                    insert.setInt(2,quantitySembako);
                    insert.setDouble(3,hargaSembako);
                    insert.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data Added");

                    tfQuantity.setValue(0);
                    table();
                } catch (SQLException ex) {
                    Logger.getLogger(form.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }


}
