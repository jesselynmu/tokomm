import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class transaksi1 extends JFrame {
    private JPanel panel1;
    private JTable jTable1;
    private JButton button1;
    private JButton addButton;
    private JButton doneButton;
    private JComboBox dataSelect;
    private JSpinner tfQuantity;
    private Connection conn = connection.getConnection();
    PreparedStatement insert;

    private List<String> idBarang = new ArrayList<>();
    private List<String> namaBarang = new ArrayList<>();
    private List<Double> harga = new ArrayList<>();

    String[] columnNames = {"Id Stock", "Nama Barang", "Quantity", "Harga", "Harga Total"};
    public transaksi1() {
        setContentPane(panel1);
        setSize(1280, 720);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
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

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jButton1ActionPerformed(e);
            }
        });
    }
    private int counter = 0;
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        DefaultTableModel tblModel = (DefaultTableModel) jTable1.getModel();
        tblModel.setColumnIdentifiers(columnNames);
        tblModel.addRow(new Object[]

                {
                        counter += 1,
                        dataSelect.getSelectedItem(),
                        tfQuantity.getValue().toString(),
                        harga.get(dataSelect.getSelectedItem().equals(namaBarang) ? namaBarang.indexOf(dataSelect.getSelectedItem()) : 0),
                        Integer.parseInt(tfQuantity.getValue().toString()) * harga.get(dataSelect.getSelectedItem().equals(namaBarang) ? namaBarang.indexOf(dataSelect.getSelectedItem()) : 0),
                });

        int sum = 0;

//        for(int i = 0; i<jTable1.getRowCount(); i++)
//        {
//            sum = sum + Integer.parseInt(jTable1.getValueAt(i, 4).toString());
//        }
        System.out.println(sum);
//        txtotal.setText(Integer.toString(sum));
        tfQuantity.setValue(1);
//        txtpcode.setText("");
//        txtpname.setText("");
//        txtprice.setText("");
//        txtamount.setText("");
//        txtpcode.requestFocus();
    }


//    public void bill()
//    {
////        String total = txtotal.getText();
////        String pay = txtpay.getText();
////        String bal = txtbal.getText();
//
//        DefaultTableModel model = new DefaultTableModel();
//
//        model = (DefaultTableModel)jTable1.getModel();
//
//        txtbill.setText(txtbill.getText() + "******************************************************\n");
//        txtbill.setText(txtbill.getText() + "           POSBILL                                     \n");
//        txtbill.setText(txtbill.getText() + "*******************************************************\n");
//
//        //Heading
//        txtbill.setText(txtbill.getText() + "Product" + "\t" + "Price" + "\t" + "Amount" + "\n"  );
//
//
//        for(int i = 0; i < model.getRowCount(); i++)
//        {
//
//            String pname = (String)model.getValueAt(i, 1);
//            String price = (String)model.getValueAt(i, 3);
//            String amount = (String)model.getValueAt(i, 4);
//
//            txtbill.setText(txtbill.getText() + pname  + "\t" + price + "\t" + amount  + "\n"  );
//
//        }
//
//        txtbill.setText(txtbill.getText() + "\n");
//
//        txtbill.setText(txtbill.getText() + "\t" + "\t" + "Subtotal :" + total + "\n");
//        txtbill.setText(txtbill.getText() + "\t" + "\t" + "Pay :" + pay + "\n");
//        txtbill.setText(txtbill.getText() + "\t" + "\t" + "Balance :" + bal + "\n");
//        txtbill.setText(txtbill.getText() + "\n");
//        txtbill.setText(txtbill.getText() + "*******************************************************\n");
//        txtbill.setText(txtbill.getText() + "           THANK YOU COME AGIN             \n");
//
//
//    }
}
