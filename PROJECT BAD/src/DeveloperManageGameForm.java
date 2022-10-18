
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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

public class DeveloperManageGameForm extends JFrame implements ActionListener, MouseListener{
	
	private MySQLConnection sql = new MySQLConnection();
	private JPanel pnlMain, pnlButton1, pnlButton2, pnlCenter, pnlBottom, pnlForm1, pnlForm2;
	private JLabel lblID, lblName, lblPrice, lblGenre, lblQuantity, lblNewName, lblNewPrice, lblNewGenre, lblNewQty, lblDummy, lblDummy2;
	private JTextField txtID, txtName, txtPrice, txtNewName, txtNewPrice;
	private JSpinner quantity, newQuantity;
	SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 999999999, 1);
	SpinnerNumberModel newModel = new SpinnerNumberModel(0,0,99999999,1);
	private JTable tblGame;
	private JScrollPane scrollPane;
	private JCheckBox term;
	private DefaultTableModel dtm;
	private JButton btnBack, btnDelete, btnUpdate, btnInsert;
	private Vector<Object> columns = new Vector<>();
	private boolean updateButton;
	private JComboBox<String> cmbGenre;
	private JComboBox<String> cmbNewGenre;
	
	String [] genres = {"Select a Genre","FPS", "Adventure", "Mystery"};
	
	public DeveloperManageGameForm() {
		config();
		
		dtm = new DefaultTableModel(columns, 0);
		columns.add("Game ID");
		columns.add("Game Name");
		columns.add("Game Price");
		columns.add("Game Genre");
		columns.add("Game Quantity");
		
		tblGame = new JTable(dtm);
		scrollPane = new JScrollPane(tblGame);
		
		pnlMain = new JPanel(new BorderLayout());
		pnlButton1 = new JPanel(new FlowLayout());
		pnlButton2 = new JPanel(new FlowLayout());
		pnlCenter = new JPanel(new GridLayout(2,1,10,10));
		pnlBottom = new JPanel(new GridLayout(1,2,10,10));
		pnlForm1 = new JPanel(new GridLayout(6,2,10,10));
		pnlForm2 = new JPanel(new GridLayout(6,2,10,10));
		
		lblID = new JLabel("Game ID");
		lblName = new JLabel("Game Name");
		lblPrice = new JLabel("Game Price");
		lblGenre = new JLabel("Game Genre");
		lblQuantity = new JLabel("Game Quantity");
		
		txtID = new JTextField();
		txtName = new JTextField();
		txtPrice = new JTextField();
	
		cmbGenre = new JComboBox<>(genres);
		
		quantity = new JSpinner(model);
		
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
		pnlForm1.add(setFormComponent(lblPrice));
		pnlForm1.add(setFormComponent(txtPrice));
		pnlForm1.add(setFormComponent(lblGenre));
		pnlForm1.add(setFormComponent(cmbGenre));
		pnlForm1.add(setFormComponent(lblQuantity));
		pnlForm1.add(setFormComponent(quantity));
		pnlForm1.add(setFormComponent(lblDummy));
		pnlForm1.add(pnlButton1, BorderLayout.CENTER);
		
		lblNewName = new JLabel("New Game Name");
		lblNewPrice = new JLabel("New Game Price");
		lblNewGenre = new JLabel("New Game Genre");
		lblNewQty = new JLabel("New Game Quantity");
		
		txtNewName = new JTextField();
		txtNewPrice = new JTextField();
		newQuantity = new JSpinner(newModel);
		
		cmbNewGenre = new JComboBox<>(genres);
		
		lblDummy2 = new JLabel();
		
		btnInsert = new JButton("Insert");
		pnlButton2.add(btnInsert);
		
		pnlForm2.add(setFormComponent(lblNewName));
		pnlForm2.add(setFormComponent(txtNewName));
		pnlForm2.add(setFormComponent(lblNewPrice));
		pnlForm2.add(setFormComponent(txtNewPrice));
		pnlForm2.add(setFormComponent(lblNewGenre));
		pnlForm2.add(setFormComponent(cmbNewGenre));
		pnlForm2.add(setFormComponent(lblNewQty));
		pnlForm2.add(setFormComponent(newQuantity));
		pnlForm2.add(setFormComponent(lblDummy2));
		pnlForm2.add(pnlButton2, BorderLayout.EAST);
		
		pnlBottom.add(pnlForm1);
		pnlBottom.add(pnlForm2);
		
		pnlCenter.add(scrollPane);
		pnlCenter.add(pnlBottom);
		
		pnlMain.add(pnlCenter);
		
		add(pnlMain);
		
		setVisible(true);
		
		txtID.setEnabled(false);
		
		tblGame.addMouseListener(this);
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
		String query = "SELECT * From game gm JOIN genre gr ON gm.genreId = gr.genreId";
	    sql.rs = sql.execQuery(query);
		try {
			Vector<Object> row;
		
			while(sql.rs.next()) {
				row = new Vector<>();
				row.add(sql.rs.getString("gameId"));
				row.add(sql.rs.getString("name"));				
				row.add(sql.rs.getInt("price"));				
				row.add(sql.rs.getString("genreName"));
				row.add(sql.rs.getInt("quantity"));
				dtm.addRow(row);
			
			}
		
			tblGame.setModel(dtm);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private void fillField() {
		int selectedRow = tblGame.getSelectedRow();
		
		if(selectedRow != -1) {
			txtID.setText(tblGame.getValueAt(selectedRow, 0) + "");
			txtName.setText(tblGame.getValueAt(selectedRow,1) + "");
			txtPrice.setText(tblGame.getValueAt(selectedRow,2) + "");
			cmbGenre.setSelectedItem(tblGame.getValueAt(selectedRow,3));
			quantity.setValue(tblGame.getValueAt(selectedRow, 4));
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
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if(arg0.getSource() == tblGame) {
			fillField();
		}
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// UPDATE
		String genre = "";
		String gameId = txtID.getText();
		String name = txtName.getText();
		String genreName = String.valueOf(genres[cmbGenre.getSelectedIndex()]);
		int Quantity = (int)quantity.getValue();
		String priceValidation = txtPrice.getText();
		int priceValidation2 = 0;
		
		String nameValidation = txtName.getText();
		String gameName = txtName.getText();
		
		if(e.getSource() == btnUpdate) {
			if(txtID.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "Please Select a Game!");
			}
			if(nameValidation.length() < 5 || nameValidation.length() > 30) {
				JOptionPane.showMessageDialog(this, "Name must be between 5 - 30 Characters!");
			}
			if(checkGameExists(gameName)) {
				JOptionPane.showMessageDialog(this, "Game name Already Exists");
			}
			try {
				try {
					priceValidation2 = Integer.parseInt(priceValidation);
				} catch (Exception e1) {
				   JOptionPane.showMessageDialog(this, "Price must be Numeric");
				}
				
				if(priceValidation2 <= 0) {
					JOptionPane.showMessageDialog(this, "Price must be > 0");
				}
			} catch (Exception e2) {
					
			}
			if(Quantity <= 0){
				JOptionPane.showMessageDialog(this, "Quantity must be > 0");
			}
			
			String query2 = "SELECT * FROM genre WHERE genreName ='"+genreName+"'";
	        ResultSet user2 = sql.execQuery(query2);
	        try {
				if(user2.next()) {
					genre = user2.getString("genreId");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
	 
			sql.execDeveloperUpdate(gameId, name, priceValidation2, genre, Quantity);
			JOptionPane.showMessageDialog(this, "Update Success");
			fillData();	
			
		}
		
		// DELETE
		if(e.getSource() == btnDelete) {
			if(txtID.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "Please Select a Game!");
			}
			
			sql.execDeveloperDelete(txtID.getText());
			JOptionPane.showMessageDialog(this, "Delete Success");
			fillData();
		}
		
		// INSERT
		String newGameName = txtNewName.getText();
		String newGenreName = String.valueOf(genres[cmbGenre.getSelectedIndex() + 1]);
		int newqty = (int) newQuantity.getValue();
		String priceValidation3 = txtNewPrice.getText();
		int priceValidation4 = 0;
		
		if(e.getSource() == btnInsert) {
			if (txtNewName.getText().length() < 5 || txtNewName.getText().length() > 30) {
				JOptionPane.showMessageDialog(this, "Name must be between 5 - 30 Characters!");
			}
			if(checkGameExists(newGameName)) {
				JOptionPane.showMessageDialog(this, "Game name Already Exists");
			}
			try {
				try {
					priceValidation4 = Integer.parseInt(priceValidation3);
				} catch (Exception e1) {
				   JOptionPane.showMessageDialog(this, "Price must be Numeric");
				}
				
				if(priceValidation4 <= 0) {
					JOptionPane.showMessageDialog(this, "Price must be > 0");
				}
			} catch (Exception e2) {
					
			}
			if(cmbNewGenre.getSelectedItem() == "Select a Genre") {	
				JOptionPane.showMessageDialog(this, "please select a genre");
			}
			if(newqty <= 0){
				JOptionPane.showMessageDialog(this, "Quantity must be > 0");
			}
		
			String query2 = "SELECT * FROM genre WHERE genreName ='"+newGenreName+"'";
			ResultSet user2 = sql.execQuery(query2);
			try {
				if(user2.next()) {
					genre = user2.getString("genreId");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			Random rand = new Random();
			String newId = "GAME"  + rand.nextInt(9) + rand.nextInt(9) + rand.nextInt(9);
			sql.execDeveloperInsert(newId, newGameName, priceValidation4, genre, newqty);
			JOptionPane.showMessageDialog(this, "Insert Success");
			fillData();
		}
	}
	
	public boolean checkGameExists(String gameName) {
		String query = "SELECT * From game where name = '" + gameName + "'";
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