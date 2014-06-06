package com.ndsec.wifisec;


import wificrack.WiFiCrackActivity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

 

@SuppressWarnings("deprecation")
public class MainTabActivity extends TabActivity {

 

 @Override

 public void onCreate(Bundle savedInstanceState) {

     super.onCreate(savedInstanceState);

     setContentView(R.layout.activity_maintabactivity);//Tabҳ��Ĳ���

     Resources res = getResources(); 

     TabHost tabHost = getTabHost();  // The activity TabHost

//     TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);

     TabSpec spec;

     Intent intent;  // Reusable Intent for each tab

 

   //��һ��TAB

     intent = new Intent(this,ListViewActivity.class);//�½�һ��Intent����Tab1��ʾ�����ݣ�����ShowActivity�Զ��壩
     spec = tabHost.newTabSpec("tab1")//�½�һ�� Tab
     .setIndicator("�������", res.getDrawable(android.R.drawable.ic_media_play))//���������Լ�ͼ��
     .setContent(intent);//������ʾ��intentx
     tabHost.addTab(spec);//��ӽ�ȥ

 

     //�ڶ���TAB

     intent = new Intent(this,WiFiCrackActivity.class);//�ڶ���Intent����Tab1��ʾ������
     spec = tabHost.newTabSpec("tab2")//�½�һ�� Tab
     .setIndicator("����������", res.getDrawable(android.R.drawable.ic_menu_camera))//���������Լ�ͼ��
     .setContent(intent);
     tabHost.addTab(spec);

     
     intent = new Intent(this,WiFiCrackActivity.class);//�ڶ���Intent����Tab1��ʾ������
     spec = tabHost.newTabSpec("tab3")//�½�һ�� Tab
     .setIndicator("��ȫ��Ѷ", res.getDrawable(android.R.drawable.ic_menu_camera))//���������Լ�ͼ��
     .setContent(intent);
     tabHost.addTab(spec);
     
     intent = new Intent(this,aboutme.ListViewActivity.class);//�ڶ���Intent����Tab1��ʾ������
     spec = tabHost.newTabSpec("tab4")//�½�һ�� Tab
     .setIndicator("��������", res.getDrawable(android.R.drawable.ic_menu_camera))//���������Լ�ͼ��
     .setContent(intent);
     tabHost.addTab(spec);
     

     tabHost.setCurrentTab(0);//����Ĭ��ѡ���� 

 }
}