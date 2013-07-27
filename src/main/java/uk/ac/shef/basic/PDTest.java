package uk.ac.shef.basic;


import com.cycling74.max.*;
import static com.cycling74.max.MaxObject.post;
import org.robokind.api.common.position.NormalizedDouble;
import org.robokind.api.motion.Joint;
import org.robokind.api.motion.Robot;
import org.robokind.api.motion.messaging.RemoteRobot;
import org.robokind.client.basic.Robokind;
import static org.robokind.client.basic.RobotJoints.LEFT_ELBOW_PITCH;
import static org.robokind.client.basic.RobotJoints.LEFT_ELBOW_YAW;
import org.robokind.client.basic.UserSettings;
import uk.ac.shef.settings.SetSettings;

public class PDTest extends MaxObject implements Executable {
    private static RemoteRobot myRobot;
    private static Robot.RobotPositionMap myGoalPositions;
    private static Robot.JointId left_elbow_yaw;
    private static Robot.JointId left_elbow_pitch;
    
    public PDTest() {
        String robotID = "myRobot";
        String robotIP = "192.168.0.54";
        
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
        left_elbow_yaw = new Robot.JointId(myRobot.getRobotId(), new Joint.Id(LEFT_ELBOW_YAW)); 
        left_elbow_pitch = new Robot.JointId(myRobot.getRobotId(), new Joint.Id(LEFT_ELBOW_PITCH));
        myGoalPositions = new Robot.RobotPositionHashMap();
        
    }

    /*    @Override
    protected void inlet(float f) {
	post("left elbow yaw " + f + "inlet " + getInlet());
	
        myGoalPositions.put(left_elbow_yaw, new NormalizedDouble(f));
        myRobot.move(myGoalPositions, 100);    
    }*/
    
    protected void list(Atom content[]) {
        float f = content[0].getFloat();
        String jointName = content[1].getString();
        int jointId = content[2].getInt();
        post(f+"\t"+jointName+"\t"+jointId);
        Robot.JointId jid = new Robot.JointId(myRobot.getRobotId(), new Joint.Id(jointId)); 
        myGoalPositions.put(jid, new NormalizedDouble(f));
        myRobot.move(myGoalPositions, 100);  
    }
        
   

    public void execute() {
	System.out.println("allo");
    }
	
}
