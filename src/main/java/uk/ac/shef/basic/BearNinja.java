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
import static org.robokind.client.basic.RobotJoints.*;
import uk.ac.shef.settings.SetSettings;

/**
 * App.java
 * @author Lianne Meah
 * @version 3.0
 */

public class BearNinja {
  
    private static RemoteRobot myRobot;
    private static RemoteAnimationPlayerClient myPlayer;
    private static RemoteSpeechServiceClient mySpeaker;
    private static RobotPositionMap myGoalPositions;
    
    public static void main(String[] args) {
        long animLen;
        
        ///////////////////////////////////////////
        /// CONFIG
        ///////////////////////////////////////////
        
        String robotID = "myRobot";
        //String robotIP = "192.168.0.54";
        try {
         BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\zeno\\Documents\\NetBeansProjects\\zeno-ip.txt"));
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
        
        // make the robot speak
  //      mySpeaker.speak("Let's play a game.");
        Animation anim;
        String[] str = {"One", "Two", "Three"};
        
       // mySpeaker.speak("One. Two. Three");
        
        //animLen = anim.getLength();
       // Robokind.sleep(500 + animLen);
      
        
        
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