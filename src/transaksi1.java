import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class transaksi1 extends JFrame {
    private JPanel panel1;
    private JTable jTable1;
    private JButton addButton;
    private JButton backButton;
    private JComboBox dataSelect;
    SpinnerNumberModel disScroll = new SpinnerNumberModel(0,0,10000,1);
    private JSpinner tfQuantity;
    private JTextField tfTotal;
    private JTextField tfPay;
    private JTextField tfBalance;
    private JButton printBillButton;
    private JTextArea taBill;
    private final Connection conn = connection.getConnection();
    PreparedStatement insert;

    private final List<String> idBarang = new ArrayList<>();
    private final List<String> namaBarang = new ArrayList<>();
    private final List<Double> harga = new ArrayList<>();
    private final List<Integer> quantity = new ArrayList<>();
    private final User dataUser;

    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final Date date = new Date();
    String[] columnNames = {"Id Stock", "Nama Barang", "Quantity", "Harga", "Harga Total"};
    public transaksi1(User objUser) {
        setContentPane(panel1);
        setSize(1000, 550);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        tfQuantity.setModel(disScroll);

        this.dataUser = objUser;
        int counter;
        try {

            insert = conn.prepareStatement("select * from stock");
            ResultSet rs = insert.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            counter = rsmd.getColumnCount();

            while (rs.next()) {
                List<String> rsGetter = new ArrayList<>();
                List<String> rsGetterId = new ArrayList<>();
                List<String> rsGetterHarga = new ArrayList<>();
                List<String> rsGetterQuantity = new ArrayList<>();
                for(int i = 1; i <= counter; i++) {
                    rsGetter.add(rs.getString("namabarang"));
                    rsGetterId.add(rs.getString("idstock"));
                    rsGetterHarga.add(rs.getString("harga"));
                    rsGetterQuantity.add(rs.getString("quantity"));

                }
                namaBarang.add(rsGetter.get(0));
                idBarang.add(rsGetterId.get(0));
                harga.add(Double.valueOf(rsGetterHarga.get(0)));
                quantity.add(Integer.valueOf(rsGetterQuantity.get(0)));
            }
            dataSelect.setModel(new DefaultComboBoxModel<>(namaBarang.toArray(new String[0])));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        addButton.addActionListener(e -> jButton1ActionPerformed(e));
        backButton.addActionListener(e -> {
            if(dataUser.getuLevel() == 1) {
                setVisible(false);
                formAdmin fa = new formAdmin(dataUser);
            } else {
                setVisible(false);
                home hOne = new home(dataUser);
            }
        });
        printBillButton.addActionListener(e -> {
            try {
                bill();

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    private int counter = 0;
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {

        tfPay.setText("");
        tfBalance.setText("");
        DefaultTableModel tblModel = (DefaultTableModel) jTable1.getModel();
        tblModel.setColumnIdentifiers(columnNames);
        tblModel.addRow(new Object[]

                {
                        counter += 1,
                        dataSelect.getSelectedItem(),
                        tfQuantity.getValue().toString(),
                        harga.get(Objects.equals(dataSelect.getSelectedItem(), namaBarang) ? namaBarang.indexOf(dataSelect.getSelectedItem()) : 0),
                        Integer.parseInt(tfQuantity.getValue().toString()) * harga.get(Objects.equals(dataSelect.getSelectedItem(), namaBarang) ? namaBarang.indexOf(dataSelect.getSelectedItem()) : 0),
                });

        double sum = 0;

       for(int i = 0; i<jTable1.getRowCount(); i++)
        {
            sum = sum + Double.parseDouble(jTable1.getValueAt(i, 4).toString());
       }

        tfTotal.setText(Double.toString(sum));
        tfQuantity.setValue(0);
        taBill.setText("");
        tfBalance.setText("");
        dataSelect.setSelectedIndex(-1);
    }

    public void bill() throws SQLException {
        String total = tfTotal.getText();
        String pay = tfPay.getText();
        String bal = tfBalance.getText();

        DefaultTableModel model = new DefaultTableModel();

        model = (DefaultTableModel) jTable1.getModel();



        taBill.setText(taBill.getText() + "  ******************************************************\n");
        taBill.setText(taBill.getText() + "            TOTAL BELANJA TOKO MAJU MUNDUR               \n");
        taBill.setText(taBill.getText() + "  *******************************************************\n");
        taBill.setText(taBill.getText() + "  Nama Kasir : " + dataUser.getNama() + "\n");
        //Heading
        taBill.setText(taBill.getText() + "  Quantity"+ "\t" + "Product" + "\t" + "Price" + "\t" + "Amount"  + "\n");

        for (int i = 0; i < model.getRowCount(); i++) {

            String pname = (String) model.getValueAt(i, 1);
            String price = Double.toString((Double) model.getValueAt(i, 3));
            String amount = Double.toString((Double) model.getValueAt(i, 4));
            Integer qty = Integer.parseInt((String) model.getValueAt(i, 2));


            taBill.setText(taBill.getText() +"  "+ qty + "\t" + pname + "\t" + price + "\t" + amount  + "\n");

        }

        taBill.setText(taBill.getText() + "\n");
        bal = String.valueOf(Double.parseDouble(tfPay.getText()) - Double.parseDouble(tfTotal.getText()));
        tfBalance.setText(bal);
        if (Double.parseDouble(bal) < 0) {
            JOptionPane.showMessageDialog(null, "INSUFFICIENT MONEY");
            taBill.setText("");
            tfPay.setText("");

        } else {
            taBill.setText(taBill.getText() + "\t" + "\t" + "Subtotal :" + total + "\n");
            taBill.setText(taBill.getText() + "\t" + "\t" + "Pay :" + pay + "\n");
            taBill.setText(taBill.getText() + "\t" + "\t" + "Balance :" + bal + "\n");

            taBill.setText(taBill.getText() + "\n");
            taBill.setText(taBill.getText() + "  *******************************************************\n");
            taBill.setText(taBill.getText() + "             THANK YOU COME AGAIN             \n");

            if (model.getRowCount() == 1) {
                insert = conn.prepareStatement("insert into log_transaksi(nama_barang, quantity, harga_barang, total_harga_per_barang, admin, createdAt)values(?,?,?,?,?,?)");
                insert.setString(1, (String) model.getValueAt(0, 1));
                insert.setInt(2, Integer.parseInt((String) model.getValueAt(0, 2)));
                insert.setDouble(3, (Double) model.getValueAt(0, 3));
                insert.setDouble(4, (Double) model.getValueAt(0, 4));
                insert.setString(5, dataUser.getNama());
                insert.setString(6, formatter.format(date));
                insert.executeUpdate();
                reduceStock(Integer.parseInt((String) model.getValueAt(0, 2)), model.getValueAt(0, 1));
                model.setRowCount(0);
            } else {
                for (int i = 0; i < model.getRowCount(); i++) {
                    insert = conn.prepareStatement("insert into log_transaksi(nama_barang, quantity, harga_barang, total_harga_per_barang, admin, createdAt)values(?,?,?,?,?,?)");
                    insert.setString(1, (String) model.getValueAt(i, 1));
                    insert.setInt(2, Integer.parseInt((String) model.getValueAt(i, 2)));
                    insert.setDouble(3, (Double) model.getValueAt(i, 3));
                    insert.setDouble(4, (Double) model.getValueAt(i, 4));
                    insert.setString(5, dataUser.getNama());
                    insert.setString(6, formatter.format(date));
                    insert.executeUpdate();
                    reduceStock(Integer.parseInt((String) model.getValueAt(i, 2)), model.getValueAt(i, 1));
                }
                model.setRowCount(0);
            }
        }
    }

    public void reduceStock(int quantityReduce, Object valueAt) throws SQLException {
        if (quantity.get(namaBarang.indexOf(valueAt)) - quantityReduce < 0){
            JOptionPane.showMessageDialog(null,"Stock " + namaBarang.get(namaBarang.indexOf(valueAt)) + " Habis!");
        }
        else{
            insert = conn.prepareStatement("update stock set quantity=? where idstock=?");
            insert.setInt(1, quantity.get(namaBarang.indexOf(valueAt)) - quantityReduce);
            insert.setInt(2, Integer.parseInt(idBarang.get(namaBarang.indexOf(valueAt))));
            insert.executeUpdate();
        }
    }
}
