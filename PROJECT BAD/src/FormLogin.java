import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class FormLogin extends JFrame implements ActionListener{
	
	private JTextField txtUser, txtPass;
	private JPanel panelForm = new JPanel(); // setLayout di bawah
	private JPanel panelButton = new JPanel(new FlowLayout());
	private JPanel panelTitle = new JPanel(new FlowLayout());
	private JLabel lblLogin, lblUsername, lblPassword;
	String sql;
	MySQLConnection db = new MySQLConnection();
	
	public FormLogin() {
		config();
		
		lblLogin = new JLabel("Login");
		lblLogin.setForeground(Color.WHITE);
		panelTitle.add(lblLogin);
		
		panelForm.setLayout(new GridLayout (3,2,0,4));
		panelForm.setBorder(new EmptyBorder(10,20,10,20));
		
		// label username
		lblUsername = new JLabel("Username: ");
		lblUsername.setForeground(Color.WHITE);
		txtUser = new JTextField();
		panelForm.add(lblUsername);
		panelForm.add(txtUser);
		
		// label password
		lblPassword = new JLabel("Password: ");
		lblPassword.setForeground(Color.WHITE);
		txtPass = new JPasswordField();
		panelForm.add(lblPassword);
		panelForm.add(txtPass);
		
		// button
		JButton buttonLogin = new JButton("Login");
		panelButton.add(buttonLogin);
		JButton buttonRegister = new JButton ("Register");
		panelButton.add(buttonRegister);
		
		panelTitle.setBackground(Color.GRAY);
		panelForm.setBackground(Color.GRAY);
		panelButton.setBackground(Color.GRAY);
		
		add(panelTitle, BorderLayout.NORTH);
		add(panelForm, BorderLayout.CENTER);
		add(panelButton, BorderLayout.SOUTH);
		setVisible(true);
		
		buttonLogin.addActionListener(this);
		buttonRegister.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			dispose();
			new FormRegister().setVisible(true);
			
		}
	});
	}
	
	private void config() {
		setTitle("Stim");
		setSize(400, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if(txtUser.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Username cannot be Empty!");
		} else if(txtPass.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Password cannot be Empty!");
		} else {
			try {
	            sql = "SELECT * FROM user WHERE username='"+txtUser.getText()+"' AND password='"+txtPass.getText()+"'";
	            ResultSet user = db.execQuery(sql);
	            if(user.next()){
	                if(txtUser.getText().equals(user.getString("username")) && txtPass.getText().equals(user.getString("password"))){
	                    JOptionPane.showMessageDialog(null, "Login Success");
	                    Main.userNow = user.getString("userId");
	                 if(getRole(Main.userNow).equals("Player")) {
	                	 new PlayerMainForm().setVisible(true);
	                 }
	                 else {
	                	 new DeveloperMainForm().setVisible(true);
	                 }
	                 dispose();
	                }
	            }else{
	                    JOptionPane.showMessageDialog(null, "Username or Password is Wrong");
	                }
	        } catch (Exception e1) {
	            JOptionPane.showMessageDialog(this, e1.getMessage());
	        }
		}
		
		
		
   }
	public String getRole(String userId) {
		String query = "SELECT * From user where userId = '" + userId + "'";
		try {
			db.rs = db.state.executeQuery(query);
            if(db.rs.next()) {
				
				return db.rs.getString("role");
				
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		return null;
	}
}


	