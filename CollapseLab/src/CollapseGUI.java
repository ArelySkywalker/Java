import javax.swing.*;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.stream.IntStream;
import java.applet.*;
import java.net.*;


/**  Skeleton GUI for grid-based games. 
 *   GUI has a menu, a status area, and a 2d playing area.
 *   The GUI will display the game and handle mouse clicks, dispatching
 *   them to the appropriate button or cell of the board.
 * @author Arely Miramontes
 * 
 */
public class CollapseGUI extends JFrame implements ActionListener 
{

	/* Main components of the GUI */ 
	private JLabel gameStatus = null;
	private JMenuBar menuBar;
	private JTable table;
	private JPanel statusPane;
	private GameTableModel model;
	private Timer timer;
	/* Tile dimensions */
	private int TileWidth = 75;
	private int TileHeight = 55;
	private ImageIcon [] images = new ImageIcon[10];
	private CollapseGame game;
	private int boardPrefSize, boardNum;
	private final static int kNumBoards = 5000;

	static JButton pause;

	boolean GameRunning = true;



	/** Create a GUI.
	 * Will use the System Look and Feel when possible.
	 */
	public CollapseGUI() throws IOException
	{
		super();

		this.boardNum = new Random().nextInt(kNumBoards);
		this.boardPrefSize = 10;
		this.game = new CollapseGame(this.boardPrefSize, this.boardNum);
		super.setTitle("Collapse - board " + this.boardNum);
		try
		{
			UIManager.setLookAndFeel(
					UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception ex)
		{
			System.err.println(ex);
		}

		timer = new Timer(10,this);
		timer.setInitialDelay(0);
		timer.start();
		
//		try {
//			AudioClip clip = Applet.newAudioClip(
//			new URL("EverythingIsAwesome.wav"));
//			clip.loop();
//			} catch (MalformedURLException murle) {
//			System.out.println(murle);
//			}
	}


	/** Place all the Swing widgets in the frame of this GUI.
	 * @post the GUI is visible.  
	 */
	public void run()
	{
		loadImages();
		this.model = new GameTableModel(game);
		this.table = new JTable(model)
		{
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
			{
				Component c = super.prepareRenderer(renderer, row, column);

				if( c instanceof JComponent )
				{	
					((JComponent)c).setOpaque(false);
				}
				return c;
			}

			String file = "icons/" + "black.jpg";
			URL backgroundUrl = getClass().getResource(file);
			ImageIcon backgroundIcon = new ImageIcon(backgroundUrl);

			// Override paint so as to show the table background

			public void paint(Graphics g)
			{
				g.drawImage(backgroundIcon.getImage(), 0, 0, null, null);
				// Now let the paint do its usual work
				super.paint(g);
			}
		};

		table.setDefaultEditor(Object.class, null);  // remove editor makes table not editable
		table.setDefaultRenderer(CollapsePiece.class, new GameCellRenderer(images));

		// Define the layout manager that will control order of components
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		// Add a menubar
		menuBar = new javax.swing.JMenuBar();
		JMenu mnuGame = new JMenu("Collapse Menu");
		menuBar.add(mnuGame);

		JMenuItem mnuRestart = new JMenuItem("Restart");
		mnuRestart.setAccelerator(KeyStroke.getKeyStroke('R', ActionEvent.ALT_MASK));
		mnuRestart.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				newGame(boardNum);
			}
		});
		mnuGame.add(mnuRestart);

		JMenuItem mnuNew = new JMenuItem("New");
		mnuNew.setAccelerator(KeyStroke.getKeyStroke('N', ActionEvent.ALT_MASK));
		mnuNew.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(boardNum == 5000)
				{
					boardNum = 1;
				}
				else
				{
					boardNum++;
				}
				newGame(boardNum);           
			}
		});
		mnuGame.add(mnuNew);

		JMenuItem mnuSelectGame = new JMenuItem("Select Game");
		mnuSelectGame.setAccelerator(KeyStroke.getKeyStroke('G', ActionEvent.ALT_MASK));
		mnuSelectGame.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String gameNumString = JOptionPane.showInputDialog(
						null,
						"Enter desired game number (1 - 5000)",
						"Select Game",
						JOptionPane.QUESTION_MESSAGE
						);

				if(gameNumString != null)
				{
					try
					{
						int gameNum = Integer.parseInt(gameNumString);
						boardNum = gameNum;
						newGame(boardNum); 
					}
					catch(NumberFormatException nfe)
					{

					}
				}          
			}
		});
		mnuGame.add(mnuSelectGame);

		JMenuItem mnuScores = new JMenuItem("Scores");
		mnuScores.setAccelerator(KeyStroke.getKeyStroke('S', ActionEvent.ALT_MASK));
		mnuScores.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(
						null,
						HighScores.getHighScores(),
						"Hall of Fame",
						JOptionPane.PLAIN_MESSAGE
						);

			}

		});
		mnuGame.add(mnuScores);

		JMenuItem mnuCheat = new JMenuItem("Cheat");
		mnuCheat.setAccelerator(KeyStroke.getKeyStroke('C', ActionEvent.ALT_MASK));
		mnuCheat.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				game.cheat();
				repaint();
			}
		});
		mnuGame.add(mnuCheat);

		JMenuItem mnuQuit = new JMenuItem("Quit");
		mnuQuit.setAccelerator(KeyStroke.getKeyStroke('Q', ActionEvent.ALT_MASK));
		mnuQuit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		mnuGame.add(mnuQuit);
		setJMenuBar(menuBar);        

		// Create a panel for the status information
		statusPane = new JPanel();
		gameStatus = new JLabel("Tiles left: " + game.getTilesLeft() + "\t\tMoves: " + game.getNumberOfMoves());
		pause = new JButton("Pause");

		pause.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(GameRunning == true) {
					timer.stop();
					pause.setText("Start");
					GameRunning = false;


				} else if(GameRunning == false) {
					timer.start();
					pause.setText("Pause");
					GameRunning = true;
					statusPane.requestFocusInWindow();

				}
			}

		});

		statusPane.add(pause);

		statusPane.add(gameStatus);
		statusPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		getContentPane().add(statusPane);

		// Define the characteristics of the table that shows the game board        
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setCellSelectionEnabled(false);
		table.setRowHeight(TileHeight);

		table.setOpaque(false);
		table.setShowGrid(false);

		table.setAlignmentX(Component.CENTER_ALIGNMENT);
		getContentPane().add(table);

		// Define the mouse listener that will handle player's clicks.
		table.addMouseListener(new MouseAdapter()
		{
			public void mouseReleased(MouseEvent ev)
			{
				int col = table.getSelectedColumn();
				int row = table.getSelectedRow();
				// Is it a left mouse click?
				if (SwingUtilities.isLeftMouseButton(ev)) 
				{
					row = (int) (ev.getPoint().getY()/TileHeight);
					col = (int) (ev.getPoint().getX()/TileWidth);
					//System.out.println("Clicked X: " + ev.getPoint().getX() + ", " + "Clicked Y: " + ev.getPoint().getY());
					//System.out.println("Row: " + row + ", Column: " + col);
					selectCell(row, col);
				}
				repaint();
			}
		}
				);

		table.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e)
			{
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_SPACE)
				{
					int col = table.getSelectedColumn();
					int row = table.getSelectedRow();
					selectCell(row, col);
				}
				repaint();
			}
		});

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


	}

	protected void loadImages()
	{
		CollapsePiece [] lights = CollapsePiece.values();

		// Any images will be loaded here
		for (int ndx = 0; ndx < lights.length - 1; ndx++)
		{
			String file = "icons/" + lights[ndx].name().toLowerCase() + ".png";
			URL url = getClass().getResource(file);
			//System.out.println(url.getPath());
			ImageIcon original = new ImageIcon(url);
			Image scaled = original.getImage().getScaledInstance(TileWidth, TileHeight, Image.SCALE_DEFAULT);
			ImageIcon icon = new ImageIcon(scaled);
			images[ndx] = icon;
		}
	}

	private void selectCell(int row, int col)
	{	
		boolean validMove = game.takeTurn(row, col);
		
		gameStatus.setText("Tiles left: " + game.getTilesLeft() + "\t\tMoves: " + game.getNumberOfMoves() + "\t\t"
				+ "Score: " + IntStream.of(game.getTilesLeft()*game.getNumberOfMoves()).sum());
		
		if(game.isGameOver() && validMove)
		{
			String choice = JOptionPane.showInputDialog(
					this,
					"Game " + this.boardNum + " Cleared!\nSave your score? (y/n)",
					"Select Game",
					JOptionPane.QUESTION_MESSAGE
					);
			if(choice != null && choice.toLowerCase().trim().equals("y"))
			{
				String name = JOptionPane.showInputDialog(
						this,
						"Your score of " + IntStream.of(game.getTilesLeft()*game.getNumberOfMoves()).sum() + " will be entered into the Hall of Fame.\nEnter your name:",
						"Hall of Fame Entry",
						JOptionPane.QUESTION_MESSAGE
						);
				if(name != null)
				{
					try
					{
						HighScores.addHighScore(name, game.getNumberOfMoves());
					}
					catch(IOException e)
					{
						e.printStackTrace();
					}
				}
			}newGame(boardNum);
		}
	}
	

	/** Start a new game by putting new values in the board */
	private void newGame(int gameNum)
	{
		//getContentPane().remove(table);
		this.game = new CollapseGame(this.boardPrefSize, gameNum);
		super.setTitle("Collapse - board " + this.boardNum);
		gameStatus = new JLabel("Tiles left: " + game.getTilesLeft() + "\t\tMoves: " + game.getNumberOfMoves());
		this.statusPane.removeAll();
		this.statusPane.add(gameStatus);
		this.statusPane.revalidate();
		this.statusPane.repaint();

		this.model = new GameTableModel(game);
		this.table.setModel(model);

		repaint();


	}

	// Local main to launch the GUI
	public static void main(String[] args)
	{
		JOptionPane.showMessageDialog(null, "Welcome to Collapse!\n"
				+ "The Goal is to get the smallest amount of moves possible!",
				"Collapse", JOptionPane.INFORMATION_MESSAGE);
		try
		{
			// Create the GUI 
			CollapseGUI frame = new CollapseGUI();

			frame.run();   // do the layout of widgets

			// Make the GUI visible and available for user interaction
			frame.pack();
			frame.setVisible(true);
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.out.println("Error reading from preferences file.");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}