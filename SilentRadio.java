/**
 * SilentRadio.java
 * @author Pedro Gárate
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class SilentRadio extends JFrame
{
	private JPanel jpButton, jpBanner;
	private JLabel jlNews;
	private JButton left, right, fast, slow, stop;
	private Timer timer;
	private int delay = 20;
	private ArrayList<String> newsList = new ArrayList<String>();
	private String str;
    
	static final int HEIGHT = 200;
    static final int WIDTH = 400;
    private int x = 399;
    private int i = 0;
    private int length;
    private int count = 0;
    private final int Y = 40;
    
    //******************* SilentRadio **************************
    public SilentRadio() 
    {
    	OpenFile();
    	str = newsList.get(i);
    	length = str.length();
    	    	
    	setTitle("Silent Radio");
        setSize(WIDTH,HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocation(400,100);
        setLayout(new BorderLayout());
        
        buildPanels();
        add(jpBanner,BorderLayout.CENTER);
        add(jpButton,BorderLayout.SOUTH);
        
        timer = new Timer(delay,new ScrollListener());
        timer.start();
        setVisible(true);
        
    }
    
    //******************* OpenFile *****************************
    private void OpenFile()
	{
        try
        {
        	Scanner sc = new Scanner(new File("news.txt"));
            while(sc.hasNext())
            {
            	newsList.add(sc.nextLine());
            }
            sc.close();
        }catch(FileNotFoundException e){};
    }
    
    //******************* buildPanels **************************
    private void buildPanels()
    {
		jlNews = new JLabel();
		jlNews.setHorizontalAlignment(SwingConstants.CENTER);
		jlNews.setFont(new Font("Courier", Font.BOLD, 40));
		jlNews.setForeground(Color.WHITE);
		jlNews.setText(str);
		jlNews.setBounds(new Rectangle(new Point(x, Y), jlNews.getPreferredSize()));
		
		jpBanner = new JPanel(null);
		jpBanner.setBackground(Color.BLACK);
		jpBanner.add(jlNews);
		
		jpButton = new JPanel(new FlowLayout());
		left = new JButton("Left"); right = new JButton("Right");
		fast = new JButton("Fast"); slow = new JButton("Slow");
		stop = new JButton("Stop");
		
		jpButton.add(left); jpButton.add(right);
		jpButton.add(fast); jpButton.add(slow); jpButton.add(stop);
		
		//******************* right button listener ************
		right.addActionListener(new ActionListener()
	    {
	        public void actionPerformed(ActionEvent e)
	        {
	        	timer.stop();
	        	x=x+24;
	        	stop.setText("Start");
	        	jlNews.setBounds(new Rectangle(new Point(x, Y), jlNews.getPreferredSize()));
	        }
        });
		
		//******************* left button listener ************
		left.addActionListener(new ActionListener()
	    {
		    public void actionPerformed(ActionEvent e)
		    {
		    	timer.stop();
		    	x=x-24;
		    	stop.setText("Start");
	        	jlNews.setBounds(new Rectangle(new Point(x, Y), jlNews.getPreferredSize()));
		    }
	    });
	
		//******************* fast button listener ************
		fast.addActionListener(new ActionListener()
	    {
	        public void actionPerformed(ActionEvent e)
	        {
	        	delay = delay - 10;
	        	if (delay < 0 ) delay = 0;
	        	timer.setDelay(delay);
	        }
        });
	    
		//******************* slow button listener ************
	    slow.addActionListener(new ActionListener()
	    {
	        public void actionPerformed(ActionEvent e)
	        {
	        	delay = delay + 10;
	        	timer.setDelay(delay);
	        }
        });
	    
	    //******************* stop button listener ************
	    stop.addActionListener(new ActionListener()
	    {
	        public void actionPerformed(ActionEvent e)
	        {
	        	if(stop.getText().equals("Start"))
	        	{
	        		stop.setText("Stop");
	        		timer.restart();
	        	}
	        	else 
	        	{
	        		stop.setText("Start");
	        		timer.stop();
	        	}
	        }
        });
		
	}
	
    //********************** ScrollListener class **************
    private class ScrollListener implements ActionListener
    {
    	//******************* scroll listener ************
    	public void actionPerformed(ActionEvent ae)
        {            
        	x = x-1;
    		if (x==-length*24)
    		{
    			x=399;
    			count++;
    		}
    		
    		if (count==3)
    		{
    			i++;
    			count = 0;
    		}
    		
    		if (i==newsList.size())
    		{
    			i=0;
    		}
    		str = newsList.get(i);
        	length = str.length();
        	jlNews.setText(str);
        	jlNews.setBounds(new Rectangle(new Point(x, Y), jlNews.getPreferredSize()));
        }
    }

    //**************************** main ************************
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                new SilentRadio();
            }
        });
    }

}
