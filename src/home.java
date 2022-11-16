import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        cekStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                form callForm = new form();
                callForm.setVisible(true);
                setVisible(false);
            }
        });
        transaksiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transaksi1 callTransaksi = new transaksi1();
                callTransaksi.setVisible(true);
                setVisible(false);
            }
        });
    }
}
