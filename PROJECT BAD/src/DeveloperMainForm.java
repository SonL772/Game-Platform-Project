
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class DeveloperMainForm extends JFrame implements ActionListener {

	JDesktopPane desktopPane;
	JMenuBar menubar;
	JMenu menuAccount, menuManage;
	JMenuItem menuLogout, menuManageGames, menuManageGenre;

	public DeveloperMainForm() {
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
		menuManage = new JMenu("Manage");
		
		menuLogout = new JMenuItem("Logout");
		menuManageGames = new JMenuItem("Manage Games");
		menuManageGenre = new JMenuItem("Manage Genre");
		
		menubar.add(menuAccount);
		menubar.add(menuManage);
		
		menuAccount.add(menuLogout);
		
		menuManage.add(menuManageGames);
		menuManage.add(menuManageGenre);
		
		setJMenuBar(menubar);
		
		setContentPane(desktopPane);
		
		menuLogout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new FormLogin().setVisible(true);
				
			}
        	
        });
		menuManageGames.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new DeveloperManageGameForm().setVisible(true);
				
			}
        	
        });
		menuManageGenre.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new DeveloperManageGenreForm().setVisible(true);
				
			}
        	
        });
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}


