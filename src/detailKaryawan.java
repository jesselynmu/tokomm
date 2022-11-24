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

public class detailKaryawan extends JFrame{
    private JPanel panel1;
    private JTextField tfPassword;
    private JTextField tfUserLevel;
    private JButton addButton;
    private JTextField tfNama;
    private JButton editButton;
    private JButton deleteButton;
    private JTable table1;
    private JButton backButton;

    private Connection conn = connection.getConnection();
    PreparedStatement insert;
    String[] columnNames = {"Id", "Nama", "Password", "UserLevel"};

    public detailKaryawan(User objUser) {
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
                tfPassword.setText(tblModel.getValueAt(selectedIndex,2).toString());
                tfUserLevel.setText(tblModel.getValueAt(selectedIndex,3).toString());
            }
        });
        deleteData();
        updateData();
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                formAdmin fa = new formAdmin(objUser);
                fa.setVisible(true);
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
                    int dialogResult = JOptionPane.showConfirmDialog(null,"Do you want to Delete this User?","Warning",JOptionPane.YES_NO_OPTION);
                    if(dialogResult == JOptionPane.YES_OPTION){
                        insert = conn.prepareStatement("delete from user where id=?");
                        insert.setInt(1,id);
                        insert.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Data Deleted");

                        tfNama.setText("");
                        tfPassword.setText("");
                        tfUserLevel.setText("");
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
                    String nama = tfNama.getText();
                    String password = tfPassword.getText();
                    int u_level = Integer.parseInt(tfUserLevel.getText());

                    connection.getConnection();
                    insert = conn.prepareStatement("update user set nama=?,password=?,u_level=? where id=?");
                    insert.setString(1, nama);
                    insert.setString(2,password);
                    insert.setInt(3,u_level);
                    insert.setInt(4,id);
                    insert.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data Updated");

                    tfNama.setText("");
                    tfPassword.setText("");
                    tfUserLevel.setText("");
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
            insert = conn.prepareStatement("select * from user");
            ResultSet rs = insert.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            counter = rsmd.getColumnCount();
            DefaultTableModel tblModel = (DefaultTableModel) table1.getModel();
            tblModel.setColumnIdentifiers(columnNames);
            tblModel.setRowCount(0);

            while (rs.next()) {
                Vector vector = new Vector();

                for(int i = 1; i <= counter; i++) {
                    vector.add(String.valueOf(rs.getInt("id")));
                    vector.add(rs.getString("nama"));
                    vector.add(rs.getString("password"));
                    vector.add(String.valueOf(rs.getInt("u_level")));
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
                String nama = tfNama.getText();
                String password = tfPassword.getText();
                int u_level = Integer.parseInt(tfUserLevel.getText());

                try {
                    insert = conn.prepareStatement("insert into user(nama, password, u_level)values(?,?,?)");
                    insert.setString(1, nama);
                    insert.setString(2,password);
                    insert.setInt(3,u_level);
                    insert.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data Added");

                    tfNama.setText("");
                    tfPassword.setText("");
                    tfUserLevel.setText("");
                    table();
                } catch (SQLException ex) {
                    Logger.getLogger(form.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
