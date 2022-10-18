import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

public class DeveloperManageGenreForm extends JFrame implements ActionListener, MouseListener{
	
	private MySQLConnection sql = new MySQLConnection();
	private JPanel pnlMain, pnlButton1, pnlButton2, pnlCenter, pnlBottom, pnlForm1, pnlForm2;
	private JLabel lblID, lblName, lblNewName,lblDummy, lblDummy2;
	private JTextField txtID, txtName, txtNewName;
	private JTable tblGenre;
	private JScrollPane scrollPane;
	private DefaultTableModel dtm;
	private JButton btnBack, btnDelete, btnUpdate, btnInsert;
	private Vector<Object> columns = new Vector<>();
	
	public DeveloperManageGenreForm() {
		config();
		
		dtm = new DefaultTableModel(columns, 0);
		columns.add("Genre ID");
		columns.add("Genre Name");
		
		tblGenre = new JTable(dtm);
		scrollPane = new JScrollPane(tblGenre);
		
		pnlMain = new JPanel(new BorderLayout());
		pnlButton1 = new JPanel(new FlowLayout());
		pnlButton2 = new JPanel(new FlowLayout());
		pnlCenter = new JPanel(new GridLayout(2,1,10,10));
		pnlBottom = new JPanel(new GridLayout(1,2,10,10));
		pnlForm1 = new JPanel(new GridLayout(3,2,10,10));
		pnlForm2 = new JPanel(new GridLayout(2,2,10,10));
		
		lblID = new JLabel("Genre ID");
		lblName = new JLabel("Genre Name");
		
		txtID = new JTextField();
		txtName = new JTextField();
		
		lblDummy = new JLabel();
		
		btnBack = new JButton("Back");
		btnDelete = new JButton("Delete");
		btnUpdate = new JButton("Update");
		pnlButton1.add(btnBack);
		pnlButton1.add(btnDelete);
		pnlButton1.add(btnUpdate);
	
		pnlForm1.add(setFormComponent(lblID));
		pnlForm1.add(setFormComponent(txtID));
		pnlForm1.add(setFormComponent(lblName));
		pnlForm1.add(setFormComponent(txtName));
		pnlForm1.add(setFormComponent(lblDummy));
		pnlForm1.add(pnlButton1, BorderLayout.CENTER);
		
		lblNewName = new JLabel("New Genre Name");
		
		txtNewName = new JTextField();
		
		lblDummy2 = new JLabel();
		
		btnInsert = new JButton("Insert");
		pnlButton2.add(btnInsert);
		
		pnlForm2.add(setFormComponent(lblNewName));
		pnlForm2.add(setFormComponent(txtNewName));
		pnlForm2.add(setFormComponent(lblDummy2));
		pnlForm2.add(pnlButton2, BorderLayout.EAST);
		
		pnlBottom.add(pnlForm1);
		pnlBottom.add(pnlForm2);
		
		pnlCenter.add(scrollPane);
		pnlCenter.add(pnlBottom);
		
		pnlMain.add(pnlCenter);
		
		txtID.setEnabled(false);
		
		tblGenre.addMouseListener(this);
		
		add(pnlMain);
		
		setVisible(true);
		
		
		btnUpdate.addActionListener(this);
		btnDelete.addActionListener(this);
		btnInsert.addActionListener(this);
	    btnBack.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new DeveloperMainForm().setVisible(true);
			}
		});
			
		
		fillData();
	}
	
	private void fillData() {
		dtm = new DefaultTableModel(columns, 0);
		String query = "SELECT * From genre";
	    sql.rs = sql.execQuery(query);
		try {
			Vector<Object> row;
		
			while(sql.rs.next()) {
				row = new Vector<>();
				row.add(sql.rs.getString("genreId"));
				row.add(sql.rs.getString("genreName"));			
				dtm.addRow(row);
			
			}
		
			tblGenre.setModel(dtm);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private void fillField(){
		int selectedRow = tblGenre.getSelectedRow();
		
		if(selectedRow != -1) {
			txtID.setText(tblGenre.getValueAt(selectedRow, 0) + "");
			txtName.setText(tblGenre.getValueAt(selectedRow, 1)+ "");
		}
	}
	
	public JPanel setFormComponent(Component c) {
		JPanel newPanel = new JPanel();
		
		newPanel.add(c);
		
		c.setPreferredSize(new Dimension(200,35));
		
		return newPanel;
	}

	private void config() {
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1280, 720);
		setLocationRelativeTo(null);	
	}
	
	
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent arg0) {
		if(arg0.getSource() == tblGenre) {
			fillField();
		}
		
	}
	
	
	//BERUBAH DISINI
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String genre = "";
		String genreId = txtID.getText();
		String genreName = txtName.getText();
		
		// UPDATE
		if(arg0.getSource() == btnUpdate) {
			if(checkGenreExists(genreName)) {
				JOptionPane.showMessageDialog(this, "Genre Already Exists");
			}
			else if(genreId.length() == 0) {
				JOptionPane.showMessageDialog(this, "Please enter a name!");
			}
	        sql.execGenreUpdate(genreId, genreName);
	        JOptionPane.showMessageDialog(this, "Update Success");
	        fillData();
		}
		
		//DELETE
		if(arg0.getSource() == btnDelete) {
			if(txtID.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "Please select a genre");
			}
			sql.execGenreDelete(txtID.getText());
			JOptionPane.showMessageDialog(this, "Delete Success");
			fillData();
		}
		
		//INSERT
		if(arg0.getSource() == btnInsert) {
			String genreNewName = txtNewName.getText();
			
			if(genreNewName.length() == 0) {
				JOptionPane.showMessageDialog(this, "Genre name must have a name!");
			}
			else if(checkGenreExists(genreNewName)) {
				JOptionPane.showMessageDialog(this, "Genre Already Exists");
			}
			else {
				Random rand = new Random();
				String newId = "GAME" + rand.nextInt(9) + rand.nextInt(9) + rand.nextInt(9);
				sql.execGenreInsert(newId, genreNewName);
       	       	JOptionPane.showMessageDialog(this, "Insert Success");
       	       	fillData();
			}
		}
	}

	//CHECK GENRE NAME
	public boolean checkGenreExists(String genreName) {
		String query = "SELECT * From genre where genreName = '" + genreName + "'";
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