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

import javax.swing.DefaultListModel;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

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
		this.setSize(232, 285);
		body.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		getContentPane().add(body, BorderLayout.CENTER);
		body.setLayout(new GridLayout(0, 1, 0, 20));
		
		body.add(upPanel);
		nameLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		nameIn.setColumns(10);
		
		body.add(downPanel);
		
		nameLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		GroupLayout gl_upPanel = new GroupLayout(upPanel);
		gl_upPanel.setHorizontalGroup(
			gl_upPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_upPanel.createSequentialGroup()
					.addComponent(nameLabel)
					.addContainerGap(98, Short.MAX_VALUE))
				.addComponent(nameIn, GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
		);
		gl_upPanel.setVerticalGroup(
			gl_upPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_upPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(nameLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(nameIn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(10))
		);
		upPanel.setLayout(gl_upPanel);
		footer.setBorder(new EmptyBorder(10, 40, 10, 40));
		getContentPane().add(footer, BorderLayout.SOUTH);
		footer.setLayout(new BorderLayout(0, 0));
		add.setFont(new Font("Tahoma", Font.BOLD, 14));
		listModel = new DefaultListModel<>();
		downPanel.setLayout(new BorderLayout());
		tracks = new JList<String>(listModel);
		JScrollPane scroller = new JScrollPane(tracks);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		downPanel.add(scroller);
		footer.add(add, BorderLayout.WEST);
		JRootPane root = SwingUtilities.getRootPane(add);
		setFont(new Font("Tahoma", Font.BOLD, 14));
		root.setDefaultButton(add);
		this.setLocationRelativeTo(null);
	}
	
	/**
	 * Returns the written for the playlist
	 * @return
	 */
	public String getInputName(){
		return nameIn.getText();
	}
	
	/**
	 * Returns a list containing the selected tracks' names
	 * @return
	 */
	public List<String> getSelected(){
		return new ArrayList<>(tracks.getSelectedValuesList());
	}
	
	/**
	 * Sets the visibility of the Dialog
	 */
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
	
	/**
	 * Refreshes the tracks list
	 * @param trackNames
	 */
	public void refreshList(List<String> trackNames){
		listModel.clear();
		for(String name: trackNames){
			listModel.addElement(name);
		}
	}
	
	/**
	 * Sets the buttons for the Dialog
	 * @param add
	 */
	public void setButtons(ActionListener add){
		this.add.addActionListener(add);
	}
	
	/**
	 * Resets the input fields
	 */
	private void reset(){
		this.nameIn.setText("");
	}

	/**
	 * Shows a dialog with the input content
	 * @param title
	 * @param message
	 */
	public void showErrorMessage(String title, String message) {
		JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Shows a confim message with the input content
	 * @param title
	 * @param message
	 * @return
	 */
	public int showConfirmMessage(String title, String message){
		return JOptionPane.showConfirmDialog(this, message, title, JOptionPane.YES_NO_OPTION);
	}
}