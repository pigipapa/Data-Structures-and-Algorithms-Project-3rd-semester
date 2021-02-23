import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class IntroFrame {
    private JFrame IntroFrame;
    private JComboBox TheseusComboBox;
    private JComboBox MinotaurComboBox;
    private String MinotaurTypeOfPlayer;
    private String TheseusTypeOfPlayer;

    public IntroFrame(Board board, int maxRounds)
    {
        IntroFrame = new JFrame("Type of Player Selection");
        IntroFrame.setLayout(new GridLayout(4,2));
		IntroFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        IntroFrame.setSize(1000,1000);
        MinotaurTypeOfPlayer = "Player";
        TheseusTypeOfPlayer = "Player";

        String[] selector = {"Player", "HeuristicPlayer", "MinMaxPlayer"};
        MinotaurComboBox = new JComboBox(selector);
        TheseusComboBox = new JComboBox(selector);

        ActionListener MinotaurActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                MinotaurTypeOfPlayer = (String) MinotaurComboBox.getSelectedItem();
                if(MinotaurTypeOfPlayer == "MinMaxPlayer")
                {
                    TheseusComboBox.setSelectedItem("MinMaxPlayer");
                    TheseusComboBox.setEnabled(false);
                    MinotaurComboBox.setEnabled(false);
                }
            }
        };
        MinotaurComboBox.addActionListener(MinotaurActionListener);

        JLabel text1 = new JLabel("Select Minotaur Type");
        IntroFrame.add(text1);
        IntroFrame.add(MinotaurComboBox);

        ActionListener TheseusActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                TheseusTypeOfPlayer = (String) TheseusComboBox.getSelectedItem();
                if(TheseusTypeOfPlayer == "MinMaxPlayer")
                {
                    MinotaurComboBox.setSelectedItem("MinMaxPlayer");
                    TheseusComboBox.setEnabled(false);
                    MinotaurComboBox.setEnabled(false);
                }
            }
        };

        TheseusComboBox.addActionListener(TheseusActionListener);
        JLabel text2 = new JLabel("Select Theseus Type");
        IntroFrame.add(text2);

        IntroFrame.add(TheseusComboBox);
        IntroFrame.setVisible(true);

        JLabel text3 = new JLabel("After selecting, please press Proceed");
        IntroFrame.add(text3);

        JButton quitButton = new JButton("Proceed");
        IntroFrame.add(quitButton);

        JLabel text4 = new JLabel("Choose again");
        IntroFrame.add(text4);

        JButton refreshButton = new JButton("Refresh");
        IntroFrame.add(refreshButton);

        ActionListener refreshButtonActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                TheseusComboBox.setEnabled(true);
                MinotaurComboBox.setEnabled(true);
                MinotaurComboBox.setSelectedItem("Player");
                TheseusComboBox.setSelectedItem("Player");
            }
        };

        refreshButton.addActionListener(refreshButtonActionListener);

        quitButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
                IntroFrame.dispose();
                Game game = new Game(board, maxRounds);
                Frame frame = new Frame(MinotaurTypeOfPlayer, TheseusTypeOfPlayer, game); 
			} 
        });

    }

    String getMinotaurTypeOfPlayer(){ return MinotaurTypeOfPlayer; }
    
    String getTheseusTypeOfPlayer(){ return TheseusTypeOfPlayer; }
}
