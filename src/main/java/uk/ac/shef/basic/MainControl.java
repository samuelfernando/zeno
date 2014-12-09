/**
 * Robot control
 * @author Tarlan Suleymanov
 */
package uk.ac.shef.basic;


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
import org.robokind.api.vision.messaging.RemoteImageRegionServiceClient;
import static org.robokind.client.basic.RobotJoints.*;

public class MainControl {
  
    private static RemoteRobot myRobot;
    private static RemoteAnimationPlayerClient myPlayer;
    private static RemoteSpeechServiceClient mySpeaker;
    private static RobotPositionMap myGoalPositions;
    private static RemoteImageRegionServiceClient myVision;
    private static long animLen;

    //initialise connection with the robot
    public static void init(){
        ///////////////////////////////////////////
        /// CONFIG
        ///////////////////////////////////////////        
        String robotID = "myRobot";
        String robotIP = "192.168.0.105";
        
        
        
        //////////////////////////////////////////////////////
        // SETTINGS - this is handled in SetSettings.java ////
        //////////////////////////////////////////////////////
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
        //myVision = Robokind.connectImageRegionService();
        mySpeaker.speak("Start.");
        Robokind.sleep(2000);
        
        //setting starting postition
        Animation introAnim = Robokind.loadAnimation("animations/Start_Position.xml");
        AnimationJob playAnimation = myPlayer.playAnimation(introAnim);
        animLen = introAnim.getLength();
        Robokind.sleep(1000 + animLen);        
    }

    //disconnect the connection with the robot
    public static void disconnect(){
        Robokind.disconnect();
    }
    
    //run animation to drive forwards
    public static void forward(){
	//robot speaks
        mySpeaker.speak("Moving forward.");
	//waits 2 seconds for speaking
        Robokind.sleep(2000);

	//plays animation to drive forward
        Animation animation = Robokind.loadAnimation("animations/Forward.xml");
        AnimationJob playAnimation = myPlayer.playAnimation(animation);
        animLen = animation.getLength();
        Robokind.sleep(500 + animLen);        
    }
    
    //run animation to drive backwards
    public static void backward(){
        mySpeaker.speak("Moving backward.");
        Robokind.sleep(2000);

        Animation animation = Robokind.loadAnimation("animations/Backward.xml");
        AnimationJob playAnimation = myPlayer.playAnimation(animation);
        animLen = animation.getLength();
        Robokind.sleep(500 + animLen);
    }
    
    //run animation to turn left
    public static void turnLeft(){
        Animation animation = Robokind.loadAnimation("animations/Turn_Left.xml");
        AnimationJob playAnimation = myPlayer.playAnimation(animation);
        animLen = animation.getLength();
        Robokind.sleep(500 + animLen);
    }
    
    //run animation to turn right
    public static void turnRight(){
        Animation animation = Robokind.loadAnimation("animations/Turn_Right.xml");
        AnimationJob playAnimation = myPlayer.playAnimation(animation);
        animLen = animation.getLength();
        Robokind.sleep(500 + animLen);
    }
    
    //run animation to make half left turn
    public static void turnHalfLeft(){
        Animation animation = Robokind.loadAnimation("animations/Turn_Left_Half.xml");
        AnimationJob playAnimation = myPlayer.playAnimation(animation);
        animLen = animation.getLength();
        Robokind.sleep(500 + animLen);
    }
    
    //run animation to make half right turn
    public static void turnHalfRight(){
        Animation animation = Robokind.loadAnimation("animations/Turn_Right_Half.xml");
        AnimationJob playAnimation = myPlayer.playAnimation(animation);
        animLen = animation.getLength();
        Robokind.sleep(500 + animLen);
    }
    
    //run animation to turn left and drive forwards
    public static void turnLeftAndGo(){
        mySpeaker.speak("Turning left.");
        Robokind.sleep(2000);

        turnLeft();
        forward();
        turnHalfRight();
    }    

    //run animation to turn right and drive backwards
    public static void turnRightAndGo(){
        mySpeaker.speak("Turning right.");
        Robokind.sleep(2000);

        turnRight();
        forward();
        turnHalfLeft();
    }
    
    public static void speak(String text, int time) {
        mySpeaker.speak(text);
        Robokind.sleep(time);
    }
    
    public static void turnHeadLeft(){
        Animation animation = Robokind.loadAnimation("animations/Turn_Head_Left.xml");
        AnimationJob playAnimation = myPlayer.playAnimation(animation);
        animLen = animation.getLength();
        Robokind.sleep(500 + animLen);
    }

    public static void turnHeadRight(){
        Animation animation = Robokind.loadAnimation("animations/Turn_Head_Right.xml");
        AnimationJob playAnimation = myPlayer.playAnimation(animation);
        animLen = animation.getLength();
        Robokind.sleep(500 + animLen);
    }

    
}