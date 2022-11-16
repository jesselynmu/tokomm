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
    private JButton addButton;
    private JButton backButton;
    private JComboBox dataSelect;
    private JSpinner tfQuantity;
    private JTextField tfTotal;
    private JTextField tfPay;
    private JTextField tfBalance;
    private JButton printBillButton;
    private JTextArea taBill;
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
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                home hOne = new home();
            }
        });
        printBillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bill();
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


    public void bill()
    {
        String total = tfTotal.getText();
        String pay = tfPay.getText();
        String bal = tfBalance.getText();

        DefaultTableModel model = new DefaultTableModel();

        model = (DefaultTableModel)jTable1.getModel();



        taBill.setText(taBill.getText() + "******************************************************\n");
        taBill.setText(taBill.getText() + "          TOTAL BELANJA TOKO MAJU MUNDUR               \n");
        taBill.setText(taBill.getText() + "*******************************************************\n");

        //Heading
        taBill.setText(taBill.getText() + "Product" + "\t" + "Price" + "\t" + "Amount" + "\n"  );
        tfBalance.setText(bal);
        for(int i = 0; i < model.getRowCount(); i++)
        {

            String pname = (String)model.getValueAt(i, 1);
            String price = Double.toString((Double) model.getValueAt(i, 3));
            String amount = Double.toString((Double) model.getValueAt(i, 4));



            taBill.setText(taBill.getText() + pname  + "\t" + price + "\t" + amount  + "\n"  );

        }

        taBill.setText(taBill.getText() + "\n");
        bal = String.valueOf(Double.parseDouble(tfPay.getText()) -Double.parseDouble(tfTotal.getText()));
        if (Double.parseDouble(bal) < 0) {
            JOptionPane.showMessageDialog(null,"INSUFFICIENT MONEY");
            taBill.setText("");
            tfPay.setText("");

        }
        else{


            taBill.setText(taBill.getText() + "\t" + "\t" + "Subtotal :" + total + "\n");
            taBill.setText(taBill.getText() + "\t" + "\t" + "Pay :" + pay + "\n");
            taBill.setText(taBill.getText() + "\t" + "\t" + "Balance :" + bal + "\n");

            taBill.setText(taBill.getText() + "\n");
            taBill.setText(taBill.getText() + "*******************************************************\n");
            taBill.setText(taBill.getText() + "           THANK YOU COME AGAIN             \n");
        }



    }
}
