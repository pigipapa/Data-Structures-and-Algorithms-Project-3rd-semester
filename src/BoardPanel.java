import java.util.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;

public class BoardPanel {

    private JPanel boardPanel;
    private GridBagConstraints gbc;
    private ArrayList<ImageIcon> graphics;

    public BoardPanel()
    {
        graphics = new ArrayList<ImageIcon>();
        gbc = new GridBagConstraints();
        boardPanel = new JPanel(new GridLayout(0, 0));
    }

    public BoardPanel(Board board)
    {
        graphics = new ArrayList<ImageIcon>();
        gbc = new GridBagConstraints();
        boardPanel = new JPanel(new GridBagLayout());

        loadGraphics();
        getVisualizedBoard(board);
    }

    public JPanel getBoard() { return boardPanel; }

    public void loadGraphics()
    {
        ArrayList<String> urls = new ArrayList<String>(); 
        urls.add("https://raw.githubusercontent.com/valiapp/Data-Structures-and-Algorithms-Project-3rd-semester/GUI/graphics/players/minotaur.png"); 
        urls.add("https://raw.githubusercontent.com/valiapp/Data-Structures-and-Algorithms-Project-3rd-semester/GUI/graphics/players/theseus.png");
        urls.add("https://raw.githubusercontent.com/valiapp/Data-Structures-and-Algorithms-Project-3rd-semester/GUI/graphics/tiles/0wall/Dungeon-Top.jpg"); 
        urls.add("https://raw.githubusercontent.com/valiapp/Data-Structures-and-Algorithms-Project-3rd-semester/GUI/graphics/tiles/1wall/DungeonTileDown.png");
        urls.add("https://raw.githubusercontent.com/valiapp/Data-Structures-and-Algorithms-Project-3rd-semester/GUI/graphics/tiles/1wall/DungeonTileUp.png");
        urls.add("https://raw.githubusercontent.com/valiapp/Data-Structures-and-Algorithms-Project-3rd-semester/GUI/graphics/tiles/1wall/DungeonTileRight.png");
        urls.add("https://raw.githubusercontent.com/valiapp/Data-Structures-and-Algorithms-Project-3rd-semester/GUI/graphics/tiles/1wall/DungeonTileLeft.png");
        urls.add("https://raw.githubusercontent.com/valiapp/Data-Structures-and-Algorithms-Project-3rd-semester/GUI/graphics/tiles/2walls/DungeonTileLeftDown.png");
        urls.add("https://raw.githubusercontent.com/valiapp/Data-Structures-and-Algorithms-Project-3rd-semester/GUI/graphics/tiles/2walls/DungeonTileLeftRight.png");
        urls.add("https://raw.githubusercontent.com/valiapp/Data-Structures-and-Algorithms-Project-3rd-semester/GUI/graphics/tiles/2walls/DungeonTileLeftUp.png");
        urls.add("https://raw.githubusercontent.com/valiapp/Data-Structures-and-Algorithms-Project-3rd-semester/GUI/graphics/tiles/2walls/DungeonTileRightDown.png");
        urls.add("https://raw.githubusercontent.com/valiapp/Data-Structures-and-Algorithms-Project-3rd-semester/GUI/graphics/tiles/2walls/DungeonTileRightUp.png");
        urls.add("https://raw.githubusercontent.com/valiapp/Data-Structures-and-Algorithms-Project-3rd-semester/GUI/graphics/tiles/2walls/DungeonTileUpDown.png");

        for(int i = 0; i < urls.size(); i++)
        {
            try {
                BufferedImage image = ImageIO.read(new URL(urls.get(i))); 
                graphics.add(new ImageIcon(image));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public JLabel chooseTile(Board board, int tileId)
    {
        JLabel label = new JLabel();
        int numberOfWalls = board.getTile(tileId).countTileWalls();
        
        switch(numberOfWalls)
        {
            case 0:
                label.setIcon(graphics.get(2));
                break;
            case 1:
                if(board.getTile(tileId).getDown()) 
                    label.setIcon(graphics.get(3));
                else if(board.getTile(tileId).getUp()) 
                    label.setIcon(graphics.get(4));
                else if(board.getTile(tileId).getRight()) 
                    label.setIcon(graphics.get(5));
                else if(board.getTile(tileId).getLeft()) 
                    label.setIcon(graphics.get(6));
                break;                    
            case 2:
                if(board.getTile(tileId).getLeft() && board.getTile(tileId).getDown()) 
                    label.setIcon(graphics.get(7));
                else if(board.getTile(tileId).getLeft() && board.getTile(tileId).getRight()) 
                    label.setIcon(graphics.get(8));
                else if(board.getTile(tileId).getLeft() && board.getTile(tileId).getUp()) 
                    label.setIcon(graphics.get(9));
                else if(board.getTile(tileId).getRight() && board.getTile(tileId).getDown()) 
                    label.setIcon(graphics.get(10));
                else if(board.getTile(tileId).getRight() && board.getTile(tileId).getUp()) 
                    label.setIcon(graphics.get(11));
                else if(board.getTile(tileId).getUp() && board.getTile(tileId).getDown()) 
                    label.setIcon(graphics.get(12));
                break;
        }
        return label;
    }

    JPanel getVisualizedBoard(Board board)
    {
        int dimensions = board.getN();

        for(int y = 0; y < dimensions; y++)
        {
            for(int x = 0; x < dimensions; x++)
            {
                int tileId = dimensions*dimensions - dimensions - dimensions*y + x;

                if(board.getTile(tileId).hasTheseus())
                {
                    JPanel subpanel = new JPanel();
                    LayoutManager overlay = new OverlayLayout(subpanel);
                    subpanel.setLayout(overlay);

                    subpanel.add(new JLabel(graphics.get(1)));
                    subpanel.add(chooseTile(board, tileId));
                    
                    gbc.gridx = x;
                    gbc.gridy = y;
                    boardPanel.add(subpanel, gbc);
                }
                else if(board.getTile(tileId).hasMinotaur())
                {
                    JPanel subpanel = new JPanel();
                    LayoutManager overlay = new OverlayLayout(subpanel);
                    subpanel.setLayout(overlay);

                    subpanel.add(new JLabel(graphics.get(0)));
                    subpanel.add(chooseTile(board, tileId));
                    
                    gbc.gridx = x;
                    gbc.gridy = y;
                    boardPanel.add(subpanel, gbc);
                }
                else
                {
                    gbc.gridx = x;
                    gbc.gridy = y;
                    boardPanel.add(chooseTile(board, tileId), gbc);
                }
            }
        }
        return boardPanel;
    }
}