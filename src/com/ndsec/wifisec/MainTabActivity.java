package com.ndsec.wifisec;

import com.ndsec.wifisec.Constant.ConValue;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;



@SuppressLint("NewApi")
@SuppressWarnings("deprecation")
public class MainTabActivity extends TabActivity {
	//����TabHost����  
	private TabHost tabHost;  
	//����RadioGroup����  
    private RadioGroup radioGroup; 
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
 		switch (item.getItemId()) {
 		case android.R.id.home:
 			// ��ActionBarͼ�걻���ʱ����
 			System.out.println("�����Home��ť��");
 			break;
 		}
 		return super.onOptionsItemSelected(item);
 	}
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
    @SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
    
     ActionBar actionBar = this.getActionBar(); 
     actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP, ActionBar.DISPLAY_HOME_AS_UP);

     setContentView(R.layout.activity_maintab);//Tabҳ��Ĳ���
     initView();  
     initData();
 }
 /** 
  * ��ʼ����� 
  */  
private void initView(){  
     //ʵ����TabHost���õ�TabHost����  
     tabHost = getTabHost();  
       
     //�õ�Activity�ĸ���  
     int count = ConValue.mTabClassArray.length;               
               
     for(int i = 0; i < count; i++){    
         //Ϊÿһ��Tab��ť����ͼ�ꡢ���ֺ�����  
         TabSpec tabSpec = tabHost.newTabSpec(ConValue.mTextviewArray[i]).setIndicator(ConValue.mTextviewArray[i]).setContent(getTabItemIntent(i));  
         //��Tab��ť��ӽ�Tabѡ���  
         tabHost.addTab(tabSpec);  
     }  
       
     //ʵ����RadioGroup  
     radioGroup = (RadioGroup) findViewById(R.id.main_radiogroup);  
 }  
  
 /** 
  * ��ʼ����� 
  */  
    
 private void initData() {  
     // ��radioGroup���ü����¼�  
     radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {  
         @Override  
         public void onCheckedChanged(RadioGroup group, int checkedId) {
        	 if(checkedId==R.id.RadioButton0)
        	 {
        		 tabHost.setCurrentTabByTag(ConValue.mTextviewArray[0]);  
        	 }
        	 else if(checkedId==R.id.RadioButton1){
        		 tabHost.setCurrentTabByTag(ConValue.mTextviewArray[1]);  
        	 }
        	 else if(checkedId==R.id.RadioButton2){
        		 tabHost.setCurrentTabByTag(ConValue.mTextviewArray[2]);  
        	 }
        	 else if(checkedId==R.id.RadioButton3){
        		 tabHost.setCurrentTabByTag(ConValue.mTextviewArray[3]);  
        	 }
             /*switch (checkedId) {  
             case R.id.RadioButton0:  
                 tabHost.setCurrentTabByTag(ConValue.mTextviewArray[0]);  
                 break;  
             case R.id.RadioButton1:  
                 tabHost.setCurrentTabByTag(ConValue.mTextviewArray[1]);  
                 break;  
             case R.id.RadioButton2:  
                 tabHost.setCurrentTabByTag(ConValue.mTextviewArray[2]);  
                 break;  
             case R.id.RadioButton3:  
                 tabHost.setCurrentTabByTag(ConValue.mTextviewArray[3]);  
                 break;  
             }  */
         }  
     });  
     ((RadioButton) radioGroup.getChildAt(0)).toggle();  
 }  
    
 /** 
  * ��Tabѡ��������ݣ�ÿ�����ݶ���һ��Activity�� 
  */  
 private Intent getTabItemIntent(int index){  
     Intent intent = new Intent(this, ConValue.mTabClassArray[index]);     
     return intent;  
 }  
/*@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
   // TODO Auto-generated method stub
   if (keyCode == KeyEvent.KEYCODE_BACK) {
    long mExitTime = 0;
	if ((System.currentTimeMillis() - mExitTime) > 2000) {// ������ΰ���ʱ��������2000���룬���˳�
     Toast.makeText(this, "�ٰ�һ���˳�����", Toast.LENGTH_SHORT).show();
     mExitTime = System.currentTimeMillis();// ����mExitTime
     System.out.println("dff");
    } else {
    	System.out.println("exit");
     System.exit(0);// �����˳�����
     
    }
    return true;
   }
   return super.onKeyDown(keyCode, event);
 }
*/
@Override
public boolean dispatchKeyEvent(KeyEvent event) {
 // TODO Auto-generated method stub
  if (event.getAction()==KeyEvent.ACTION_DOWN&&event.getKeyCode()==KeyEvent.KEYCODE_BACK) {
   new AlertDialog.Builder(this)
          .setCancelable(false)
          .setTitle("��ܰ��ʾ")
          .setMessage("��ȷ��Ҫ�˳���?")
          .setPositiveButton("ȷ��",new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int which) { 
                 finish();
              }
          })
          .setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int which) { 
                
              }
          }).show();
   return true;//��֪������true����false��ʲô����??
 }
 
 return super.dispatchKeyEvent(event);
 
}
 /*
public boolean dispatchKeyEvent(KeyEvent event)  
{  
    int keyCode=event.getKeyCode();  
    switch(keyCode)  
    {  
        case KeyEvent.KEYCODE_BACK: {  
             if(event.isLongPress())  
             {  
                 this.stopService(getIntent());  
                 System.exit(0);  
                 return true;  
             }else  
             {  
                 return false;  

             }  
        }    
    }  
    return super.dispatchKeyEvent(event);  
      
}
*/
}