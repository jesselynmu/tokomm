import javax.swing.*;
import java.sql.SQLException;

public class home extends JFrame{
    private JButton transaksiButton;
    private JButton cekStockButton;
    private JPanel panelHome;

    public home() {
        setContentPane(panelHome);
        setSize(300, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
