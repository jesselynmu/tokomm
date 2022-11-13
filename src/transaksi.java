import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class transaksi extends JFrame {
    private JPanel panel1;
    private JButton button1;
    private JTextField tfQuantity;
    private JTextField tfHarga;
    private JButton addButton;
    private JButton addMoreButton;
    private JTable table1;
    private JComboBox comboBox1;

    private Connection conn = connection.getConnection();
    PreparedStatement insert;

    public transaksi() {
        JCheckBox dataSelect = new JCheckBox();
        try {
            insert = conn.prepareStatement("select * from stock");
            ResultSet rs = insert.executeQuery();
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
