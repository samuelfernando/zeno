/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.shef.basic;

import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.List;
import org.robokind.api.common.position.NormalizedDouble;
import org.robokind.api.motion.Joint;
import org.robokind.api.motion.Robot;
import org.robokind.api.motion.servos.ServoJoint;
import org.robokind.client.basic.Robokind;
import org.robokind.client.basic.UserSettings;
import uk.ac.shef.settings.SetSettings;

/**
 *
 * @author Polly
 */
public class MakePD {
    public static void main(String args[]) {
        MakePD mpd = new MakePD();
        try {
            mpd.start();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    void start() throws Exception {
        PrintStream out = new PrintStream("pd-zeno.pd");
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
        Robot myRobot = Robokind.connectRobot();
        out.println("#N canvas 100 100 1600 900;");
        out.println("#X obj 800 450 pdj uk.ac.shef.basic.PDTest;");
        List<Joint> jlist = myRobot.getJointList();
        int count=0;
        Robot.RobotPositionMap myCurrentPositions = myRobot.getCurrentPositions();
        for (Joint joint : jlist) {
            String name = joint.getName();
            Joint.Id id = joint.getId();
            String norm = name.toLowerCase().replaceAll(" ", "_")+" "+id;
            count++;
            int x = (count % 5)*200;
            int y = (count / 5) * 200;
            Robot.JointId myjid = new Robot.JointId(myRobot.getRobotId(), id);
            NormalizedDouble position = myCurrentPositions.get(myjid);
            DecimalFormat df = new DecimalFormat("#.#");
            String def = "0";
            if (position==null) {
                System.out.println(name+" null");
            }
            else {
                def = df.format(position.getValue()*100);
            }
            String slider = "#X obj "+x+" "+y+" hsl 128 15 0 1 0 0 empty empty empty -2 -8 0 10 -262144 -1 -1 " +def+" 1;";
            String label;
            label = "#X obj "+x+" "+(y+10)+" list append "+norm+";";
            out.println(slider+"\n"+label);
            out.println("#X connect "+(count*2-1)+" 0 "+(count*2)+" 0;");
            out.println("#X connect "+(count*2)+" 0 "+0+" 0;");
            
        }
        
    }
}
