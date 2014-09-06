package z.hol.testbuilder;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

public class MainActivity extends Activity {
	
	private TextView txt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = (TextView) findViewById(R.id.texts);
        getBuilders();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    public void getBuilders (){
    	
    	// --- get builds ---
        InfoBuilder ib = new InfoBuilder();
    	Class<Build> b = android.os.Build.class;
    	Field[] fields = b.getFields();
        ib.addTitle("Build");
    	for (Field field : fields){
    		String name = field.getName();
    		Object v = null;
    		try {
				v =  field.get(null);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            ib.addInfo(name, v);
            ib.newLine();
    	}
    	
    	// --- get versions---
    	
    	Class<Build.VERSION> version = android.os.Build.VERSION.class;
    	Field[] vfields = version.getFields();
        ib.addTitle("Build.VERSION");
    	for (Field field : vfields){
    		String name = field.getName();
    		Object v = null;
    		try {
				v =  field.get(null);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            ib.addInfo(name, v);
            ib.newLine();
    	}

        ib.addTitle("Who am i");
    	ib.addInfo(whoami());

        ib.addTitle("id command");
        ib.addInfo(cmd("id"));

    	if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN){
    		ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
    		MemoryInfo mi = new MemoryInfo();
    		am.getMemoryInfo(mi);
            ib.addTitle("Memory");
            ib.addInfo("total", String.format("%dB , %dM , %s", mi.totalMem, mi.totalMem >> 20, Formatter.formatFileSize(mi.totalMem, false, null)));
    		ib.addInfo("free", String.format("%dB , %dM , %s", mi.availMem, mi.availMem >> 20, Formatter.formatFileSize(mi.availMem, false, null)));
    	}
    	
    	// IMEI
    	TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
    	if (tm != null){
            ib.addTitle("IMEI");
            ib.addInfo("imei", tm.getDeviceId());
            ib.addInfo("sdcard", Environment.getExternalStorageDirectory().getAbsolutePath());
    	}
    	
    	// System file
    	ib.addTitle("System File");

        ib.addSubTitle("Vold File");
    	ib.addInfo(isVoldExist());
    	ib.addInfo(isVoldRealExist());

        // Su file
        ib.addSubTitle("Su File");
        ib.addInfo("/system/bin/su", isFileExist("/system/bin/su"));
        ib.addInfo("/system/xbin/su", isFileExist("/system/xbin/su"));

        ib.addSubTitle("Which su");
        ib.addInfo(cmd("which su"));

        ib.addSubTitle("su -v command");
        ib.addInfo(cmd("su -v"));

    	txt.setText(ib.toString());
    }

    private String cmd(String cmd){
        int existValue = -1;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        StringBuilder sb = null;
        if (process != null){
            InputStream in = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            sb = new StringBuilder();
            String line = null;
            try {
                while ((line = reader.readLine()) != null){
                    sb.append(line);
                    sb.append("\n");
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            existValue = process.exitValue();
            try {
                reader.close();
                in.close();
                process.destroy();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (sb.length() > 0){
                return sb.toString();
            }
            return "error, exist value: " + existValue;
        }
        return "error, exist value: " + existValue;
    }

    private String whoami(){
        int existValue = -1;
    	Process process = null;
    	try {
			process = Runtime.getRuntime().exec("whoami");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	StringBuilder sb = null;
    	if (process != null){
    		InputStream in = process.getInputStream();
    		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    		sb = new StringBuilder();
    		String line = null;
    		try {
				while ((line = reader.readLine()) != null){
					sb.append(line);
					sb.append("\n");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            try {
                process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            existValue = process.exitValue();
    		try {
				reader.close();
	    		in.close();
                process.getErrorStream().close();
	    		process.destroy();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            if (sb.length() > 0){
                return sb.toString();
            }
            return "error, exist value: " + existValue;
    	}
    	tmpTest();
    	return "error, exist value: " + existValue;
    }
    
    private void tmpTest(){
    	File f = new File("/data/local/tmp/ccc.txt");
    	try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private String isVoldExist(){
    	boolean exist = isFileExist("/system/bin/vold");
    	return "/system/bin/vold , " + exist;
    }

    private String isVoldRealExist(){
    	boolean exist = isFileExist("/system/bin/vold_real");
    	return "/system/bin/vold_real , " + exist;
    }
    
    private boolean isFileExist(String filepath){
    	File file = new File(filepath);
    	return file.exists();
    }
}
