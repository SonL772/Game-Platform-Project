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
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

public class PlayerOwnedGamesForm extends JFrame implements ActionListener, MouseListener{
	
	private MySQLConnection sql = new MySQLConnection();
	private JPanel pnlMain, pnlButton, pnlCenter, pnlForm;
	private JLabel lblID, lblName, lblPrice, lblGenre, lblQuantity, totalSpent;
	private JTextField txtID, txtName, txtPrice, txtGenre, txtQuantity, txtSpent;
	SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 999999999, 1);
	private JTable tblGame;
	private JScrollPane scrollPane;
	private DefaultTableModel dtm;
	private JButton btnBack;
	private Vector<Object> columns = new Vector<>();
	private int total = 0;
	
	public PlayerOwnedGamesForm() {
		config();
		
		dtm = new DefaultTableModel(columns, 0);
		columns.add("Game ID");
		columns.add("Game Name");
		columns.add("Genre");
		columns.add("Quantity");
		columns.add("Price");
		
		tblGame = new JTable(dtm);
		scrollPane = new JScrollPane(tblGame);
		
		pnlMain = new JPanel(new BorderLayout());
		pnlButton = new JPanel(new FlowLayout());
		pnlCenter = new JPanel(new GridLayout(2,1));
		pnlForm = new JPanel(new GridLayout(6,2));
		lblID = new JLabel("Game ID");
		lblName = new JLabel("Game Name");
		lblPrice = new JLabel("Game Price");
		lblGenre = new JLabel("Game Genre");
		lblQuantity = new JLabel("Owned Quantity");
		totalSpent = new JLabel("Total Spent on Games");
		
		txtID = new JTextField();
		txtName = new JTextField();
		txtPrice = new JTextField();
		txtGenre = new JTextField();
		txtQuantity = new JTextField();
		txtSpent = new JTextField();
		
		pnlForm.add(setFormComponent(lblID));
		pnlForm.add(setFormComponent(txtID));
		pnlForm.add(setFormComponent(lblName));
		pnlForm.add(setFormComponent(txtName));
		pnlForm.add(setFormComponent(lblPrice));
		pnlForm.add(setFormComponent(txtPrice));
		pnlForm.add(setFormComponent(lblGenre));
		pnlForm.add(setFormComponent(txtGenre));
		pnlForm.add(setFormComponent(lblQuantity));
		pnlForm.add(setFormComponent(txtQuantity));
		pnlForm.add(setFormComponent(totalSpent));
		pnlForm.add(setFormComponent(txtSpent));
		
		btnBack = new JButton("Back");	
		pnlButton.add(btnBack);
		
		pnlCenter.add(scrollPane);
		pnlCenter.add(pnlForm);
		
		pnlMain.add(pnlCenter);
		pnlMain.add(pnlButton, BorderLayout.SOUTH);
		
		add(pnlMain);
		
		setVisible(true);
		
		btnBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new PlayerMainForm().setVisible(true);
				
			}
        	
        });
		
		txtID.setEnabled(false);
		txtName.setEnabled(false);
		txtPrice.setEnabled(false);
		txtGenre.setEnabled(false);
		txtQuantity.setEnabled(false);
		txtSpent.setEnabled(false);
		
		tblGame.addMouseListener(this);
		
		fillData();
	}
	
	private void fillData() {
		dtm = new DefaultTableModel(columns, 0);
	    String query = "SELECT * From transaction tr JOIN game gm ON tr.gameId = gm.gameId JOIN genre gr ON gm.genreId = gr.genreId WHERE userId ="+ Main.userNow + "";
		sql.rs = sql.execQuery(query);
	    
		try {
			Vector<Object> row;
		
			while(sql.rs.next()) {
				row = new Vector<>();
				row.add(sql.rs.getString("gameId"));
				row.add(sql.rs.getString("name"));
				row.add(sql.rs.getString("genreName"));
				row.add(sql.rs.getInt("gameQuantity"));
				row.add(sql.rs.getInt("price"));
				countTotal(sql.rs.getInt("gameQuantity"),sql.rs.getInt("price"));
				dtm.addRow(row);
			
			}
			tblGame.setModel(dtm);
			txtSpent.setText(String.valueOf(total));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private void countTotal(int quantity, int price) {
		total += quantity * price;
		
	}
	private void fillField() {
		int selectedRow = tblGame.getSelectedRow();
		
		if (selectedRow != -1) {
			txtID.setText(tblGame.getValueAt(selectedRow, 0) + "");
			txtName.setText(tblGame.getValueAt(selectedRow, 1) + "");
			txtGenre.setText(tblGame.getValueAt(selectedRow, 2) + "");
			txtQuantity.setText(tblGame.getValueAt(selectedRow, 3) + "");
		    txtPrice.setText(tblGame.getValueAt(selectedRow, 4) + "");
		    
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
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

}
