package mapview;

import javax.sound.sampled.*;
import java.io.*;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbc.JDBC;
 
/**
 * Modified from example provided at www.coderanch.com
 */
public class Recorder {
    Configuration configuration = null;
    StreamSpeechRecognizer recognizer = null;
    
    // record duration, in milliseconds
    static final long RECORD_TIME = 5000;  // 3 seconds
 
    // path of the wav file
    
    
    File wavFile = null;
 
    // format of audio file
    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
 
    // the line from which audio data is captured
    TargetDataLine line;
    
    MainPanel mp;
    
    public Recorder(MainPanel mp) {
        try {
           File jarPath=new File(Recorder.class.getProtectionDomain().getCodeSource().getLocation().getPath());
           String propertiesPath=jarPath.getParentFile().getAbsolutePath();
           
           if(System.getProperty("os.name").startsWith("Windows")) {
               wavFile = new File(propertiesPath + "\\record.wav");
           } else {
               wavFile = new File(propertiesPath + "/record.wav");
           }
       } catch (Exception ex) {
           
       }
        
        this.mp = mp;
        
        Thread loadVoiceEngine = new Thread(new Runnable() {
            public void run() {
                configuration = new Configuration();

                configuration
                        .setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
                configuration
                        .setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
                configuration
                        .setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
                
                configuration.setGrammarPath("resource:/icons/");
                configuration.setGrammarName("command");
                configuration.setUseGrammar(true);
                
                try {
                    recognizer = new StreamSpeechRecognizer(configuration);
                } catch (IOException ex) {
                    Logger.getLogger(Recorder.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });  
    
        loadVoiceEngine.start();
    }
 
    /**
     * Defines an audio format
     */
    AudioFormat getAudioFormat() {
        float sampleRate = 16000;
        int sampleSizeInBits = 16;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = false;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                                             channels, signed, bigEndian);
        return format;
    }
 
    /**
     * Captures the sound and record into a WAV file
     */
    void start() throws IOException {
        try {
            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
 
            // checks if system supports the data line
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported");
                System.exit(0);
            }
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();   // start capturing
 
            System.out.println("Start capturing...");
 
            AudioInputStream ais = new AudioInputStream(line);
 
            System.out.println("Start recording...");
 
            // start recording
            AudioSystem.write(ais, fileType, wavFile);
 
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
 
    /**
     * Closes the target data line to finish capturing and recording
     */
    void finish() {
        line.stop();
        line.close();
        System.out.println("Finished");
        
        InputStream stream = null;
        try {
            File jarPath=new File(Recorder.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            String propertiesPath=jarPath.getParentFile().getAbsolutePath();
            
            if(System.getProperty("os.name").startsWith("Windows")) {
               stream = new FileInputStream(new File(propertiesPath + "\\record.wav"));
            } else {
               stream = new FileInputStream(new File(propertiesPath + "/record.wav"));
            }
        } catch (Exception ex) {
            Logger.getLogger(Recorder.class.getName()).log(Level.SEVERE, null, ex);
        }

        if(stream != null) {
            recognizer.startRecognition(stream);
            SpeechResult result;
            
            String phrase = "";
            while ((result = recognizer.getResult()) != null) {
                phrase += result.getHypothesis();
            }
            
            recognizer.stopRecognition();
            
            phrase = phrase.replace("<unk>", "").trim();
            
            System.out.println("phrase is: " + phrase);
            if(phrase.contains(" to ")) {
                String loc1 = "";
                String loc2 = "";
                
                String[] arr = phrase.trim().split(" to ");
                loc1 = arr[0].trim();
                loc2 = arr[1].trim();
                
                mp.route(loc1, loc2);
            }
        }
        
        mp.isRecording = false;
    }
}

