package view.create;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Dialog;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.Font;
import javax.swing.border.EmptyBorder;

import controller.DataController;
import javax.swing.DefaultListModel;

import java.awt.GridLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JScrollPane;

public class PlaylistAdder extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7303346759639884086L;
	
	private final JPanel body = new JPanel();
	private final JPanel footer = new JPanel();
	private final JPanel upPanel = new JPanel();
	private final JPanel downPanel = new JPanel();
	
	private final JLabel nameLabel = new JLabel("Nome Playlist: ");
	
	private JTextField nameIn = new JTextField();
	private JList<String> tracks;
	
	private final JButton add = new JButton("AGGIUNGI");
	
	public PlaylistAdder(){
//		this.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
		this.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		this.setSize(350, 224);
		body.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		getContentPane().add(body, BorderLayout.CENTER);
		body.setLayout(new GridLayout(0, 1, 0, 20));
		
		body.add(upPanel);
		upPanel.setLayout(new GridLayout(2, 0, 0, 0));
		upPanel.add(nameLabel);
		nameLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		upPanel.add(nameIn);
		nameIn.setColumns(10);
		
		body.add(downPanel);
		
		nameLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		footer.setBorder(new EmptyBorder(10, 40, 10, 40));
		getContentPane().add(footer, BorderLayout.SOUTH);
		footer.setLayout(new BorderLayout(0, 0));
		add.setFont(new Font("Tahoma", Font.BOLD, 14));
		DefaultListModel<String> listModel = new DefaultListModel<>();
//		try {
//			Set<String> trackNames = new TreeSet<>((s1, s2)-> s1.compareTo(s2));
//			trackNames.addAll(manager.getTracks().keySet());
//			for(String name: trackNames){
//				System.out.println(name);
//				listModel.addElement(name);
//			}
//		} catch (ClassNotFoundException | IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		downPanel.setLayout(new BorderLayout());
		tracks = new JList<String>(listModel);
		downPanel.add(new JScrollPane(tracks));
		
//		add.addActionListener(e-> {
//			if(nameIn.getText().trim().length() > 0){
//				try {
//					manager.createPlaylist(nameIn.getText(), new ArrayList<>(tracks.getSelectedValuesList()));
//					JOptionPane.showMessageDialog(this, "La playlist è stata salvata correttamente", "Playlist salvata", JOptionPane.INFORMATION_MESSAGE);
//					setVisible(false);
//				} catch(IllegalArgumentException se){
//					JOptionPane.showMessageDialog(this, "Esiste già una playlist con questo nome", "Creazione playlist fallita", JOptionPane.ERROR_MESSAGE);
//				}catch (Exception e1) {
//					JOptionPane.showMessageDialog(null, "Controlla i dati inseriti o riprova", "Qualcosa è andato storto", JOptionPane.ERROR_MESSAGE);
//					e1.printStackTrace();
//				}
//			}
//			else{
//				JOptionPane.showMessageDialog(null, "Inserisci un nome per la playlist", "Dati non validi", JOptionPane.ERROR_MESSAGE);
//			}
//		});
		footer.add(add, BorderLayout.WEST);
		JRootPane root = SwingUtilities.getRootPane(add);
		setFont(new Font("Tahoma", Font.BOLD, 14));
		root.setDefaultButton(add);
		this.setLocationRelativeTo(null);
	}
	
	@Override
	public void setVisible(boolean show){

		if(show == false)
	     {
	         super.setVisible(show);
	         return;
	     }

	     reset();
	     super.setVisible(show);
	     return;
	}
	
	private void reset(){
		this.nameIn.setText("");
	}
}