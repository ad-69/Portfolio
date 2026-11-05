package gui;

import authentication.LoginManager;

import javax.swing.*;
import java.awt.*;

public class LoginGUI extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    private LoginManager loginManager;


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public LoginGUI() {

        loginManager =
                new LoginManager();


        setTitle(
                "Personal Message Management System"
        );


        setSize(
                450,
                300
        );


        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );


        setLocationRelativeTo(null);


        createLoginScreen();


        setVisible(true);
    }


    // ==========================================
    // LOGIN SCREEN
    // ==========================================

    private void createLoginScreen() {

        JPanel mainPanel =
                new JPanel();


        mainPanel.setLayout(
                new BoxLayout(
                        mainPanel,
                        BoxLayout.Y_AXIS
                )
        );


        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        30,
                        40,
                        30,
                        40
                )
        );


        // Title
        JLabel titleLabel =
                new JLabel(
                        "Personal Message Manager"
                );


        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );


        titleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        // Subtitle
        JLabel subtitleLabel =
                new JLabel(
                        "Login to manage your messages"
                );


        subtitleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        // Username
        usernameField =
                new JTextField();


        JPanel usernamePanel =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );


        usernamePanel.add(
                new JLabel("Username:"),
                BorderLayout.WEST
        );


        usernamePanel.add(
                usernameField,
                BorderLayout.CENTER
        );


        // Password
        passwordField =
                new JPasswordField();


        JPanel passwordPanel =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );


        passwordPanel.add(
                new JLabel("Password:"),
                BorderLayout.WEST
        );


        passwordPanel.add(
                passwordField,
                BorderLayout.CENTER
        );


        // Login button
        JButton loginButton =
                new JButton(
                        "LOGIN"
                );


        loginButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        loginButton.addActionListener(
                e -> login()
        );


        // Add components
        mainPanel.add(
                titleLabel
        );


        mainPanel.add(
                Box.createVerticalStrut(5)
        );


        mainPanel.add(
                subtitleLabel
        );


        mainPanel.add(
                Box.createVerticalStrut(30)
        );


        mainPanel.add(
                usernamePanel
        );


        mainPanel.add(
                Box.createVerticalStrut(15)
        );


        mainPanel.add(
                passwordPanel
        );


        mainPanel.add(
                Box.createVerticalStrut(25)
        );


        mainPanel.add(
                loginButton
        );


        add(mainPanel);
    }


    // ==========================================
    // LOGIN
    // ==========================================

    private void login() {

        String username =
                usernameField
                        .getText()
                        .trim();


        String password =
                new String(
                        passwordField
                                .getPassword()
                ).trim();


        if (
                username.isEmpty()
                || password.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter username and password.",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        boolean successful =
                loginManager.login(
                        username,
                        password
                );


        if (successful) {

            JOptionPane.showMessageDialog(
                    this,
                    "Login successful!"
            );


            openDashboard();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid username or password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // ==========================================
    // OPEN DASHBOARD
    // ==========================================

    private void openDashboard() {

        dispose();


        new DashboardGUI(
                usernameField
                        .getText()
                        .trim()
        );
    }


    // ==========================================
    // MAIN
    // ==========================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () -> new LoginGUI()
        );
    }
}