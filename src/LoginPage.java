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
    private List<String> uName = new ArrayList<>();
    private List<String> uPass = new ArrayList<>();
    private List<Integer> uLevel = new ArrayList<>();

    int counter;



    private Connection conn = connection.getConnection();
    /*
    
    private static String[] userCorrect = {"Alex","Brian","Nana","Tigreal"};
    private static String[] passCorrect = {"123123","3205","test123","aaaa"};
    */



    static User kLogin = new User();

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

    public static void setKaryawan(int uname, int upass) {


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
                try{
                    get = conn.prepareStatement("select * from user");
                    ResultSet rs = get.executeQuery();
                    ResultSetMetaData rsmd = rs.getMetaData();
                    counter = rsmd.getColumnCount();

                    while (rs.next()) {
                        List<String> rsGetteruName = new ArrayList<>();
                        List<String> rsGetteruPass = new ArrayList<>();
                        List<Integer> rsGetteruLevel = new ArrayList<>();
                        for(int i = 1; i <= counter; i++) {

                            rsGetteruName.add(rs.getString("nama"));
                            rsGetteruPass.add(rs.getString("password"));
                            rsGetteruLevel.add(rs.getInt("u_level"));


                        }
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
                        formAdmin fa = new formAdmin(kLogin);
                        setVisible(false);
                        setKaryawan(userCor,passCor);

                    }
                    else {
                        setVisible(false);
                        home panelHome = new home(kLogin);
                        setKaryawan(userCor, passCor);
                    }


                }
                else {
                    textFailed.setText("PASSWORD OR USERNAME IS INCORRECT.");
                }



            }
        });
    }

}
