import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Frame {
    private JFrame frame;
    private JPanel panel;
	private JButton playButton;
	private JButton quitButton;
	private JPanel buttonPanel;
	private JPanel boardPanel;

    public Frame(String MinotaurTypeOfPlayer, String TheseusTypeOfPlayer, Game game)
    {
		int Dimensions = game.getBoard().getN();

		TypesOfPlayer Minotaur = new TypesOfPlayer(1, "Minotaur", game.getBoard(), 0, (Dimensions-1)/2, (Dimensions-1)/2);
		TypesOfPlayer Theseus = new TypesOfPlayer(2, "Theseus", game.getBoard(), 0, 0, 0);

		// this.board = new Board(board);
        frame = new JFrame("A Night in the Museum");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000,1000);
		// frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setVisible(true);
		
		// int frameWidth = (int)frame.getWidth();
		// int frameHeight = (int)frame.getHeight();

		panel = new JPanel(new BorderLayout());

		boardPanel = new BoardPanel(game.getBoard()).getBoard();
		panel.add(boardPanel, BorderLayout.CENTER);

		buttonPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		quitButton = new JButton("Quit");
		playButton = new JButton("Play");

		gbc.insets = new Insets(8, 8, 8, 8);
		
		buttonPanel.add(playButton, gbc);
		buttonPanel.add(quitButton, gbc);

		playButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				
				if(MinotaurTypeOfPlayer == "Player")
				{
					game.movePlayer(Minotaur.getPlayer());
				}
				else if(MinotaurTypeOfPlayer == "HeuristicPlayer")
				{
					game.movePlayer(Minotaur.getHeuristicPlayer());
				}

				if(TheseusTypeOfPlayer == "Player")
				{
					game.movePlayer(Theseus.getPlayer());
				}
				else if(TheseusTypeOfPlayer == "HeuristicPlayer")
				{
					game.movePlayer(Theseus.getHeuristicPlayer());
				}

				if(TheseusTypeOfPlayer == "MinMaxPlayer" && MinotaurTypeOfPlayer == "MinMaxPlayer")
					game.movePlayers(Theseus.getMinMaxPlayer(), Minotaur.getMinMaxPlayer());
					
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