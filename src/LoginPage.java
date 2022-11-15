import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame {
    private JPanel LoginPanel;
    private JTextField UsernameField;
    private JPasswordField PasswordField;
    private JButton loginButton;
    private JLabel textFailed;
    private String userCorrect [] = {"Alex","Brian","Nana","Tigreal"};
    private String passCorrect[] = {"123123","3205","test123","aaaa"};




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




    public LoginPage(){
        setContentPane(LoginPanel);
        setSize(300,300);
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
                    home panelHome = new home();

                }
                else {
                    textFailed.setText("PASSWORD OR USERNAME IS INCORRECT.");
                }

            }
        });
    }

}
