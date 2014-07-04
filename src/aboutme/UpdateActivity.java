package aboutme;

import aboutme.UpdateService;
import aboutme.Config;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class UpdateActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(com.ndsec.wifisec.R.layout.aboutme_list);
		checkVersion();
	}
	public void checkVersion() {

		if (Config.localVersion < Config.serverVersion) {
			Log.i("shibin", "==============================");
			// �����°汾����ʾ�û�����
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("�������")
			.setMessage("�����°汾,������������ʹ��.")
			.setPositiveButton("����",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									// �������·���UpdateService
									// ����Ϊ�˰�update����ģ�黯�����Դ�һЩupdateService������ֵ
									// �粼��ID����ԴID����̬��ȡ�ı���,������app_nameΪ��
									
									Intent updateIntent = new Intent(
											UpdateActivity.this,
											UpdateService.class);
									updateIntent.putExtra("titleId",
											com.ndsec.wifisec.R.string.app_name);
									startService(updateIntent);
									
								}
							})
					.setNegativeButton("ȡ��",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							});
			alert.create().show();
		} else {
			// ����������ȥ
			// cheanUpdateFile(),���º����һḽ�ϴ���
		}
	}
}