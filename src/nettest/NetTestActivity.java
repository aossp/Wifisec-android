package nettest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class NetTestActivity extends Activity {
			private TextView view2;
		    private Spinner spinner2;
		    private ArrayAdapter adapter2;
		    private EditText et_inputaddress;
			private Button bt_nettest;
		    @Override
		    protected void onCreate(Bundle savedInstanceState) {
		        // TODO Auto-generated method stub
		        super.onCreate(savedInstanceState);
		        setContentView(com.ndsec.wifisec.R.layout.nettest_layout);
		 
		        spinner2 = (Spinner) findViewById(com.ndsec.wifisec.R.id.Spinner01);
		        view2 = (TextView) findViewById(com.ndsec.wifisec.R.id.spinnerText);
		        et_inputaddress = (EditText) findViewById(com.ndsec.wifisec.R.id.ET_inputaddress);
		        bt_nettest = (Button)findViewById(com.ndsec.wifisec.R.id.bt_nettest);
		        
		        //����ѡ������ArrayAdapter��������
		        adapter2 = ArrayAdapter.createFromResource(this, com.ndsec.wifisec.R.array.nettest_tools, android.R.layout.simple_spinner_item);
		        //���������б�ķ��
		        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 
		        //��adapter2 ��ӵ�spinner��
		        spinner2.setAdapter(adapter2);
		 
		        //����¼�Spinner�¼����� 
		        spinner2.setOnItemSelectedListener(new SpinnerXMLSelectedListener());
	
		        //����Ĭ��ֵ
		        spinner2.setVisibility(View.VISIBLE);
		        
		    }
		     
		    //ʹ��XML��ʽ����
		    class SpinnerXMLSelectedListener implements OnItemSelectedListener{
		        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
		                long arg3) {
		           // view2.setText("��ʹ��ʲô�����ֻ���"+adapter2.getItem(arg2));
		            view2.setText(""+Ping("www.baidu.com"));
		          
		          
		            if(arg2==1)
		            {
		            	   //�½���ѯ�̣߳�����UI��������
		                Thread mThread = new Thread(new Runnable() {
		          
							public void run() {                        
		                          try {
		         
									//���ص�������Ҫʹ��'\n'�����У�
		                              URL url = new URL("http://www.cbcye.cn/whois.axd?w="+et_inputaddress.getText().toString());
		                              
		                               URLConnection urlConnection = url.openConnection();
		                               InputStream is = urlConnection.getInputStream();
		                               
		                               /* ��ByteArrayBuffer������ */
		                                        ByteArrayBuffer baf = new ByteArrayBuffer(50);
		                                        int current = 0;
		                                        
		                                        while((current = is.read()) != -1){
		                                             baf.append((byte)current);
		                                        }
		                                        
		                                        /* �����������ת��ΪString�� ��UTF-8���� */
		                                        String myString = EncodingUtils.getString(baf.toByteArray(), "UTF-8");
		                                       //myString = new String(baf.toByteArray());
		                                        //vResult.setText(myString);
		                                        Message mg = Message.obtain();  
		                                        mg.obj = myString;  
		                                        mHandler.sendMessage(mg);  
		                                        
		                              } catch (Exception e) {
		                               e.printStackTrace();
		                              }
		                    }
					 
		          }
		            );
		                 mThread.start();  
		            }
		            
		        }
		 
		        public void onNothingSelected(AdapterView<?> arg0) {
		             
	        }
		         
		    }
		    /**
			 * @param str 
			 * @return avg ���ٶ�
			 */
			public Double Ping(String str) {
				StringBuffer buf = new StringBuffer();
				String s = "";
				Process process;
				String teep = null;
				String teep2 = null;
				String Average = null;
				try {
					String pingUrl = "ping -c 2 -w 100 " + str;
					process = Runtime.getRuntime().exec(pingUrl);
					BufferedReader br = new BufferedReader(new InputStreamReader(
							process.getInputStream()));

					while ((s = br.readLine()) != null) {
						buf.append(s + "");
					}

					process.waitFor();
					Log.e("ds", buf.toString());
					if (buf != null && !buf.equals("") && buf.length() > 1) {
						if (buf != null && buf.length() > 0) {
							teep = buf.substring(buf.lastIndexOf("=") + 1).trim();
						}
						if (teep != null && teep.length() > 0) {
							teep2 = teep.substring(teep.indexOf("/") + 1).toString();
						}
						if (teep2 != null && teep2.length() > 0) {
							Average = teep2.substring(0, teep2.indexOf("/")).toString();
						}
					}
					// Toast.makeText(this, Average, 1).show();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				if (Average != null && !Average.equals("")) {
					return Double.valueOf(Average);
				} else {
					return 0.0;
				}

			}
			private Handler mHandler = new Handler(){  
		        public void handleMessage(Message msg){  
		            String m = (String) msg.obj;
		            if(m.length()<=0)
		            {
		            	view2.setText("��ѯ���Ϊ��.");
		            }
		            else
		            {
		            	view2.setText(m);
		            }
		        }  
		    };  
		}