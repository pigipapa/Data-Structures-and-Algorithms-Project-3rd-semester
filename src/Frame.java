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

        frame = new JFrame("A Night in the Museum");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000,1000);
		frame.setVisible(true);

		panel = new JPanel(new BorderLayout());
		JPanel statusPanel = makeStatusPanel(game.getBoard().getS(), game.getRound(), game.getMaxRounds(), TheseusTypeOfPlayer, MinotaurTypeOfPlayer, Minotaur, Theseus);
		panel.add(statusPanel, BorderLayout.PAGE_START);

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

				game.setRound(game.getRound()+1);

				frame.invalidate();

				panel.remove(boardPanel);
				panel.add(new BoardPanel(game.getBoard()).getBoard(), BorderLayout.CENTER);

				panel.remove(statusPanel);
				panel.add(makeStatusPanel(game.getBoard().getS(), game.getRound(), game.getMaxRounds(), TheseusTypeOfPlayer, MinotaurTypeOfPlayer, Minotaur, Theseus), BorderLayout.PAGE_START);

				boolean[] checkWin = checkWin(TheseusTypeOfPlayer, MinotaurTypeOfPlayer, Minotaur, Theseus, game.getBoard().getS());
				if(game.getMaxRounds() == game.getRound() || checkWin[0] || checkWin[1])
					playButton.setEnabled(false);

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
	
	JPanel makeStatusPanel( int Supplies, int gameRound, int gameMaxRounds, String TheseusTypeOfPlayer, String MinotaurTypeOfPlayer, TypesOfPlayer Minotaur, TypesOfPlayer Theseus)
	{
		int MinotaurScoreNumber=0;
		int TheseusScoreNumber=0;

		if(MinotaurTypeOfPlayer == "Player")
			MinotaurScoreNumber = Minotaur.getPlayer().getScore();
		else if(MinotaurTypeOfPlayer == "HeuristicPlayer")
			MinotaurScoreNumber = Minotaur.getHeuristicPlayer().getScore();
		else if(MinotaurTypeOfPlayer == "MinMaxPlayer")
			MinotaurScoreNumber = Minotaur.getMinMaxPlayer().getScore();

		if(TheseusTypeOfPlayer == "Player")
			TheseusScoreNumber = Theseus.getPlayer().getScore();
		else if(TheseusTypeOfPlayer == "HeuristicPlayer")
			TheseusScoreNumber = Theseus.getHeuristicPlayer().getScore();
		else if(TheseusTypeOfPlayer == "MinMaxPlayer")
			TheseusScoreNumber = Theseus.getMinMaxPlayer().getScore();

		JLabel MinotaurScore = new JLabel("<html>Minotaur Score: " + String.valueOf(MinotaurScoreNumber) + "<br/>Type of player: " + MinotaurTypeOfPlayer+"</html>");
		JLabel TheseusScore = new JLabel("<html>Theseus Score: " + String.valueOf(TheseusScoreNumber) + "<br/>Type of player: " + TheseusTypeOfPlayer+"</html>");

		JPanel statusPanel = new JPanel(new GridBagLayout());
		GridBagConstraints statusGbc = new GridBagConstraints();
		statusGbc.insets = new Insets(8, 25, 25, 8);

		statusPanel.add(MinotaurScore, statusGbc);
		statusPanel.add(TheseusScore, statusGbc);

		boolean[] checkWin = checkWin(TheseusTypeOfPlayer, MinotaurTypeOfPlayer, Minotaur, Theseus, Supplies);

		if(gameRound == gameMaxRounds)
			statusPanel.add(new JLabel("Maximum rounds have been reached"), statusGbc);
		else if(checkWin[0])
			statusPanel.add(new JLabel("Theseus won!"), statusGbc);
		else if(checkWin[1])
			statusPanel.add(new JLabel("Minotaur won!"), statusGbc);
		else if(gameRound != gameMaxRounds)
			statusPanel.add(new JLabel("Round: " + String.valueOf(gameRound)), statusGbc);

		return statusPanel;
	}

	public boolean[] checkWin(String TheseusTypeOfPlayer, String MinotaurTypeOfPlayer, TypesOfPlayer Minotaur, TypesOfPlayer Theseus, int Supplies) {
		
		boolean[] checkWin = new boolean[2];
		for(int i = 0; i < 2; i++)
			checkWin[i] = false;

		int MinotaurScoreNumber=0;
		int TheseusScoreNumber=0;

		int MinotaurCurrentTile=0;
		int TheseusCurrentTile=0;

		if(MinotaurTypeOfPlayer == "Player")
		{
			MinotaurScoreNumber = Minotaur.getPlayer().getScore();
			MinotaurCurrentTile =  Minotaur.getPlayer().getCurrentTile();
		}
		else if(MinotaurTypeOfPlayer == "HeuristicPlayer")
		{
			MinotaurScoreNumber = Minotaur.getHeuristicPlayer().getScore();
			MinotaurCurrentTile =  Minotaur.getHeuristicPlayer().getCurrentTile();
		}
		else if(MinotaurTypeOfPlayer == "MinMaxPlayer")
		{
			MinotaurScoreNumber = Minotaur.getMinMaxPlayer().getScore();
			MinotaurCurrentTile =  Minotaur.getMinMaxPlayer().getCurrentTile();
		}

		if(TheseusTypeOfPlayer == "Player")
		{
			TheseusScoreNumber = Theseus.getPlayer().getScore();
			TheseusCurrentTile = Theseus.getPlayer().getCurrentTile();
		}
		else if(TheseusTypeOfPlayer == "HeuristicPlayer")
		{
			TheseusScoreNumber = Theseus.getHeuristicPlayer().getScore();
			TheseusCurrentTile = Theseus.getHeuristicPlayer().getCurrentTile();
		}
		else if(TheseusTypeOfPlayer == "MinMaxPlayer")
		{
			TheseusScoreNumber = Theseus.getMinMaxPlayer().getScore();
			TheseusCurrentTile = Theseus.getMinMaxPlayer().getCurrentTile();
		}

		if(TheseusScoreNumber == Supplies) 											
			checkWin[0] = true;
		
		if(TheseusCurrentTile == MinotaurCurrentTile)					
			checkWin[1] = true;

		return checkWin;
		
	}

	JFrame getFrame() { return frame;}

	JPanel getPanel() { return panel;}

	JPanel getButtonPanel() { return buttonPanel; }

	JButton getQuitButton() { return quitButton; }
 
	JButton getPlayButton() { return playButton; }

	void setQuitButton(JButton quitButton) { this.quitButton = quitButton; }

	void setPlayButton (JButton playButton) { this.playButton = playButton; }
}