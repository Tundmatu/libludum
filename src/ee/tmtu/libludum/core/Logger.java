package ee.tmtu.libludum.core;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    public static final String TIME_FORMAT = "HH:mm:ss";

    public static final Logger ASSET = new Logger("Asset");
    public static final Logger CORE = new Logger("Core");
    public static final Logger GFX = new Logger("Graphics");
    public static final Logger SND = new Logger("Sound");

    public static int master = LoggerLevel.NONE;

    public int level;
    public String major;

    public Logger(String major) {
        this(major, LoggerLevel.DEBUG);
    }

    public Logger(String major, int level) {
        this.major = major;
        this.level = level;
    }

    public void err(String minor, String msg) {
        if(this.level < LoggerLevel.ERROR || (Logger.master != LoggerLevel.NONE && Logger.master < LoggerLevel.ERROR)) return;
        this.log(minor, msg, true);
    }

    public void err(String msg) {
        if(this.level < LoggerLevel.ERROR || (Logger.master != LoggerLevel.NONE && Logger.master < LoggerLevel.ERROR)) return;
        this.log(msg, true);
    }

    public void wrn(String minor, String msg) {
        if(this.level < LoggerLevel.WARNING || (Logger.master != LoggerLevel.NONE && Logger.master < LoggerLevel.WARNING)) return;
        this.log(minor, msg, true);
    }

    public void wrn(String msg) {
        if(this.level < LoggerLevel.WARNING || (Logger.master != LoggerLevel.NONE && Logger.master < LoggerLevel.WARNING)) return;
        this.log(msg, true);
    }

    public void log(String minor, String msg) {
        if(this.level < LoggerLevel.DEBUG || (Logger.master != LoggerLevel.NONE && Logger.master < LoggerLevel.DEBUG)) return;
        this.log(minor, msg, true);
    }

    public void log(String msg) {
        if(this.level < LoggerLevel.DEBUG || (Logger.master != LoggerLevel.NONE && Logger.master < LoggerLevel.DEBUG)) return;
        this.log(msg, true);
    }

    private void log(String minor, String msg, boolean error) {
        String out = String.format("(%s) [%s - %s] - [%s] %s", Logger.getTime(), this.major, Logger.getLevel(this.level), minor, msg);
        if (error) {
            System.err.println(out);
        } else {
            System.out.println(out);
        }
    }

    private void log(String msg, boolean error) {
        String out = String.format("(%s) [%s - %s] %s", Logger.getTime(), this.major, Logger.getLevel(this.level), msg);
        if (error) {
            System.err.println(out);
        } else {
            System.out.println(out);
        }
    }

    public static void override(int level) {
        Logger.master = level;
    }

    public static String getLevel(int level) {
        switch (level) {
            case LoggerLevel.ERROR:
                return "Error";
            case LoggerLevel.WARNING:
                return "Warning";
            case LoggerLevel.DEBUG:
                return "Debug";
            case LoggerLevel.NONE:
                return "None";
            default:
                return "?";
        }
    }

    private static String getTime() {
        return new SimpleDateFormat(Logger.TIME_FORMAT).format(new Date());
    }

}
