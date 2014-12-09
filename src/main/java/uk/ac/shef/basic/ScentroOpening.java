package uk.ac.shef.basic;

import java.io.BufferedReader;
import java.io.FileReader;
import org.robokind.api.animation.Animation;
import org.robokind.api.animation.messaging.RemoteAnimationPlayerClient;
import org.robokind.api.animation.player.AnimationJob;
import org.robokind.api.common.position.NormalizedDouble;
import org.robokind.api.motion.Joint;
import org.robokind.api.motion.messaging.RemoteRobot;
import org.robokind.api.speech.messaging.RemoteSpeechServiceClient;
import org.robokind.client.basic.Robokind;
import org.robokind.client.basic.UserSettings;
import static org.robokind.api.motion.Robot.*;
import org.robokind.api.speech.SpeechJob;
import org.robokind.api.speech.utils.DefaultSpeechJob;
import static org.robokind.client.basic.RobotJoints.*;
import uk.ac.shef.settings.SetSettings;

/**
 * App.java
 * @author Lianne Meah
 * @version 3.0
 */

public class ScentroOpening {
  
    private static RemoteRobot myRobot;
    private static RemoteAnimationPlayerClient myPlayer;
    private static RemoteSpeechServiceClient mySpeaker;
    private static RobotPositionMap myGoalPositions;
    
    @SuppressWarnings("empty-statement")
    public static void main(String[] args) {
        long animLen;
        
        ///////////////////////////////////////////
        /// CONFIG
        ///////////////////////////////////////////
        
        String robotID = "myRobot";
        //String robotIP = "192.168.0.54";
        try {
         BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\samf\\Documents\\NetBeansProjects\\zeno-ip.txt"));
         String robotIP = br.readLine();
       System.out.println("ip = "+robotIP);
        
         // String robotIP = "143.167.145.225";
        
        //////////////////////////////////////////////////////
        // SETTINGS - this is handled in SetSettings.java ////
        //////////////////////////////////////////////////////
        
        ///////////// use line 45 ////////////////////////////
        SetSettings settings = new SetSettings(robotID, robotIP);
        //////////// instead of 47-55 ////////////////////////
        UserSettings.setRobotId(robotID);
        UserSettings.setRobotAddress(robotIP);
        UserSettings.setAnimationAddress(robotIP);
        UserSettings.setSpeechAddress(robotIP);
        UserSettings.setSensorAddress(robotIP);
        UserSettings.setAccelerometerAddress(robotIP);
        UserSettings.setGyroscopeAddress(robotIP);
        UserSettings.setCompassAddress(robotIP);
        UserSettings.setCameraAddress(robotIP);
        //////////// End settings //////////////////////////////
        myRobot = Robokind.connectRobot();
        myPlayer = Robokind.connectAnimationPlayer();
        mySpeaker = Robokind.connectSpeechService();
  
        myGoalPositions = myRobot.getDefaultPositions();
        myRobot.move(myGoalPositions, 100);
        ///////////////////////////////////////////
        /// LOADING ANIMATIONS
        ///////////////////////////////////////////
        SpeechJob currentJob = null;
        Animation anim = Robokind.loadAnimation("animations/avatar_wave.anim.xml");
       // AnimationJob introJob = myPlayer.playAnimation(introAnim);
        myPlayer.playAnimation(anim);
        animLen = anim.getLength();
        //Robokind.sleep(500 + animLen);
        
        ///////////////////////////////////////////
        /// SPEAKING
        ///////////////////////////////////////////
        
        // make the robot speak
        mySpeaker.speak("Hello everyone and welcome to Sentro.");
        Robokind.sleep(500 + animLen);
         myGoalPositions = myRobot.getDefaultPositions();
        myRobot.move(myGoalPositions, 500);
        
        anim = Robokind.loadAnimation("animations/AZR50_VictoryPose_02.anim.xml");
        myPlayer.playAnimation(anim);
        animLen = anim.getLength();
        mySpeaker.speak("I am so happy to be here at Scentro.");
        Robokind.sleep(500 + animLen);
      
         myGoalPositions = myRobot.getDefaultPositions();
        myRobot.move(myGoalPositions, 500);
        
        
        /*currentJob = mySpeaker.speak("I am going to the museum next month and I will meet children for the first time.");
        
        while (currentJob.getStatus()!=DefaultSpeechJob.COMPLETE) {Robokind.sleep(200);}
       
        currentJob = mySpeaker.speak("I am going to play games with them and have fun.");

        while (currentJob.getStatus()!=DefaultSpeechJob.COMPLETE) {Robokind.sleep(200);}
       */
        currentJob = mySpeaker.speak("I hope you like me.");
        while (currentJob.getStatus()!=DefaultSpeechJob.COMPLETE) {Robokind.sleep(200);}

        
        anim = Robokind.loadAnimation("animations/AZR50_Disappointed_01.anim.xml");
        myPlayer.playAnimation(anim);
        animLen = anim.getLength();
      
        mySpeaker.speak("If you don't like me I will get very sad.");
        Robokind.sleep(500 + animLen);
        
   /*      myGoalPositions = myRobot.getDefaultPositions();
        myRobot.move(myGoalPositions, 100);
        currentJob = mySpeaker.speak("Over the next few years I hope I can make friends.");
        while (currentJob.getStatus()!=DefaultSpeechJob.COMPLETE) {Robokind.sleep(200);}
 
        anim = Robokind.loadAnimation("animations/AZR50_VictoryPose_02.anim.xml");
        myPlayer.playAnimation(anim);
        animLen = anim.getLength();
        mySpeaker.speak("I will be happy when I make friends.");
        Robokind.sleep(500 + animLen);
     */ 
        currentJob = mySpeaker.speak("It was nice to meet you all and I hope you enjoy the rest of your tour."); 
        while (currentJob.getStatus()!=DefaultSpeechJob.COMPLETE) {Robokind.sleep(200);}
 
        anim = Robokind.loadAnimation("animations/avatar_wave.anim.xml");
       // AnimationJob introJob = myPlayer.playAnimation(introAnim);
        myPlayer.playAnimation(anim);
        animLen = anim.getLength();
        
       // Robokind.sleep(500 + animLen);
        mySpeaker.speak("Goodbye.");
        Robokind.sleep(500 + animLen);
 
                ///////////////////////////////////////////
        /// MOVING THE JOINTS
        ///////////////////////////////////////////
        
        /*JointId left_elbow_yaw = new JointId(myRobot.getRobotId(), new Joint.Id(LEFT_ELBOW_YAW)); 
        JointId left_elbow_pitch = new JointId(myRobot.getRobotId(), new Joint.Id(LEFT_ELBOW_PITCH));
        
        myGoalPositions = new RobotPositionHashMap();
        myGoalPositions.put(left_elbow_yaw, new NormalizedDouble(0.38));
        myGoalPositions.put(left_elbow_pitch, new NormalizedDouble(0.99));
        myRobot.move(myGoalPositions, 1000);
        */
        
        myGoalPositions = myRobot.getDefaultPositions();
        myRobot.move(myGoalPositions, 1000);

        ///////////////////////////////////////////
        /// DISCONNECT AND EXIT
        ///////////////////////////////////////////
        
        Robokind.disconnect();
        System.exit(0);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}