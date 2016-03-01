import java.awt.TextArea;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

import javax.swing.JFrame;


/*
 * This class creates a new chat GUI which is the way of talking to
 * out AI. 
 */





public class ChatGUI extends JFrame implements WindowListener, MouseListener, KeyListener, ActionListener{

	private TextArea convoBox = null; 		// The part that displays the chat history
	private TextField messageBox = null; 	// Where you enter stuff
	private String userName = null;			// Whodonneit?
	private int guiHeight = 600;
	private int guiWidth = 800;
	
	
	/*
	 * This is the constructor for the GUI.
	 * It first creates the GUI complete with convobox(chat history),
	 * messagebox(user input), and submit and clear buttons (enter key
	 * can be pressed instead of submit button).
	 * 
	 * Once the GUI is set up it creates a new AshBrain (our AI logic).
	 * 
	 */
	ChatGUI(String s){
		super(s);
		this.addWindowListener(this);
		this.setResizable(true);
		this.setSize(guiWidth,guiHeight);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//ConvoBox
		convoBox = new TextArea();
		convoBox.setEditable(false);
		this.add(convoBox,"Center");
		convoBox.setFont(new Font("Arial", Font.PLAIN, 16));
		
		//MessageBox
		messageBox = new TextField(30);
		messageBox.addKeyListener(this);
		messageBox.addActionListener(this);
		messageBox.setFont(new Font("Arial", Font.PLAIN, 16));
		
		Panel messagePanel = new Panel();
		messagePanel.setLayout(new FlowLayout());
		messagePanel.add(messageBox);
		messagePanel.setBackground(new Color(120,120,120));
		
		//Buttons
		Button send = new Button("Send");
		send.addActionListener(this);
		messagePanel.add(send);
		
		Button clear = new Button("Clear");
		clear.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String input = messageBox.getText();
		        messageBox.setText("");
			}
		});
		messagePanel.add(clear);

		this.add(messagePanel,"South");
		this.setVisible(true);
		messageBox.requestFocus();
		
		//Wake up Ash
		AshBrain ash = new AshBrain();
		
	}
	
	
	public void actionPerformed(ActionEvent evt) {
        String input = messageBox.getText();
        convoBox.append("You: " + input + "\n");
        messageBox.setText("");
        
        //TODO parse input, talk back
        String AshResponse = AshBrain.communicate(input);
        convoBox.append("Ash: " + AshResponse + "\n");
    }
	
	
	
	//Implemented Methods (Unused)
	
	@Override
	public void keyTyped(KeyEvent e)
	{
		
		
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		
		
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		
		
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		
		
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		
		
	}

	@Override
	public void windowOpened(WindowEvent e)
	{
		
		
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		
		
	}

	@Override
	public void windowClosed(WindowEvent e)
	{
		
		
	}

	@Override
	public void windowIconified(WindowEvent e)
	{
		
		
	}

	@Override
	public void windowDeiconified(WindowEvent e)
	{
		
		
	}

	@Override
	public void windowActivated(WindowEvent e)
	{
		
		
	}

	@Override
	public void windowDeactivated(WindowEvent e)
	{
		
		
	}
	
}
