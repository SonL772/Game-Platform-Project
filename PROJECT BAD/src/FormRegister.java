import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class FormRegister extends JFrame implements ActionListener {
	private MySQLConnection sql = new MySQLConnection();
	JLabel lblregister, lblusername, lblpassword, lblgender, lblcity, lblrole;
	JPanel paneltitle, panelform, panelbutton, panelgender, panelopsigender, panelrole, panelopsirole, panelusername, panelpassword, paneladdress, panelcity;
	JTextField txtusername, txtpassword;
	JRadioButton radmale, radfemale, radplayer, raddvlpr;
	ButtonGroup groupgender, grouprole;
	JComboBox<String> combocountry;
	JButton btnback, btnregister;
	
	String[] countries = {"Select a Country", "Indonesia", "America", "England", "Malaysia", "Singapore", "South Korean", "German"};
	
	public FormRegister() {
	config();
	
	paneltitle = new JPanel(new FlowLayout());
	panelform = new JPanel(new GridLayout(7, 2, 2, 2));
	panelusername = new JPanel(new GridLayout(1, 2));
	panelpassword = new JPanel(new GridLayout(1, 2));
	paneladdress = new JPanel(new GridLayout(1, 2));
	panelcity = new JPanel(new GridLayout(1, 2));
	panelbutton = new JPanel(new FlowLayout());
	panelgender = new JPanel(new GridLayout(1, 2));
	panelrole = new JPanel(new GridLayout(1, 2));
	
	lblregister = new JLabel("Create An Account");
	paneltitle.add(lblregister);
	
	lblusername = new JLabel("Username");
	txtusername = new JTextField();
	panelusername.add(lblusername);
	panelusername.add(txtusername);
	
	lblpassword = new JLabel("Password");
	txtpassword = new JPasswordField();
	panelpassword.add(lblpassword);
	panelpassword.add(txtpassword);
	
	lblgender = new JLabel("Gender");
	
	panelopsigender = new JPanel();
	radmale = new JRadioButton("Male");
	radmale.setActionCommand("Male");
	radfemale = new JRadioButton("Female");
	radfemale.setActionCommand("Female");
	
	groupgender = new ButtonGroup();
	groupgender.add(radmale);
	groupgender.add(radfemale);
	
	panelopsigender.add(radmale);
	panelopsigender.add(radfemale);
	panelgender.add(lblgender);
	panelgender.add(panelopsigender);
	
	if(radmale.isSelected()) {
		radfemale.setSelected(false);
	}
	else {
		radmale.setSelected(false);
	}
	
	lblcity = new JLabel("City");
	combocountry = new JComboBox<>(countries);
	panelcity.add(lblcity);
	panelcity.add(combocountry);
	
	lblrole = new JLabel("Role");
	
	panelopsirole = new JPanel();
	radplayer = new JRadioButton("Player");
	radplayer.setActionCommand("Player");
	raddvlpr = new JRadioButton("Developer");
	raddvlpr.setActionCommand("Developer");
	
	grouprole = new ButtonGroup();
	grouprole.add(radplayer);
	grouprole.add(raddvlpr);
	
	panelopsirole.add(radplayer);
	panelopsirole.add(raddvlpr);
	panelrole.add(lblrole);
	panelrole.add(panelopsirole);
	
	if(radplayer.isSelected()) {
		raddvlpr.setSelected(false);
	}
	else {
		radplayer.setSelected(false);
	}
	
	btnback = new JButton("Back");
	btnregister = new JButton("Register");
	panelbutton.add(btnback);
	panelbutton.add(btnregister);
	
	panelform.add(setFormComponent(panelusername));
	panelform.add(setFormComponent(panelpassword));
	panelform.add(setFormComponent(paneladdress));
	panelform.add(setFormComponent(panelgender));
	panelform.add(setFormComponent(panelcity));
	panelform.add(setFormComponent(panelrole));
	
	btnregister.addActionListener(this);
	btnback.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			dispose();
			new FormLogin().setVisible(true);
		}
	});
	
	add(panelform, BorderLayout.CENTER);
	add(paneltitle, BorderLayout.NORTH);
	add(panelbutton, BorderLayout.SOUTH);
	
	}
	
	public JPanel setFormComponent(Component c) {
		JPanel newPanel = new JPanel();
		
		newPanel.add(c);
		
		c.setPreferredSize(new Dimension(400, 35));
		
		return newPanel;
	}

	private void config() {
		JPanel panelregister = new JPanel();
		panelregister.setLayout(new GridLayout(3, 2, 5, 4));
		setTitle("Register");
		setSize(500, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	@Override
	public void actionPerformed(ActionEvent src) {
		String username = txtusername.getText();
		String password = txtpassword.getText();
		String gender = String.valueOf(groupgender.getSelection().getActionCommand());
		String country = String.valueOf(combocountry.getSelectedItem());
		String role = String.valueOf(grouprole.getSelection().getActionCommand());
		
		if(username.length() < 5 || username.length() > 15) {
			JOptionPane.showMessageDialog(this, "username length must be at least 5-15 chars");
		}
		else if(txtusername.getText().equals("")){
			JOptionPane.showMessageDialog(this, "Please input username!");
		}
		else if(checkUsernameExists(username)){
				JOptionPane.showMessageDialog(this, "Already Exists");
		}
		if(password.length() < 3 || password.length() > 10) {
			JOptionPane.showMessageDialog(this, "password length must be at least 3-10 chars");
		}
		else if(!radmale.isSelected() && !radfemale.isSelected()) {
			JOptionPane.showMessageDialog(this, "please select a gender");
		}
		else if(combocountry.getSelectedItem() == "Select a Country") {	
			JOptionPane.showMessageDialog(this, "please select a country");
		}
		else if(!radmale.isSelected() && !radfemale.isSelected()){
			JOptionPane.showMessageDialog(this, "please select a role");
		}
		if(src.getSource() == btnregister) {
			sql.execRegister(username, password, gender, country, role);
			JOptionPane.showMessageDialog(this, "Register Success");
		}
		
	}
	

	public boolean checkUsernameExists(String username) {
		String query = "SELECT * From user where username = '" + username + "'";
		try {
			sql.rs = sql.state.executeQuery(query);
            if(sql.rs.next()) {
				
				return true;
				
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		return false;
	}
}