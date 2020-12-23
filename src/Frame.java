import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Frame {
    private JFrame frame;
    private JPanel panel;
	private JPanel buttonPanel;
	private JButton playButton;
	private JButton quitButton;
	private JPanel boardPanel;
	private Game game; 

    public Frame(Game game)
    {
		// this.board = new Board(board);
        frame = new JFrame("A Night in the Museum");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,600);

		panel = new JPanel(new BorderLayout());

		boardPanel = new BoardPanel(game.getBoard()).getBoard();
		panel.add(boardPanel, BorderLayout.CENTER);

		buttonPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		quitButton = new JButton("Quit");
		playButton = new JButton("Begin game");

		buttonPanel.add(playButton, gbc);
		buttonPanel.add(quitButton, gbc);

		playButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 

				game.movePlayers();

				frame.invalidate();
				panel.remove(boardPanel);
				panel.add(new BoardPanel(game.getBoard()).getBoard());

				frame.validate();
			} 
		}); 

	    quitButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				frame.dispose();
			} 
		});
	
		panel.add(buttonPanel, BorderLayout.SOUTH);

		frame.add(panel); 
		frame.setVisible(true);
	}
	
	JFrame getFrame() { return frame;}

	JPanel getPanel() { return panel;}

	JPanel getButtonPanel() { return buttonPanel; }

	JButton getQuitButton() { return quitButton; }
 
	JButton getPlayButton() { return playButton; }

	void setQuitButton(JButton quitButton) { this.quitButton = quitButton; }

	void setPlayButton (JButton playButton) { this.playButton = playButton; }
}