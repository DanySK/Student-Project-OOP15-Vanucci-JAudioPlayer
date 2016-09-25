package view.create;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
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
import javax.swing.filechooser.FileFilter;

import model.FileHandler;

import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.File;

public class TrackAdder extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7303346759639884086L;
	
	private final JPanel body = new JPanel();
	private final JPanel footer = new JPanel();
	private final JPanel upPanel = new JPanel();
	private final JPanel downPanel = new JPanel();
	
	private final JLabel nameLabel = new JLabel("Nome traccia: ");
	
	private JTextField nameIn = new JTextField();
	private JLabel showFile = new JLabel("");
	
	private final JButton add = new JButton("AGGIUNGI");
	private final JButton chooser = new JButton("Scegli file");
	
	public TrackAdder(){
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
		downPanel.setLayout(new BoxLayout(downPanel, BoxLayout.X_AXIS));
		chooser.setHorizontalAlignment(SwingConstants.LEFT);
		downPanel.add(chooser);
		downPanel.add(showFile);
		chooser.setFont(new Font("Tahoma", Font.BOLD, 14));
		chooser.setToolTipText("Seleziona un file");
		nameLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		footer.setBorder(new EmptyBorder(10, 40, 10, 40));
		getContentPane().add(footer, BorderLayout.SOUTH);
		footer.setLayout(new BorderLayout(0, 0));
		add.setFont(new Font("Tahoma", Font.BOLD, 14));
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
	
	public String chooseFile(String lastPath) {
		JFileChooser fileChooser = null;
		
		if (lastPath != null && !lastPath.equals("")) {
			fileChooser = new JFileChooser(lastPath);
		} else {
			fileChooser = new JFileChooser(FileHandler.getMainDir());
		}
		
		FileFilter wavFilter = new FileFilter() {
			@Override
			public String getDescription() {
				return "File audio (*.WAV)";
			}

			@Override
			public boolean accept(File file) {
				if (file.isDirectory()) {
					return true;
				} else {
					return file.getName().toLowerCase().endsWith(".wav");
				}
			}
		};

		
		fileChooser.setFileFilter(wavFilter);
		fileChooser.setDialogTitle("Seleziona il file da aggiungere");
		fileChooser.setAcceptAllFileFilterUsed(false);

		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			lastPath = fileChooser.getSelectedFile().getParent();
			this.showFile.setText(fileChooser.getSelectedFile().getAbsolutePath());
		}
		return lastPath;
	}
	
	public String getInputName(){
		return nameIn.getText();
	}
	
	public String getChosenFile(){
		return showFile.getText();
	}
	
	private void reset(){
		this.nameIn.setText("");
		this.showFile.setText("");
	}
	
	public void setButtons(ActionListener add, ActionListener chooser){
		this.add.addActionListener(add);
		this.chooser.addActionListener(chooser);
	}
	
	public void showErrorMessage(String title, String message){
		JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
	}
	
	public int showConfirmMessage(String title, String message){
		return JOptionPane.showConfirmDialog(this, message, title, JOptionPane.YES_NO_OPTION);
	}
}