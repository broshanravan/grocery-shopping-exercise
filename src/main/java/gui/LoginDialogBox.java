package gui;

import Services.Personnel;
import Services.PersonnelImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginDialogBox extends JDialog {


    private int hight = 200;
    private int width = 400;
    JLabel userNameLbl = new JLabel("Username");
    JTextField userNameFld = new JTextField();

    JLabel userNameErrLbl = new JLabel("Please enter a valid username");

    JLabel passwordLbl = new JLabel("Password");
    JPasswordField passwordFld = new JPasswordField();
    JLabel passwordErrLbl = new JLabel("Please enter a valid password");

    JButton loginBtn = new JButton("Login");
    JButton cancelBtn = new JButton("cancel");

    public LoginDialogBox(){
        setupScreen();
    }
    public void setupScreen(){

        setResizable(false);
        getContentPane().setBackground(new Color(255, 255, 242));
        this.setLayout(null);
        this.setSize(width, hight);
        setLocationRelativeTo(null);

        getContentPane().add(userNameLbl);
        getContentPane().add(userNameFld);
        getContentPane().add(userNameErrLbl);

        getContentPane().add(passwordLbl);
        getContentPane().add(passwordFld);
        getContentPane().add(passwordErrLbl);
        userNameErrLbl.setForeground(Color.RED);
        passwordErrLbl.setForeground(Color.RED);

        getContentPane().add(userNameErrLbl);
        getContentPane().add(passwordErrLbl);

        userNameErrLbl.setVisible(false);
        passwordErrLbl.setVisible(false);

        getContentPane().add(loginBtn);
        getContentPane().add(cancelBtn);

        userNameLbl.setBounds(10,10, 150,20);
        userNameFld.setBounds(100,10, 150,20);
        userNameErrLbl.setBounds(10,35, 200,20);

        passwordLbl.setBounds(10,60, 80,20);
        passwordFld.setBounds(100,60, 150,20);
        passwordErrLbl.setBounds(10,85, 200,20);

        loginBtn.setBounds(30,110, 120,20);
        cancelBtn.setBounds(230,110, 120,20);
        loginBtn.addActionListener(buttonListener);
        cancelBtn.addActionListener(buttonListener);

    }

    ActionListener buttonListener = new ActionListener() {
        Personnel personnel = new PersonnelImpl();
        @Override
        public void actionPerformed(ActionEvent e) {

            passwordErrLbl.setVisible(false);
            userNameErrLbl.setVisible(false);
            boolean inputComplete = true;
            if (e.getActionCommand().equals("Login")){
               String username = userNameFld.getText();
               String password = passwordFld.getText();

               if (password == null || "".equals(password.trim())){
                   passwordErrLbl.setVisible(true);
                   inputComplete = false;

               }
               if (username == null || "".equals(username.trim())){
                    userNameErrLbl.setVisible(true);
                   inputComplete = false;
                }
               if(inputComplete) {
                   if (personnel.arecredentialsValid(username,password)) {
                       exit();
                       AdminScreen adminScreen = new AdminScreen();
                       adminScreen.setModal(true);
                       adminScreen.setVisible(true);
                   }

               }


            }else  if(e.getActionCommand().equals("Cancel")){


              /*
               AdminScreen adminScreen = new AdminScreen();
               adminScreen.setModal(true);
               adminScreen.setVisible(true);

              */
            }

        }
    };

    public void exit(){
        this.setVisible(false);
    }


}
