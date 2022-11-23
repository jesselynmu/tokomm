import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoginPage extends JFrame {
    private JPanel LoginPanel;
    private JTextField UsernameField;
    private JPasswordField PasswordField;
    private JButton loginButton;
    private JLabel textFailed;
    PreparedStatement get;
    private List<Integer> idUser = new ArrayList<>();
    private List<String> uName = new ArrayList<>();
    private List<String> uPass = new ArrayList<>();
    private List<Integer> uLevel = new ArrayList<>();

    int counter;

    private Connection conn = connection.getConnection();



    static User kLogin = null;

    public static int isUserCorrect(String userInput, List<String> userCorrect){
        for (int i = 0 ; i < userCorrect.size();i++){
            if (userInput.equals(userCorrect.get(i))){
               return i;

            }
        }

        return 99999;
    }

    public static int isPassCorrect(String passInput, List<String> passCorrect){
        for (int i = 0 ; i < passCorrect.size();i++){
            if (passInput.equals(passCorrect.get(i))){
                return  i;
            }
        }
        return 999989;
    }

    public void setKaryawan(int id) {
        kLogin.setId(idUser.get(id));
        kLogin.setNama(uName.get(id));
        kLogin.setuLevel(uLevel.get(id));
    }

    public LoginPage(){
        setContentPane(LoginPanel);
        setSize(650,550);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = UsernameField.getText();
                String pass = PasswordField.getText();
                try{
                    get = conn.prepareStatement("select * from user");
                    ResultSet rs = get.executeQuery();
                    ResultSetMetaData rsmd = rs.getMetaData();
                    counter = rsmd.getColumnCount();

                    while (rs.next()) {
                        List<Integer> rsGetterId = new ArrayList<>();
                        List<String> rsGetteruName = new ArrayList<>();
                        List<String> rsGetteruPass = new ArrayList<>();
                        List<Integer> rsGetteruLevel = new ArrayList<>();
                        for(int i = 1; i <= counter; i++) {
                            rsGetterId.add(rs.getInt("id"));
                            rsGetteruName.add(rs.getString("nama"));
                            rsGetteruPass.add(rs.getString("password"));
                            rsGetteruLevel.add(rs.getInt("u_level"));


                        }
                        idUser.add(Integer.valueOf(rsGetterId.get(0)));
                        uName.add(rsGetteruName.get(0));
                        uPass.add(rsGetteruPass.get(0));
                        uLevel.add(Integer.valueOf(rsGetteruLevel.get(0)));
                    }

                } catch (SQLException event){
                    throw new RuntimeException(event);
                }


                int userCor = isUserCorrect(user,uName);
                int passCor = isPassCorrect(pass,uPass);

                
                if ( userCor == passCor){
                    if (uLevel.get(userCor) == 1){
                        kLogin = new Owner();
                        if(kLogin != null) {
                            setKaryawan(userCor);
                            formAdmin fa = new formAdmin(kLogin);
                            setVisible(false);
                        }

                    }
                    else {
                        kLogin = new Admin();
                        if(kLogin != null) {
                            setKaryawan(userCor);
                            home panelHome = new home(kLogin);
                            setVisible(false);
                        }
                    }


                }
                else {
                    textFailed.setText("PASSWORD OR USERNAME IS INCORRECT.");
                }



            }
        });
    }

}
