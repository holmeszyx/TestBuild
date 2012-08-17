package z.hol.testbuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

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
    	StringBuilder sb = new StringBuilder();
    	Class<Build> b = android.os.Build.class;
    	Field[] fields = b.getFields();
    	sb.append("======");
    	sb.append("\n");
    	sb.append("Build");
    	sb.append("\n");
    	sb.append("======");
    	sb.append("\n");
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
    		String str = name + " : " + v;
    		sb.append(str);
    		sb.append("\n");
    		sb.append("\n");
    	}
    	
    	// --- get versions---
    	
    	Class<Build.VERSION> version = android.os.Build.VERSION.class;
    	Field[] vfields = version.getFields();
    	sb.append("=============");
    	sb.append("\n");
    	sb.append("Build.VERSION");
    	sb.append("\n");
    	sb.append("=============");
    	sb.append("\n");
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
    		String str = name + " : " + v;
    		sb.append(str);
    		sb.append("\n");
    		sb.append("\n");
    	}
    	sb.append("=========");
    	sb.append("\n");
    	sb.append("Who am i");
    	sb.append("\n");
    	sb.append("=========");
    	sb.append("\n");
    	sb.append(whoami());
    	sb.append("\n");
    	txt.setText(sb.toString());
    }
    
    private String whoami(){
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
				reader.close();
	    		in.close();
	    		process.destroy();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		return sb.toString();
    	}
    	return null;
    }
}
