package uk.ac.shef.basic;


import com.cycling74.max.*;
import static com.cycling74.max.MaxObject.post;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.robokind.api.common.position.NormalizedDouble;
import org.robokind.api.motion.Joint;
import org.robokind.api.motion.Robot;
import org.robokind.api.motion.messaging.RemoteRobot;
import org.robokind.api.speech.messaging.RemoteSpeechServiceClient;
import org.robokind.client.basic.Robokind;
import org.robokind.client.basic.UserSettings;
import uk.ac.shef.settings.SetSettings;

public class PDTest extends MaxObject implements Executable {
    private static RemoteRobot myRobot;
    private static Robot.RobotPositionMap myGoalPositions;
    private static RemoteSpeechServiceClient mySpeaker;
    private static PrintWriter pw;
    public PDTest() {
        BufferedReader br = null;
        try {
            String robotID = "myRobot";
            br = new BufferedReader(new FileReader("C:\\Users\\zeno\\Documents\\NetBeansProjects\\zeno-ip.txt"));
            String robotIP = br.readLine();
            System.out.println("ip = "+robotIP);
            //String robotIP = "192.168.0.54";
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
            myGoalPositions = myRobot.getCurrentPositions();
            mySpeaker = Robokind.connectSpeechService();
            pw = new PrintWriter(new BufferedWriter(new FileWriter("C:\\Users\\zeno\\Documents\\NetBeansProjects\\store-positions.txt", true)));
        } catch (Exception ex) {
            Logger.getLogger(PDTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(PDTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /*    @Override
    protected void inlet(float f) {
	post("left elbow yaw " + f + "inlet " + getInlet());
	
        myGoalPositions.put(left_elbow_yaw, new NormalizedDouble(f));
        myRobot.move(myGoalPositions, 100);    
    }*/
    
    private void writeGoalPositionsToFile() {
        int count = 0;
        for (Robot.JointId jid : myGoalPositions.keySet()) {
            NormalizedDouble n = myGoalPositions.get(jid);
            pw.println(jid + "\t" + n);
            ++count;
        }
        post("writing to file "+count);
      
        pw.flush();
    }
    
    @Override
    protected void list(Atom content[]) {
        if (content[0].isString()) {
            if (content[0].toString().equals("dump")) {
                post("Dumped to file");
                pw.println(content[1]);
                writeGoalPositionsToFile();
            }
            speak(content);
            
        } else {
            float f = content[0].getFloat();
            String jointName = content[1].getString();
            int jointId = content[2].getInt();
            post(f+"\t"+jointName+"\t"+jointId);
            Robot.JointId jid = new Robot.JointId(myRobot.getRobotId(), new Joint.Id(jointId)); 
            myGoalPositions.put(jid, new NormalizedDouble(f));
            myRobot.move(myGoalPositions, 100);  
        }
    }
    
    void speak(Atom content[]) {
        String text = "";
        for (int i=1;i<content.length;++i) {
            text += content[i]+" ";
        }
        mySpeaker.speak(text);
    }
    
        
   

    public void execute() {
	System.out.println("allo");
    }
	
}
