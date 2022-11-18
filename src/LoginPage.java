import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame {
    private JPanel LoginPanel;
    private JTextField UsernameField;
    private JPasswordField PasswordField;
    private JButton loginButton;
    private JLabel textFailed;
    private static String[] userCorrect = {"Alex","Brian","Nana","Tigreal"};
    private static String[] passCorrect = {"123123","3205","test123","aaaa"};
    private String alamat[] = {"tytyan","kemang","cipete","mampang prpt"};

    static Karyawan kLogin = new Karyawan();

    public static int isUserCorrect(String userInput, String[] userCorrect){
        for (int i = 0 ; i < userCorrect.length;i++){
            if (userInput.equals(userCorrect[i])){
               return i;

            }
        }

        return 0;
    }

    public static int isPassCorrect(String passInput, String[] passCorrect){
        for (int i = 0 ; i < passCorrect.length;i++){
            if (passInput.equals(passCorrect[i])){
                return  i;
            }
        }
        return 99;
    }

    public static void setKaryawan(int uname, int upass) {
        kLogin.setId(uname);
        kLogin.setNama(userCorrect[uname]);
        kLogin.setAlamat(passCorrect[uname]);
    }






    public LoginPage(){
        setContentPane(LoginPanel);
        setSize(400,400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = UsernameField.getText();
                String pass = PasswordField.getText();
                int userCor = isUserCorrect(user,userCorrect);
                int passCor = isPassCorrect(pass,passCorrect);



                if ( userCor == passCor){
                    setVisible(false);
                    home panelHome = new home(kLogin);
                    setKaryawan(userCor, passCor);

                }
                else {
                    textFailed.setText("PASSWORD OR USERNAME IS INCORRECT.");
                }

            }
        });
    }

}
