import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier; 
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener; 
import java.util.Enumeration;
import Push.*;

public class SerialTest implements SerialPortEventListener {

  private int index = 0;
  private double[] samples = new int[500];
  private double previous_avg = 0;

  private void processInput(double[] samples) {
    double sum = 0;  // sum of all the elements
    for (int i=0; i<samples.length; i++) {
        sum += samples[i];
    }
    
    double avg = sum / samples.length;
    if (avg < previous_avg) {
      avg *= -1;
    }
    Pusher.triggerPush("emg_data", "new", "{'avg'=>"+avg+"}");
  } 

 SerialPort serialPort;
 private static final String PORT_NAMES[] = { "/dev/tty.usbmodem1411"};
 private BufferedReader input; 
 private OutputStream output;
 private static final int TIME_OUT = 2000;
 private static final int DATA_RATE = 9600;

 public void initialize() {
  CommPortIdentifier portId = null;
  Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
  while (portEnum.hasMoreElements()) {
   CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
   for (String portName : PORT_NAMES) {
    System.out.println("Checking port: "+portName);
    if (currPortId.getName().equals(portName)) {
     portId = currPortId;
     break;
    }
   }
  }
  if (portId == null) {
   System.out.println("Could not find COM port.");
   return;
  }

  try {
   // open serial port, and use class name for the appName.
   serialPort = (SerialPort) portId.open(this.getClass().getName(),
     TIME_OUT);

   // set port parameters
   serialPort.setSerialPortParams(DATA_RATE,
     SerialPort.DATABITS_8,
     SerialPort.STOPBITS_1,
     SerialPort.PARITY_NONE);

   // open the streams
   input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
   output = serialPort.getOutputStream();

   // add event listeners
   serialPort.addEventListener(this);
   serialPort.notifyOnDataAvailable(true);
  } catch (Exception e) {
   System.err.println(e.toString());
  }
 }

 /**
  * This should be called when you stop using the port.
  * This will prevent port locking on platforms like Linux.
  */
 public synchronized void close() {
  if (serialPort != null) {
   serialPort.removeEventListener();
   serialPort.close();
  }
 }

 /**
  * Handle an event on the serial port. Read the data and print it.
  */
 public synchronized void serialEvent(SerialPortEvent oEvent) {
  if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
   try {
    String inputLine=input.readLine();
    System.out.println(inputLine);
    
    if (index < 500) {
      samples[index++] = Double.parse(inputLine);
    } else {
      processInput(samples);
      index =0;
    }

   } catch (Exception e) {
    System.err.println(e.toString());
   }
  }
  // Ignore all the other eventTypes, but you should consider the other ones.
 }

 public static void main(String[] args) throws Exception {
  SerialTest main = new SerialTest();
  main.initialize();
  Thread t=new Thread() {
   public void run() {
    //the following line will keep this app alive for 1000 seconds,
    //waiting for events to occur and responding to them (printing incoming messages to console).
    try {Thread.sleep(1000000);} catch (InterruptedException ie) {}
   }
  };
  t.start();
  System.out.println("Started");
 }
}