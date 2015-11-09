import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Display {
	private static final int DEFAULT_TRACE_LENGTH = 25;
	  private static final int DEFAULT_PIXEL_SIZE = 5;
	  private static final long serialVersionUID = 1L;
	  private static final int MAX_DELAY = 500;
	  private JFrame _mainFrame;
	
	  private class DisplayPanel
	    extends JPanel
	  {
	    private static final long serialVersionUID = 1L;
	    
	    // Default Constructure
	    public DisplayPanel()
	    {
	      setPreferredSize(new Dimension(400, 400));
	      setBackground(Color.BLACK);
	    }
	    
	    public void paintComponent(Graphics paramGraphics)
	    {
	      Graphics2D localGraphics2D = (Graphics2D)paramGraphics;
	      localGraphics2D.setBackground(Color.black);
	      localGraphics2D.clearRect(0, 0, getWidth(), getHeight());
	      ListIterator localListIterator1 = Display.this._colors.subList(Display.this._colors.size() - Display.this._points.size(), Display.this._colors.size()).listIterator();
	      
	      Point localPoint = null;
	      int i = -Display.this._pixelSize / 2;
	      int j = -Display.this._pixelSize / 2;
	      synchronized (Display.this._points)
	      {
	        ListIterator localListIterator2 = Display.this._points.listIterator();
	        while (localListIterator2.hasNext())
	        {
	          if (localListIterator1.hasNext()) {
	            localGraphics2D.setColor((Color)localListIterator1.next());
	          }
	          localPoint = (Point)localListIterator2.next();
	          localGraphics2D.fillRect(localPoint.x + i, localPoint.y + j, Display.this._pixelSize, Display.this._pixelSize);
	        }
	      }
	      localGraphics2D.setColor(Color.WHITE);
	      localGraphics2D.drawString("Fps: " + Display.this._fps, getWidth() - 75, getHeight() - 25);
	    }
	  }
	  
	  private List<Point> _points = null;
	  private List<Color> _colors = null;
	  private int _maxTraceLength = 25;
	  private int _updateDelay = 125;
	  private Color _pixelColor = Color.RED;
	  private int _pixelSize = 5;
	  private DisplayPanel _drawPanel;
	  
	  public Display()
	  {
	    this(25, 3);
	  }
	  
	  public Display(int paramInt1, int paramInt2)
	  {
	    setTraceLength(paramInt1);
	    
	    //Setting up the Frame of the GUI
	    this._mainFrame = new JFrame();
	    this._mainFrame.setSize(450, 450);
	    this._mainFrame.setTitle("Path Tracer");
	    this._mainFrame.setDefaultCloseOperation(3);
	    
	    JPanel localJPanel = new JPanel();
	    localJPanel.setPreferredSize(new Dimension(400, 50));
	    
	    localJPanel.add(new JLabel("Speed:"));
	    
	    JSlider localJSlider = new JSlider();
	    localJSlider.setPreferredSize(new Dimension(75, 20));
	    localJSlider.setCursor(new Cursor(12));
	    localJSlider.setMaximum(500);
	    localJSlider.setMinimum(0);
	    localJSlider.setPaintTicks(false);
	    localJSlider.setValue(this._updateDelay);
	    localJSlider.addChangeListener(new ChangeListener()
	    {
	      public void stateChanged(ChangeEvent paramAnonymousChangeEvent)
	      {
	        JSlider localJSlider = (JSlider)paramAnonymousChangeEvent.getSource();
	        Display.this._updateDelay = localJSlider.getValue();
	      }
	    });
	    localJPanel.add(localJSlider);
	    
	    localJPanel.add(new JLabel("Length:"));
	    JTextField localJTextField = new JTextField();
	    localJTextField.setPreferredSize(new Dimension(50, 20));
	    
	    localJTextField.setText("" + this._maxTraceLength);
	    localJTextField.addActionListener(new ActionListener()
	    {
	      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
	      {
	        JTextField localJTextField = (JTextField)paramAnonymousActionEvent.getSource();
	        int i = Display.this._maxTraceLength;
	        try
	        {
	          i = Integer.parseInt(localJTextField.getText());
	          Display.this.setTraceLength(i);
	          Display.this.generateColorSpectrum();
	        }
	        catch (NumberFormatException localNumberFormatException)
	        {
	          localJTextField.setText("" + i);
	        }
	      }
	    });
	    localJPanel.add(localJTextField);
	    
	    final JButton localJButton1 = new JButton("Color");
	    localJButton1.addActionListener(new ActionListener()
	    {
	      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
	      {
	        Display.this._pixelColor = JColorChooser.showDialog(localJButton1, "Pick a color", Display.this._pixelColor);
	        
	        Display.this.generateColorSpectrum();
	      }
	    });
	    localJPanel.add(localJButton1);
	    
	    JButton localJButton2 = new JButton("Quit");
	    localJButton2.addActionListener(new ActionListener()
	    {
	      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
	      {
	        System.exit(0);
	      }
	    });
	    localJPanel.add(localJButton2);
	    
	    this._mainFrame.add(localJPanel, "South");
	    
	    this._drawPanel = new DisplayPanel();
	    this._mainFrame.add(this._drawPanel, "Center");
	    if (paramInt2 < 1) {
	      this._pixelSize = 1;
	    } else {
	      this._pixelSize = paramInt2;
	    }
	    this._mainFrame.pack();
	    this._mainFrame.setLocationRelativeTo(null);
	    this._mainFrame.setVisible(true);
	    
	    Timer localTimer = new Timer();
	    localTimer.schedule(new TimerTask()
	    {
	      public void run()
	      {
	        Display.this._fps = Display.this._framesPainted.get();
	        Display.this._framesPainted.set(0);
	      }
	    }, 1000L, 1000L);
	  }
	  
	  private int _fps = 0;
	  private AtomicInteger _framesPainted = new AtomicInteger();
	  
	  public void drawNextPixel(int paramInt1, int paramInt2)
	  {
	    if (this._points.size() < this._maxTraceLength)
	    {
	      this._points.add(new Point(paramInt1, paramInt2));
	    }
	    else
	    {
	      Point localPoint = (Point)this._points.remove(0);
	      localPoint.setLocation(paramInt1, paramInt2);
	      this._points.add(localPoint);
	    }
	    this._drawPanel.repaint();
	    this._framesPainted.incrementAndGet();
	    try
	    {
	      Thread.sleep(500 - this._updateDelay, 125);
	    }
	    catch (InterruptedException localInterruptedException) {}
	  }
	  
	  public int getHeight()
	  {
	    return this._drawPanel.getHeight();
	  }
	  
	  public int getWidth()
	  {
	    return this._drawPanel.getWidth();
	  }
	  
	  public String toString()
	  {
	    return "cs251 pixel display ( " + getWidth() + " by " + getHeight() + ")";
	  }
	  
	  private void generateColorSpectrum()
	  {
	    this._colors = Collections.synchronizedList(new LinkedList());
	    float f1 = 1.0F / this._maxTraceLength;
	    float f2 = this._pixelColor.getRed() / 255.0F;
	    float f3 = this._pixelColor.getGreen() / 255.0F;
	    float f4 = this._pixelColor.getBlue() / 255.0F;
	    for (int i = this._maxTraceLength - 1; i >= 0; i--) {
	      this._colors.add(new Color(f2, f3, f4, 1.0F - f1 * i));
	    }
	  }
	  
	  private void setTraceLength(int paramInt)
	  {
	    this._maxTraceLength = (paramInt <= 1 ? 1 : paramInt);
	    
	    generateColorSpectrum();
	    if (this._points == null) {
	      this._points = Collections.synchronizedList(new LinkedList());
	    } else if (this._maxTraceLength < this._points.size()) {
	      this._points = Collections.synchronizedList(this._points.subList(this._points.size() - this._maxTraceLength, this._points.size()));
	    }
	  }
}
