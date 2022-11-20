import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class form extends JFrame{
    private JPanel panel1;
    private JTable table1;
    private JTextField tfNama;
    private JTextField tfQuantity;
    private JTextField tfHarga;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton backButton;

    private Connection conn = connection.getConnection();
    PreparedStatement insert;
    String[] columnNames = {"Id Stock", "Nama Barang", "Quantity", "Harga"};
    public form(User objUser) {
        setContentPane(panel1);
        setSize(1280, 720);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        initComponents();
        table();
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                DefaultTableModel tblModel = (DefaultTableModel) table1.getModel();
                int selectedIndex = table1.getSelectedRow();
                tfNama.setText(tblModel.getValueAt(selectedIndex,1).toString());
                tfQuantity.setText(tblModel.getValueAt(selectedIndex,2).toString());
                tfHarga.setText(tblModel.getValueAt(selectedIndex,3).toString());
            }
        });
        deleteData();
        updateData();
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                home callHome = new home(objUser);
                callHome.setVisible(true);
            }
        });
    }

    private void deleteData() {
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel tblModel = (DefaultTableModel) table1.getModel();
                int selectedIndex = table1.getSelectedRow();

                try {

                    int id = Integer.parseInt(tblModel.getValueAt(selectedIndex,0).toString());
                    int dialogResult = JOptionPane.showConfirmDialog(null,"Do you want to Delete this stock?","Warning",JOptionPane.YES_NO_OPTION);
                    if(dialogResult == JOptionPane.YES_OPTION){
                        insert = conn.prepareStatement("delete from stock where idstock=?");
                        insert.setInt(1,id);
                        insert.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Data Deleted");

                        tfNama.setText("");
                        tfQuantity.setText("");
                        tfHarga.setText("");
                        table();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(form.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void updateData() {
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel tblModel = (DefaultTableModel) table1.getModel();
                int selectedIndex = table1.getSelectedRow();

                try {

                    int id = Integer.parseInt(tblModel.getValueAt(selectedIndex,0).toString());
                    String namaSembako = tfNama.getText();
                    int quantitySembako = Integer.parseInt(tfQuantity.getText());
                    double hargaSembako = Double.parseDouble(tfHarga.getText());

                    connection.getConnection();
                    insert = conn.prepareStatement("update stock set namabarang=?,quantity=?,harga=? where idstock=?");
                    insert.setString(1, namaSembako);
                    insert.setInt(2,quantitySembako);
                    insert.setDouble(3,hargaSembako);
                    insert.setInt(4,id);
                    insert.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data Updated");

                    tfNama.setText("");
                    tfQuantity.setText("");
                    tfHarga.setText("");
                    tfNama.requestFocus();
                    table();
                } catch (SQLException ex) {
                    Logger.getLogger(form.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }
    private void table() {
        int counter;
        try {
            insert = conn.prepareStatement("select * from stock");
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
                String namaSembako = tfNama.getText();
                int quantitySembako = Integer.parseInt(tfQuantity.getText());
                double hargaSembako = Double.parseDouble(tfHarga.getText());

                try {
                    insert = conn.prepareStatement("insert into stock(namabarang, quantity, harga)values(?,?,?)");
                    insert.setString(1, namaSembako);
                    insert.setInt(2,quantitySembako);
                    insert.setDouble(3,hargaSembako);
                    insert.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data Added");

                    tfNama.setText("");
                    tfQuantity.setText("");
                    tfHarga.setText("");
                    table();
                } catch (SQLException ex) {
                    Logger.getLogger(form.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}