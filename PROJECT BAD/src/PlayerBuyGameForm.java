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

public class PlayerBuyGameForm extends JFrame implements ActionListener, MouseListener{
	
	private MySQLConnection sql = new MySQLConnection();
	private JPanel pnlMain, pnlButton, pnlCenter, pnlForm;
	private JLabel lblID, lblName, lblPrice, lblGenre, lblQuantity;
	private JTextField txtID, txtName, txtPrice, txtGenre;
	private JSpinner quantity;
	SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 999999999, 1);
	private JTable tblGame;
	private JScrollPane scrollPane;
	private JCheckBox term;
	private DefaultTableModel dtm;
	private JButton btnBack, btnBuyGame;
	private Vector<Object> columns = new Vector<>();
	
	public PlayerBuyGameForm() {
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
		pnlButton = new JPanel(new FlowLayout());
		pnlCenter = new JPanel(new GridLayout(2,1,10,10));
		pnlForm = new JPanel(new GridLayout(6,2,10,10));
		
		lblID = new JLabel("Game ID");
		lblName = new JLabel("Game Name");
		lblPrice = new JLabel("Game Price");
		lblGenre = new JLabel("Game Genre");
		lblQuantity = new JLabel("How many do you want to buy?");
		
		txtID = new JTextField();
		txtName = new JTextField();
		txtPrice = new JTextField();
		txtGenre = new JTextField();
		
		quantity = new JSpinner(model);
		
		term = new JCheckBox("Once bought game cannot be returned");
		
		pnlForm.add(setFormComponent(lblID));
		pnlForm.add(setFormComponent(txtID));
		pnlForm.add(setFormComponent(lblName));
		pnlForm.add(setFormComponent(txtName));
		pnlForm.add(setFormComponent(lblPrice));
		pnlForm.add(setFormComponent(txtPrice));
		pnlForm.add(setFormComponent(lblGenre));
		pnlForm.add(setFormComponent(txtGenre));
		pnlForm.add(setFormComponent(lblQuantity));
		pnlForm.add(setFormComponent(quantity));
		pnlForm.add(setFormComponent(term));
		
		btnBack = new JButton("Back");
		btnBuyGame = new JButton("Buy Game");
		pnlButton.add(btnBack);
		pnlButton.add(btnBuyGame);
		
		pnlCenter.add(scrollPane);
		pnlCenter.add(pnlForm);
		
		pnlMain.add(pnlCenter);
		pnlMain.add(pnlButton, BorderLayout.SOUTH);
		
		add(pnlMain);
		
		setVisible(true);
		
		tblGame.addMouseListener(this);
		
		btnBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new PlayerMainForm().setVisible(true);
				
			}
        	
        });
		
		btnBuyGame.addActionListener(this);
		
		txtID.setEnabled(false);
		txtName.setEnabled(false);
		txtPrice.setEnabled(false);
		txtGenre.setEnabled(false);
		
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
		
		if (selectedRow != -1) {
			txtID.setText(tblGame.getValueAt(selectedRow, 0) + "");
			txtName.setText(tblGame.getValueAt(selectedRow, 1) + "");
			txtPrice.setText(tblGame.getValueAt(selectedRow, 2) + "");
			txtGenre.setText(tblGame.getValueAt(selectedRow, 3) + "");
			
		}		
	}
	
	public JPanel setFormComponent(Component c) {
		JPanel newPanel = new JPanel();
		
		newPanel.add(c);
		
		c.setPreferredSize(new Dimension(450,35));
		
		return newPanel;
	}

	private void config() {
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1280, 720);
		setLocationRelativeTo(null);		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String gameId = txtID.getText();
		int gameQuantity = (int) quantity.getValue();
		int qty = (int) quantity.getValue();
		int stock = 0;
		if(txtID.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "Please Select a Game!");
		}
		String query2 = "SELECT * FROM game WHERE gameId ='"+gameId+"'";
        ResultSet user2 = sql.execQuery(query2);
        try {
			if(user2.next()) {
				stock = user2.getInt("quantity");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		if(qty <= 0 || qty > stock){
			JOptionPane.showMessageDialog(this, "Game quantity cannot be less than 0 or more than stock");
		}
		if(!term.isSelected()) {
			JOptionPane.showMessageDialog(this, "Checkbox must be checked");
		}
		
		if(arg0.getSource() == btnBuyGame) {
			  String query = "SELECT * FROM transaction WHERE userId= " + Main.userNow + " AND gameId ='"+gameId+"'";
	          ResultSet user = sql.execQuery(query);
	          try {
	        	  if(user.next()){
				      int quantity = user.getInt("gameQuantity");
				      int quantity2 = quantity + qty;
				      sql.execUpdateQuantity(quantity2, 3, gameId);
				  }else{
		       	      sql.execBuyTable(Main.userNow, gameId, gameQuantity);
		       	      JOptionPane.showMessageDialog(this, "Game Bought");
				  }
				
				 String query1 = "SELECT * FROM game WHERE gameId ='"+gameId+"'";
		         ResultSet user1 = sql.execQuery(query1);
		         if(user1.next()) {
		        	 stock = user1.getInt("quantity");
		 	         int stock2 = stock - qty;
		 	         sql.execUpdateStock(stock2, gameId); 
		         }
		         fillData();
			} catch (SQLException e) {
				e.printStackTrace();
			}      
		}
        
		Integer.parseInt(Main.userNow);
	}



	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getSource() == tblGame) {
			fillField();
		}
		
	}
}