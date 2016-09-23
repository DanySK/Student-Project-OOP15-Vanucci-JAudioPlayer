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

import controller.data.DataController;

import javax.swing.DefaultListModel;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
	
	private DefaultListModel<String> listModel;
	
	private JTextField nameIn = new JTextField();
	private JList<String> tracks;
	private final JButton add = new JButton("AGGIUNGI");
	
	public PlaylistAdder(){
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
		listModel = new DefaultListModel<>();
		downPanel.setLayout(new BorderLayout());
		tracks = new JList<String>(listModel);
		downPanel.add(new JScrollPane(tracks));
		footer.add(add, BorderLayout.WEST);
		JRootPane root = SwingUtilities.getRootPane(add);
		setFont(new Font("Tahoma", Font.BOLD, 14));
		root.setDefaultButton(add);
		this.setLocationRelativeTo(null);
	}
	
	public String getInputName(){
		return nameIn.getText();
	}
	
	public List<String> getSelected(){
		return new ArrayList<>(tracks.getSelectedValuesList());
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
	
	public void refreshList(List<String> trackNames){
		listModel.clear();
		for(String name: trackNames){
			listModel.addElement(name);
		}
	}
	
	public void setButtons(ActionListener add){
		this.add.addActionListener(add);
	}
	
	private void reset(){
		this.nameIn.setText("");
	}

	public void showMessage(String title, String message) {
		JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
	}
}