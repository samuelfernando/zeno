package uk.ac.shef.basic;


import com.cycling74.max.*;
import static com.cycling74.max.MaxObject.post;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jflux.api.core.Listener;
import org.robokind.api.common.position.NormalizedDouble;
import org.robokind.api.motion.Joint;
import org.robokind.api.motion.Robot;
import org.robokind.api.motion.Robot.RobotPositionHashMap;
import org.robokind.api.motion.messaging.RemoteRobot;
import org.robokind.api.sensor.DeviceBoolEvent;
import org.robokind.api.sensor.DeviceReadPeriodEvent;
import org.robokind.api.sensor.FilteredVector3Event;
import org.robokind.api.sensor.Vector3Event;
import org.robokind.api.sensor.gpio.RemoteGpioServiceClient;
import org.robokind.api.sensor.imu.RemoteAccelerometerServiceClient;
import org.robokind.api.sensor.imu.RemoteCompassServiceClient;
import org.robokind.api.sensor.imu.RemoteGyroscopeServiceClient;
import org.robokind.api.speech.messaging.RemoteSpeechServiceClient;
import org.robokind.client.basic.Robokind;
import org.robokind.client.basic.UserSettings;
import org.robokind.impl.sensor.AccelerometerConfigRecord;
import org.robokind.impl.sensor.CompassConfigRecord;
import org.robokind.impl.sensor.DeviceReadPeriodRecord;
import org.robokind.impl.sensor.GyroConfigRecord;
import org.robokind.impl.sensor.HeaderRecord;


class Command {
    Robot.JointId joint;
    float position;
    long logTime;
    Command(Robot.JointId joint, float position) {
        this.joint = joint;
        this.position = position;
        logTime = System.currentTimeMillis();
    }
}
public class PDTest extends MaxObject implements Executable {
    private static RemoteRobot myRobot;
    private static Robot.RobotPositionMap myGoalPositions;
    private static RemoteSpeechServiceClient mySpeaker;
    private static PrintWriter pw;
    long lastCheck;
    Stack<Command> commands;
    public PDTest() {
        BufferedReader br = null;
        try {
            String robotID = "myRobot";
            br = new BufferedReader(new FileReader("c:\\zeno-ip.txt"));
            String robotIP = br.readLine();
            System.out.println("ip = "+robotIP);
            //String robotIP = "192.168.0.54";
            //////////////////////////////////////////////////////
            // SETTINGS - this is handled in SetSettings.java ////
            //////////////////////////////////////////////////////
            ///////////// use line 45 ////////////////////////////
            //SetSettings settings = new SetSettings(robotID, robotIP);
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
            myGoalPositions = new RobotPositionHashMap();
            mySpeaker = Robokind.connectSpeechService();
            pw = new PrintWriter(new BufferedWriter(new FileWriter("c:\\store-positions.txt", true)));
            commands = new Stack<Command>();
            initSensors();  
           declareIO(1, 18);
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
  
        Runnable toRun = new Runnable() {
            public void run() {
                //System.out.println("running");
                try {
                    update();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
      
            
        };
      scheduler.scheduleAtFixedRate(toRun, 0, 100, TimeUnit.MILLISECONDS);
            
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
    

    void update() {
        long now = System.currentTimeMillis();
        if (!commands.isEmpty()) {
            Command command = commands.pop();
            Robot.JointId joint = command.joint;
            float position = command.position;
            long logTime = command.logTime;
            
            if (now-logTime>200) {
                myGoalPositions.put(joint, new NormalizedDouble(position));
                myRobot.move(myGoalPositions, 500);  
                post("move "+joint.toString()+" "+position);
                commands.clear();
                myGoalPositions.clear();
            }
            
        }
    }
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
    
    
    void initSensors() { 
        RemoteGpioServiceClient sensors = Robokind.connectSensors();
            DeviceReadPeriodEvent<HeaderRecord> readPeriod =
                    new DeviceReadPeriodRecord();
            HeaderRecord header = new HeaderRecord();
            
            System.out.println("Adding pin direction.");
            
            sensors.setPinDirection(2, true);
            sensors.setPinDirection(4, true);
            
            System.out.println("Adding read period.");
            
            header.setFrameId(0);
            header.setSequenceId(0);
    //        header.setTimestamp(TimeUtils.now());
            header.setTimestamp(0L);
            readPeriod.setHeader(header);
            readPeriod.setPeriod(100.0);
            
            sensors.setReadPeriod(readPeriod);
            
            System.out.println("Adding listener.");
            
            sensors.addListener(new TestGpioListener());
            
            System.out.println("Adding IMU.");
            
            RemoteAccelerometerServiceClient accel =
                    Robokind.connectAccelerometer();
            RemoteGyroscopeServiceClient gyro = Robokind.connectGyroscope();
            RemoteCompassServiceClient compass = Robokind.connectCompass();
            
            readPeriod.setPeriod(1000.0);
            
            accel.setReadPeriod(readPeriod);
            gyro.setReadPeriod(readPeriod);
            compass.setReadPeriod(readPeriod);
            
            AccelerometerConfigRecord accelConfig = new AccelerometerConfigRecord();
            GyroConfigRecord gyroConfig = new GyroConfigRecord();
            CompassConfigRecord compassConfig = new CompassConfigRecord();
            
            accelConfig.setHeader(header);
            gyroConfig.setHeader(header);
            compassConfig.setHeader(header);
            
            accelConfig.setRegisterAddress(45);
            accelConfig.setRegisterValue(8);
            
            gyroConfig.setCtl1(15);
            gyroConfig.setCtl2(-1);
            gyroConfig.setCtl3(-1);
            gyroConfig.setCtl4(-1);
            gyroConfig.setCtl5(-1);
            
            compassConfig.setAverage(3);
            compassConfig.setBias(0);
            compassConfig.setGain(7);
            compassConfig.setRate(2);
            
            accel.sendConfig(accelConfig);
            gyro.sendConfig(gyroConfig);
            compass.sendConfig(compassConfig);
            
            accel.addListener(new TestAccelListener());
            gyro.addListener(new TestGyroListener());
            compass.addListener(new TestCompassListener());
    }
    
        private static class TestGpioListener implements Listener<DeviceBoolEvent> {
        public void handleEvent(DeviceBoolEvent t) {
            //System.out.println(t.getChannelId() + ": " + (t.getBoolValue() ? "on" : "off"));
        }
    }
    
    private class TestAccelListener
        implements Listener<FilteredVector3Event> {
        public void handleEvent(FilteredVector3Event t) {
            Vector3Event v = t.getFilteredVector();
            Vector3Event r = t.getRawVector();
           // System.out.println("Accelerometer (f): " + v.getX() + ", " + v.getY() + ", " + v.getZ());
            //System.out.println("Accelerometer (r): " + r.getX() + ", " + r.getY() + ", " + r.getZ());
            outlet(0, v.getX());
            outlet(1, v.getY());
            outlet(2, v.getZ());
            outlet(3, r.getX());
            outlet(4, r.getY());
            outlet(5, r.getZ());
            
        }
    }
    
    private class TestGyroListener
        implements Listener<FilteredVector3Event> {
        public void handleEvent(FilteredVector3Event t) {
            Vector3Event v = t.getFilteredVector();
            Vector3Event r = t.getRawVector();
            //System.out.println("Gyroscope (f): " + v.getX() + ", " + v.getY() + ", " + v.getZ());
            //System.out.println("Gyroscope (r): " + r.getX() + ", " + r.getY() + ", " + r.getZ());
            outlet(6, v.getX());
            outlet(7, v.getY());
            outlet(8, v.getZ());
            outlet(9, r.getX());
            outlet(10, r.getY());
            outlet(11, r.getZ());
    
        }
    }
    
    private class TestCompassListener
        implements Listener<FilteredVector3Event> {
        public void handleEvent(FilteredVector3Event t) {
            Vector3Event v = t.getFilteredVector();
            Vector3Event r = t.getRawVector();
            //System.out.println("Compass (f): " + v.getX() + ", " + v.getY() + ", " + v.getZ());
            //System.out.println("Compass (r): " + r.getX() + ", " + r.getY() + ", " + r.getZ());
            outlet(12, v.getX());
            outlet(13, v.getY());
            outlet(14, v.getZ());
            outlet(15, r.getX());
            outlet(16, r.getY());
            outlet(17, r.getZ());
        }
    }
    @Override
    protected void list(Atom content[]) {
        long now = System.currentTimeMillis();
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
            //post(f+"\t"+jointName+"\t"+jointId);
            Robot.JointId jid = new Robot.JointId(myRobot.getRobotId(), new Joint.Id(jointId)); 
            //myGoalPositions.put(jid, new NormalizedDouble(f));
            //myRobot.move(myGoalPositions, 1000);  
            Command command = new Command(jid, f);
            commands.push(command);
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
