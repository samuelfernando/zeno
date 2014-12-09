/**
 *  Robot control and GUI for tele-operated driving
 * @author Tarlan Suleymanov
 */
package uk.ac.shef.basic;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class Buttons extends JFrame implements ActionListener
{
    public static MainControl control = new MainControl();
    private JButton[] buttons;
    private JTextField inputText = new JTextField(10);

    public Buttons()
    {
    	
        JPanel buttonPanel = new JPanel();
        JPanel buttonPanel2 = new JPanel();
        JPanel buttonPanel3 = new JPanel();

        buttons = new JButton[6];
        String text;

            text = "Forward";
            JButton button = new JButton( text );
            button.addActionListener( this );
            buttons[0] = button;
            buttonPanel.add(button);

            text = "Left";
            JButton button1 = new JButton( text );
            button1.addActionListener( this );
            buttons[1] = button1;
            buttonPanel2.add( button1);

            
            text = "LeftToS";
            JButton button2 = new JButton( text );
            button2.addActionListener( this );
            buttons[2] = button2;
            buttonPanel2.add( button2);
/* 
            text = "Disconnect";
            JButton button3 = new JButton( text );
            button3.addActionListener( this );
            buttons[3] = button3;
            buttonPanel2.add( button3 );
*/
            text = "RightToS";
            JButton button3 = new JButton( text );
            button3.addActionListener( this );
            buttons[3] = button3;
            buttonPanel2.add( button3);
            
            text = "Right";
            JButton button4 = new JButton( text );
            button4.addActionListener( this );
            buttons[4] = button4;
            buttonPanel2.add( button4 ); 

            text = "Backward";
            JButton button5 = new JButton( text );
            button5.addActionListener( this );
            buttons[5] = button5;
            buttonPanel3.add( button5 );
            
        getContentPane().add(buttonPanel, BorderLayout.NORTH);
        getContentPane().add(buttonPanel2, BorderLayout.CENTER);
        getContentPane().add(buttonPanel3, BorderLayout.SOUTH);
        setResizable( false );
    }
    
    public Buttons(String tmp) {
        
        setLayout(new GridLayout(15,1));
  
        buttons = new JButton[15];
        String text;

            text = "Hello";
            JButton button = new JButton( text );
            button.addActionListener( this );
            buttons[0] = button;

            text = "Thanks";
            JButton button1 = new JButton( text );
            button1.addActionListener( this );
            buttons[1] = button1;

            text = "Sure";
            JButton button2 = new JButton( text );
            button2.addActionListener( this );
            buttons[2] = button2;

            text = "Turn";
            JButton button3 = new JButton( text );
            button3.addActionListener( this );
            buttons[3] = button3;

            text = "Ask";
            JButton button4 = new JButton( text );
            button4.addActionListener( this );
            buttons[4] = button4;

            text = "Wow";
            JButton button5 = new JButton( text );
            button5.addActionListener( this );
            buttons[5] = button5;
            
            text = "Go";
            JButton button6 = new JButton( text );
            button6.addActionListener( this );
            buttons[6] = button6;

            text = "Teach";
            JButton button7 = new JButton( text );
            button7.addActionListener( this );
            buttons[7] = button7;

            text = "Measure-T";
            JButton button8 = new JButton( text );
            button8.addActionListener( this );
            buttons[8] = button8;
            
            text = "-- Show";
            JButton button9 = new JButton( text );
            button9.addActionListener( this );
            buttons[9] = button9;
            
            text = "-- Expert";
            JButton button10 = new JButton( text );
            button10.addActionListener( this );
            buttons[10] = button10;

            text = "See";
            JButton button11 = new JButton( text );
            button11.addActionListener( this );
            buttons[11] = button11;
            
            text = "Take care";
            JButton button12 = new JButton( text );
            button12.addActionListener( this );
            buttons[12] = button12;
            

            text = "Head Left";
            JButton button13 = new JButton( text );
            button13.addActionListener( this );
            buttons[13] = button13;
            
            text = "Head Right";
            JButton button14 = new JButton( text );
            button14.addActionListener( this );
            buttons[14] = button14;

            
            add(button);
            add(button1);
            add(button2);
            add(button3);
            add(button4);
            add(button5);
            add(button6);
            add(button7);
            add(button8);
            add(button9);
            add(button10);
            add(button11);
            add(button12);
            add(button13);
            add(button14);
        setResizable( false );
    }
    
    public Buttons(String tmp, String tmp2) {
        
        setLayout(new GridLayout(3,1));
  
        buttons = new JButton[3];
        String text;

            text = "Speak";
            JButton button = new JButton( text );
            button.addActionListener( this );
            buttons[0] = button;

            
            add(inputText);
            add(button);
        setResizable( false );    
    }    

    
    public void actionPerformed(ActionEvent e)
    {
        JButton source = (JButton)e.getSource();

        if(source.getActionCommand().equals("Left") ) {
        	System.out.println("Left");
                MainControl.turnLeft();
        }
        if(source.getActionCommand().equals("LeftToS")) {
        	System.out.println("LeftToS");
                MainControl.turnHalfLeft();
        }
        if(source.getActionCommand().equals("Forward")) {
        	System.out.println("Forward");
                MainControl.forward();
        }
        if(source.getActionCommand().equals("Backward")) {
        	System.out.println("Backward");
                MainControl.backward();
        }
        if(source.getActionCommand().equals("RightToS")) {
        	System.out.println("RightToS");
                MainControl.turnHalfRight();
        }
        if(source.getActionCommand().equals("Right")) {
        	System.out.println("Right");
                MainControl.turnRight();
        }

        
        

        if(source.getActionCommand().equals("Hello")) {
        	System.out.println("Hello");
                MainControl.speak("Hello Nao. How are you doing? I haven't seen you for so long.", 5000);
        }
        
        if(source.getActionCommand().equals("Thanks")) {
        	System.out.println("Thanks");
                MainControl.speak("Well, Thanks to Sheffield University, I can drive, very well in deed.", 5000);
        }
        if(source.getActionCommand().equals("Sure")) {
        	System.out.println("Sure");
                MainControl.speak("Yes, sure.", 5000);
        }
        if(source.getActionCommand().equals("Turn")) {
        	System.out.println("Turn");
                MainControl.speak("I can also turn left and right.", 5000);
        }
        if(source.getActionCommand().equals("Ask")) {
        	System.out.println("Ask");
                MainControl.speak("Why don’t you ask Sheffield University to teach how to drive?", 5000);
        }

        if(source.getActionCommand().equals("Wow")) {
        	System.out.println("Wow");
                MainControl.speak("Wow. That is interesting. So did you start doing some interesting moves?", 6000);
        }
        if(source.getActionCommand().equals("Go")) {
        	System.out.println("Go");
                MainControl.speak("Go for it", 2000);
        }
        

        if(source.getActionCommand().equals("Teach")) {
        	System.out.println("Teach");
                MainControl.speak("Wow. That was briliant.", 5000);
        }
        if(source.getActionCommand().equals("Measure-T")) {
        	System.out.println("Measure");
                MainControl.speak("So why don't you teach me in your spear time?", 6000);
                //MainControl.speak("Interesting. Can you already measure? Or are you just starting to learn?", 6000);
        }
        if(source.getActionCommand().equals("-- Show")) {
        	System.out.println("Show");
                MainControl.speak("Please, show me.", 2000);
        }


        if(source.getActionCommand().equals("------ Expert")) {
        	System.out.println("Expert");
                MainControl.speak("Wow. You already expert in every thing, well done!", 5000);
        }
        if(source.getActionCommand().equals("See")) {
        	System.out.println("See");
                MainControl.speak("Interesting, OK then, I will see you around and keep it up", 3000);
        }
        if(source.getActionCommand().equals("Take care")) {
        	System.out.println("Take care");
                MainControl.speak("Take care", 2000);
        }
        


        if(source.getActionCommand().equals("Head Left")) {
        	System.out.println("Head Left");
                MainControl.turnHeadLeft();
        }
        if(source.getActionCommand().equals("Head Right")) {
        	System.out.println("Head Right");
                MainControl.turnHeadRight();
        }
        
        
        
        
        if(source.getActionCommand().equals("Speak")) {
        	System.out.println("Speaking");
        	System.out.println(inputText.getText());
                MainControl.speak(inputText.getText(), 4000);
        }
        /*
        if(source.getActionCommand().equals("Disconnect")) {
        	System.out.println("Disconnect");
                MainControl.disconnect();
        }
        */
    }

    public static void main(String[] args)
    {
        //intialise connection with the robot
        MainControl.init();
        //display the GUI
        UIManager.put("Button.margin", new Insets(30, 30, 30, 30) );
        Buttons frame = new Buttons();
        frame.setDefaultCloseOperation( EXIT_ON_CLOSE );
        frame.pack();
        frame.setLocation(300,300);
        //frame.setLocationRelativeTo( null );
        frame.setVisible(true);

        UIManager.put("Button.margin", new Insets(5, 30, 5, 30) );
        Buttons frameForDialog = new Buttons("tmp");
        frameForDialog.setDefaultCloseOperation( EXIT_ON_CLOSE );
        frameForDialog.pack();
        frameForDialog.setLocation(900,300);
        //frameForDialog.setLocationRelativeTo( null );
        frameForDialog.setVisible(true);
 
        UIManager.put("Button.margin", new Insets(5, 30, 5, 30) );
        Buttons frameForInput = new Buttons("tmp", "tmp2");
        frameForInput.setDefaultCloseOperation( EXIT_ON_CLOSE );
        frameForInput.pack();
        frameForInput.setLocation(1200,300);
        //frameForInput.setLocationRelativeTo( null );
        frameForInput.setVisible(true);

       
        System.out.println("HERE");
    }
}
