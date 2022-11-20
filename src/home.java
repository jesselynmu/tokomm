import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class home extends JFrame{
    private JButton transaksiButton;
    private JButton cekStockButton;
    private JPanel panelHome;
    private JButton logout;
    private User dataUser;
    public home(User objUser) {
        setContentPane(panelHome);
        setSize(400, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        this.dataUser = objUser;

        cekStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                form callForm = new form(dataUser);
                callForm.setVisible(true);
                setVisible(false);
            }
        });
        transaksiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transaksi1 callTransaksi = new transaksi1(dataUser);
                callTransaksi.setVisible(true);
                setVisible(false);
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
