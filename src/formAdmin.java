import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class formAdmin extends JFrame {
    private JButton detailKaryawanButton;
    private JButton cekStockButton;
    private JButton transaksiButton;
    private JButton logout;
    private JButton logTransaksiButton;
    private JPanel jpanelAdmin;
    private User dataUser;

    public formAdmin(User objUser) {
        setContentPane(jpanelAdmin);
        setSize(400, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        this.dataUser = objUser;
        transaksiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transaksi1 callTransaksi = new transaksi1(dataUser);
                callTransaksi.setVisible(true);
                setVisible(false);
            }
        });
        cekStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                form callForm = new form(dataUser);
                callForm.setVisible(true);
                setVisible(false);
            }
        });
        detailKaryawanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                detailKaryawan dk = new detailKaryawan(dataUser);
                dk.setVisible(true);
                setVisible(false);
            }
        });
        logTransaksiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                LoginPage lp = new LoginPage();
                lp.setVisible(true);
            }
        });
    }
}
