
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

public class PlayerMainForm extends JFrame implements ActionListener {
	private MySQLConnection sql;
    JDesktopPane desktopPane;
    JMenuBar menubar;
    JMenu menuAccount, menuGames;
    JMenuItem menuLogout, menuBuyGames, menuOwnedGames;

    public PlayerMainForm() {
        desktopPane = new JDesktopPane();

        configMenuBar();
        
        setSize(1280,720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
	
    }

	private void configMenuBar() {
        menubar = new JMenuBar();

        menuAccount = new JMenu("Account");
        menuGames = new JMenu("Games");

        menuLogout = new JMenuItem("Logout");
        menuBuyGames = new JMenuItem("Buy Games");
        menuOwnedGames = new JMenuItem("Owned Games");

        menubar.add(menuAccount);
        menubar.add(menuGames);

        menuAccount.add(menuLogout);

        menuGames.add(menuBuyGames);
        menuGames.add(menuOwnedGames);

        setJMenuBar(menubar);

        setContentPane(desktopPane);

        menuLogout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new FormLogin().setVisible(true);
				
			}
        	
        });
        menuBuyGames.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new PlayerBuyGameForm().setVisible(true);
				
			}
        	
        });
        menuOwnedGames.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new PlayerOwnedGamesForm().setVisible(true);
			}
        	
     
        });
     
    }
	
    @Override
    public void actionPerformed(ActionEvent e) {
    	// TODO Auto-generated method stub
		
    }
    

}