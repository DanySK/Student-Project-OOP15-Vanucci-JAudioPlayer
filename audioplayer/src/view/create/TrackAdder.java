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

import controller.OptionsManager;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.io.File;
import java.awt.FlowLayout;

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
	
	public TrackAdder(OptionsManager manager){
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
		downPanel.setLayout(new BoxLayout(downPanel, BoxLayout.X_AXIS));
		chooser.setHorizontalAlignment(SwingConstants.LEFT);
		downPanel.add(chooser);
		downPanel.add(showFile);
		chooser.setFont(new Font("Tahoma", Font.BOLD, 14));
		chooser.addActionListener(e->{
			showFile.setText(manager.chooser());
		});
		chooser.setToolTipText("scegli File");
		nameLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		footer.setBorder(new EmptyBorder(10, 40, 10, 40));
		getContentPane().add(footer, BorderLayout.SOUTH);
		footer.setLayout(new BorderLayout(0, 0));
		add.setFont(new Font("Tahoma", Font.BOLD, 14));
		add.addActionListener(e-> {
			String trackName = nameIn.getText();
			String fileName = showFile.getText();
			
			if(trackName.trim().length() > 0 && fileName.trim().length() > 0){
				try {
					manager.addTrack(fileName,trackName);
					JOptionPane.showMessageDialog(this, "Il brano è stato salvato correttamente", "Brano aggiunto", JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
				} catch(IllegalArgumentException se){
					JOptionPane.showMessageDialog(this, "Esiste già un brano con questo nome", "Aggiunta brano fallita", JOptionPane.ERROR_MESSAGE);
				}catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Controlla i dati inseriti o riprova", "Qualcosa è andato storto", JOptionPane.ERROR_MESSAGE);
				}
			}
			else{
				JOptionPane.showMessageDialog(null, "Inserisci un nome e seleziona un file", "Dati non validi", JOptionPane.ERROR_MESSAGE);
			}
		});
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
		this.showFile.setText("");
	}
}