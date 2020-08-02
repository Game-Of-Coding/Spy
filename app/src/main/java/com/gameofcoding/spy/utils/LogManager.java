package com.gameofcoding.spy.utils;

import java.io.File;
import android.util.Log;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

class LogManager {
    public static final String LOG_FILE_NAME = "appLog.log";
    private static File mLogFile;

    public LogManager(File dir) {
	mLogFile = new File(dir, LOG_FILE_NAME);
    }

    public void addLog(int priority, String tag, String msg) {
	addLog(priority, tag, msg, null);
    }

    public void addLog(int priority, String tag, String msg, Throwable tr) {
	final String TAB_SIZE = " ";
	StringBuilder logLine = new StringBuilder();
	try {
	    // Parse log date and time
	    DateFormat dateFormat = new SimpleDateFormat("dd-MM HH:mm:ss.SSS", Locale.getDefault());
	    logLine.append(dateFormat.format(new Date()));

	    // Parse priority
	    logLine.append(TAB_SIZE);
	    String prioritySymbol = null;
	    switch (priority) {
	    case XLog.VERBOSE:
		prioritySymbol = "V";
		break;
	    case XLog.ERROR:
		prioritySymbol = "E";
		break;
	    case XLog.INFO:
		prioritySymbol = "I";
		break;
	    case XLog.WARN:
		prioritySymbol = "W";
		break;
	    case XLog.DEBUG:
		prioritySymbol = "D";
		break;
	    }
	    logLine.append(prioritySymbol);

	    // Parse tag
	    logLine.append(TAB_SIZE);
	    if(tag.length() > 20)
		tag = tag.substring(0, 9) + ".." + tag.substring(tag.length() - 9, tag.length());
	    else if(tag.length() < 20) {
		while(tag.length() != 20)
		    tag += " ";
	    }
	    logLine.append(tag);

	    // Parse message
	    logLine.append(TAB_SIZE);
	    logLine.append(msg);

	    // Parse exception, if has
	    if (tr != null) {
		logLine.append("\nEXCEPTION: '");
		logLine.append(Log.getStackTraceString(tr));
	    }

	    // Add new line so that future logs would be added in new line
	    logLine.append("\n");

	    // Add above log to previously stored logs in external storage
	    FileWriter file = new FileWriter(mLogFile, true);
            file.write(logLine.toString());
            file.flush();
	    file.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
