package aboutme;

import com.ndsec.wifisec.Params;
import wificrack.WiFiCrackActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class ListViewActivity extends Activity {
	private ListView mylistview;
	private List<Params> list = makeList();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(com.ndsec.wifisec.R.layout.main_listview);
		mylistview = (ListView) findViewById(com.ndsec.wifisec.R.id.listview_main);
		ArrayList<HashMap<String, String>> myArrayList = new ArrayList<HashMap<String, String>>();
	
	for (Params p : list) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id",p.getId());
		map.put("name", p.getName());
		
		myArrayList.add(map);
	}
	SimpleAdapter mySimpleAdapter = new SimpleAdapter(this, myArrayList,// ����Դ
			com.ndsec.wifisec.R.layout.aboutme_list,// ListView�ڲ�����չʾ��ʽ�Ĳ����ļ�listitem.xml
			new String[] { "id", "name" },// HashMap�е�����keyֵ
														// itemTitle��itemContent
			new int[] { com.ndsec.wifisec.R.id.itemID, com.ndsec.wifisec.R.id.itemName});/*
															 * �����ļ�listitem.
															 * xml�������id
															 * �����ļ��ĸ�����ֱ�ӳ�䵽HashMap�ĸ�Ԫ����
															 * ���������
															 */
	mylistview.setAdapter(mySimpleAdapter);

	/*
	 * mylistview.setOnTouchListener(new OnTouchListener(){
	 * 
	 * @Override public boolean onTouch(View v, MotionEvent event) { // TODO
	 * Auto-generated method stub if(event.getAction() ==
	 * MotionEvent.ACTION_DOWN) { mylistview.setBackgroundColor(Color.BLUE);
	 * } return false; }
	 * 
	 * });
	 */

	mylistview.setOnItemClickListener(new OnItemClickListener() {
/**
* http://www.oschina.net/question/263483_46547
*/
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			 switch (arg2){
			 //�������
				 case 0:
			  Intent intent = new Intent(ListViewActivity.this,WiFiCrackActivity.class);
              //intent.setClass(ListViewActivity.this, ListActivity.class);
			  intent.putExtra("params", list.get(arg2));  
              startActivity(intent);
              System.out.println("aaa");
              break;
              //�汾����
				 case 1:
					  Intent intent1 = new Intent(ListViewActivity.this,aboutme.UpdateActivity.class);
		              //intent.setClass(ListViewActivity.this, ListActivity.class);
					  intent1.putExtra("params", list.get(arg2));  
		              startActivity(intent1);
		              System.out.println("aaa");
		              break;
		      //���ܽ���
				 case 2:
					  Intent intent2 = new Intent(ListViewActivity.this,com.ndsec.wifisec.SwitchActivity.class);
		              //intent.setClass(ListViewActivity.this, ListActivity.class);
					  intent2.putExtra("params", list.get(arg2));  
		              startActivity(intent2);
		              System.out.println("aaa");
		              break;
		      //�������
				 case 3:
					  Intent intent3 = new Intent(ListViewActivity.this,com.ndsec.wifisec.SwitchActivity.class);
		              //intent.setClass(ListViewActivity.this, ListActivity.class);
					  intent3.putExtra("params", list.get(arg2));  
		              startActivity(intent3);
		              System.out.println("aaa");
		              break;
		       //�����Ұ�
				 case 4:
					  Intent intent4 = new Intent(ListViewActivity.this,com.ndsec.wifisec.SwitchActivity.class);
		              //intent.setClass(ListViewActivity.this, ListActivity.class);
					  intent4.putExtra("params", list.get(arg2));  
		              startActivity(intent4);
		              System.out.println("aaa");
		              break;
		       //��������
				 case 5:
					  Intent intent5 = new Intent(ListViewActivity.this,com.ndsec.wifisec.SwitchActivity.class);
		              //intent.setClass(ListViewActivity.this, ListActivity.class);
					  intent5.putExtra("params", list.get(arg2));  
		              startActivity(intent5);
		              System.out.println("aaa");
		              break;
				 case 6:
					  Intent intent6 = new Intent(ListViewActivity.this,com.ndsec.wifisec.SwitchActivity.class);
		              //intent.setClass(ListViewActivity.this, ListActivity.class);
					  intent6.putExtra("params", list.get(arg2));  
		              startActivity(intent6);
		              System.out.println("aaa");
		              break;
				 case 7:
					  Intent intent7 = new Intent(ListViewActivity.this,com.ndsec.wifisec.SwitchActivity.class);
		              //intent.setClass(ListViewActivity.this, ListActivity.class);
					  intent7.putExtra("params", list.get(arg2));  
		              startActivity(intent7);
		              System.out.println("aaa");
		              break;
				 case 8:
					  Intent intent8 = new Intent(ListViewActivity.this,com.ndsec.wifisec.SwitchActivity.class);
		              //intent.setClass(ListViewActivity.this, ListActivity.class);
					  intent8.putExtra("params", list.get(arg2));  
		              startActivity(intent8);
		              System.out.println("aaa");
		              break;
				 case 9:
					  Intent intent9 = new Intent(ListViewActivity.this,com.ndsec.wifisec.SwitchActivity.class);
		              //intent.setClass(ListViewActivity.this, ListActivity.class);
					  intent9.putExtra("params", list.get(arg2));  
		              startActivity(intent9);
		              System.out.println("aaa");
		              break;
			 }
		}

	});
}

private List<Params> makeList() {
	List<Params> l = new ArrayList<Params>();
	int i = 0;
		Params p1 = new Params();
		p1.setId("id" + i);
		i = i + 1;
		p1.setName("�������");
		l.add(p1);
		
		Params p2 = new Params();
		p2.setId("id" + i);
		i = i + 1;
		p2.setName("�汾����");
		l.add(p2);
		
		Params p3 = new Params();
		p3.setId("id" + i);
		i = i + 1;
		p3.setName("���ܽ���");
		l.add(p3);
		
		Params p4 = new Params();
		p4.setId("id" + i);
		i = i + 1;
		p4.setName("�������");
		l.add(p4);
		
		Params p5 = new Params();
		p5.setId("id" + i);
		i = i + 1;
		p5.setName("�����Ұ�");
		l.add(p5);
		
		Params p6 = new Params();
		p6.setId("id" + i);
		i = i + 1;
		p6.setName("��������");
		l.add(p6);
		
	
	return l;
}
}