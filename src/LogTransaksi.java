import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

public class LogTransaksi extends JFrame{
    private JTable table1;
    private JButton backButton;
    private JPanel logPanel;
    private User dataUser;

    private Connection conn = connection.getConnection();
    PreparedStatement insert;

    String[] columnNames = {"Id", "Nama Barang", "Quantity", "Harga Barang", "Total", "Admin", "Package"};

    public LogTransaksi(User objUser) {
        setContentPane(logPanel);
        setSize(1920, 1080);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        this.dataUser = objUser;
        table();
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(dataUser.getuLevel() == 1) {
                    setVisible(false);
                    formAdmin fa = new formAdmin(dataUser);
                } else {
                    setVisible(false);
                    home hOne = new home(dataUser);
                }
            }
        });
    }

    private void table() {
        int counter;
        try {
            insert = conn.prepareStatement("select * from log_transaksi");
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
                    vector.add(rs.getString("nama_barang"));
                    vector.add(String.valueOf(rs.getInt("quantity")));
                    vector.add(String.valueOf(rs.getInt("harga_barang")));
                    vector.add(String.valueOf(rs.getInt("total_harga_per_barang")));
                    vector.add(String.valueOf(rs.getInt("admin")));
                    vector.add(rs.getString("createdAt"));
                }
                tblModel.addRow(vector);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
