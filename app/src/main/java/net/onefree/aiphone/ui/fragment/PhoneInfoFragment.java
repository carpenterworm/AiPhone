package net.onefree.aiphone.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.opengl.GLSurfaceView;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import net.onefree.aiphone.R;
import net.onefree.aiphone.bean.PhoneInfo;
import net.onefree.aiphone.utils.NumberUtils;
import net.onefree.aiphone.utils.SDCardUtils;
import net.onefree.aiphone.utils.SystemInfoUtils;
import net.onefree.aiphone.view.CircleChart;
import net.onefree.aiphone.view.ExpandPanelView;


import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

/**
 * Created by maoah on 14-10-18.
 */
public class PhoneInfoFragment extends BaseFragment implements Handler.Callback {

    public CircleChart storage;
    CircleChart ram;
    CircleChart cpu;

    ExpandPanelView basePhoneInfo;
    ExpandPanelView display;
    ExpandPanelView camera;
    ExpandPanelView battery;
    ExpandPanelView cpuExpand;
    ExpandPanelView os;
    ExpandPanelView send;
    ExpandPanelView other;

    FrameLayout gpuLayout;

    Handler handler = new Handler(this);
    public static final byte Display_message = 0x1;
    //分辨率
    private int[] resolution;
    //dpi
    private int dpi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phone_info, null);


        mGLView = new GLSurfaceView(this.getActivity());
        mGLView.setRenderer(new ClearRenderer());
        initView(view);
        return view;
    }

    public void initView(View view) {

        storage = (CircleChart) view.findViewById(R.id.storage);
        ram = (CircleChart) view.findViewById(R.id.ram);
        cpu = (CircleChart) view.findViewById(R.id.cpu);
        basePhoneInfo = (ExpandPanelView) view.findViewById(R.id.base_phone_info);
        display = (ExpandPanelView) view.findViewById(R.id.display);
        camera = (ExpandPanelView) view.findViewById(R.id.camera);
        battery = (ExpandPanelView) view.findViewById(R.id.battery);
        cpuExpand = (ExpandPanelView) view.findViewById(R.id.cpu_expand_view);
        os = (ExpandPanelView) view.findViewById(R.id.os);
        send = (ExpandPanelView) view.findViewById(R.id.send);
        other = (ExpandPanelView) view.findViewById(R.id.other);
        gpuLayout = (FrameLayout) view.findViewById(R.id.gpu_layout);

        initData();

        setBaseCircleStatus();
        setBasePhoneInfoView();
//        setCameraPhoneInfoView();
        setCpuPhoneInfoView();
        setSendPhoneInfoView();
        setOtherPhoneInfoView();

        gpuLayout.addView(mGLView);

        this.getActivity().registerReceiver(mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    /**
     * 必须在主线程 初始化的数据
     */
    private void initData() {
        resolution = SystemInfoUtils.getPhoneResolution(this.getActivity());
        dpi = SystemInfoUtils.getDpi(this.getActivity());
    }

    private void setBaseCircleStatus() {
        final float scale = 1024f * 1024 * 1024;
        final float availableExternalMemorySize = SDCardUtils.getAvailableExternalMemorySize() / scale;
        final float totalExternalMemorySize = SDCardUtils.getTotalExternalMemorySize() / scale;
        final float availRAMSize = SystemInfoUtils.getAvailRAMSize(getActivity()) / 1024f / 1024;
        final float totalRAMSize = SystemInfoUtils.getTotalRAMSize(getActivity()) / 1024f;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                storage.setUsedTotal(availableExternalMemorySize, totalExternalMemorySize);
                storage.setUsedTotalText(NumberUtils.formatDecimals(availableExternalMemorySize, 1) + "G/" + NumberUtils.formatDecimals(totalExternalMemorySize, 1) + "G");
                ram.setUsedTotal(availRAMSize, totalRAMSize);
                ram.setUsedTotalText(Math.round(availRAMSize) + "M/" + Math.round(totalRAMSize) + "M");
            }
        }, 300);

    }


    private void addBasePhoneInfo(List<PhoneInfo> phoneInfos, String key, String value) {
        PhoneInfo phoneInfo = new PhoneInfo();
        phoneInfo.setKey(key);
        phoneInfo.setValuse(value);
        phoneInfos.add(phoneInfo);
    }

    /**
     * 查询 手机基本信息
     */
    private void setBasePhoneInfoView() {
        new LoadPhoneInfoAsyncTask() {
            @Override
            protected List<PhoneInfo> doInBackground(String... params) {
                List<PhoneInfo> phoneInfos = new ArrayList<PhoneInfo>();
                addBasePhoneInfo(phoneInfos, "品牌", SystemInfoUtils.getOSBrand());
                addBasePhoneInfo(phoneInfos, "手机型号", SystemInfoUtils.getOSModel());
                addBasePhoneInfo(phoneInfos, "系统版本", Build.VERSION.RELEASE);
                addBasePhoneInfo(phoneInfos, "分辨率", resolution[0] + "*" + resolution[1]);
                return phoneInfos;
            }

            @Override
            protected void onPostExecute(List<PhoneInfo> phoneInfos) {
                super.onPostExecute(phoneInfos);
                long backCameraPx = SystemInfoUtils.getCameraBackPx();
                long fontCameraPx = SystemInfoUtils.getCameraFontPx();
                if (backCameraPx > 0) {
                    addBasePhoneInfo(phoneInfos, "后置摄像头", String.valueOf(backCameraPx / 10000) + "万像素");
                }
                if (fontCameraPx > 0) {
                    addBasePhoneInfo(phoneInfos, "前置摄像头", String.valueOf(fontCameraPx / 10000) + "万像素");
                }
                basePhoneInfo.setPhoneInfos(phoneInfos);
                basePhoneInfo.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        basePhoneInfo.show();
                    }
                }, 400);
            }
        }.execute();
    }

    /**
     * 显示信息
     */
    private void setDisplayPhoneInfoView() {
        List<PhoneInfo> phoneInfos = new ArrayList<PhoneInfo>();
        addBasePhoneInfo(phoneInfos, "GPU渲染器", renderer);
        addBasePhoneInfo(phoneInfos, "供应商", vendor);
        addBasePhoneInfo(phoneInfos, "GPU版本", version);
        addBasePhoneInfo(phoneInfos, "分辨率", resolution[0] + "*" + resolution[1]);
        addBasePhoneInfo(phoneInfos, "屏幕密度", dpi + " dpi");
        display.setPhoneInfos(phoneInfos);
    }

    private void setCameraPhoneInfoView() {
        new LoadPhoneInfoAsyncTask() {

            @Override
            protected List<PhoneInfo> doInBackground(String... params) {
                List<PhoneInfo> phoneInfos = new ArrayList<PhoneInfo>();
                addBasePhoneInfo(phoneInfos, "屏幕密度", SystemInfoUtils.getDpi(getActivity()) + " dpi");
                addBasePhoneInfo(phoneInfos, "屏幕密度", SystemInfoUtils.getDpi(getActivity()) + " dpi");
                return phoneInfos;
            }

            protected void onPostExecute(List<PhoneInfo> phoneInfos) {
                camera.setPhoneInfos(phoneInfos);
            }
        }.execute();
    }

    private void setBatteryPhoneInfoView() {
        List<PhoneInfo> phoneInfos = new ArrayList<PhoneInfo>();
        addBasePhoneInfo(phoneInfos, "电量", BatteryN + "%");
        addBasePhoneInfo(phoneInfos, "温度", NumberUtils.formatDecimals(((float) (BatteryT * 0.1)), 1) + "℃");
        addBasePhoneInfo(phoneInfos, "状态", BatteryStatus + "");
        battery.setPhoneInfos(phoneInfos);
    }


    private void setCpuPhoneInfoView() {
        new LoadPhoneInfoAsyncTask() {

            @Override
            protected List<PhoneInfo> doInBackground(String... params) {
                List<PhoneInfo> phoneInfos = new ArrayList<PhoneInfo>();
                addBasePhoneInfo(phoneInfos, "CPU型号", SystemInfoUtils.getCpuName());
                addBasePhoneInfo(phoneInfos, "CPU个数", SystemInfoUtils.getNumCores() + "");
                try {
                    addBasePhoneInfo(phoneInfos, "CPU频率", Integer.valueOf(SystemInfoUtils.getMinCpuFreq()) / 1000 + " ~ " + Integer.valueOf(SystemInfoUtils.getMaxCpuFreq()) / 1000 + " MHz");
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                //        addBasePhoneInfo(phoneInfos, "CPU info", SystemInfoUtils.ReadCPUinfo() + "");
                return phoneInfos;
            }

            @Override
            protected void onPostExecute(List<PhoneInfo> phoneInfos) {
                cpuExpand.setPhoneInfos(phoneInfos);
                super.onPostExecute(phoneInfos);
            }
        }.execute();
    }

    private void setOsPhoneInfoView() {
        List<PhoneInfo> phoneInfos = new ArrayList<PhoneInfo>();
        addBasePhoneInfo(phoneInfos, "系统版本", Build.VERSION.RELEASE);
        addBasePhoneInfo(phoneInfos, "内核", SystemInfoUtils.getKernelVersion());
        os.setPhoneInfos(phoneInfos);
    }

    private void setSendPhoneInfoView() {
//        new LoadPhoneInfoAsyncTask() {
//
//            @Override
//            protected List<PhoneInfo> doInBackground(String... params) {
//                List<PhoneInfo> phoneInfos = new ArrayList<PhoneInfo>();
//                addBasePhoneInfo(phoneInfos, "WIFI", SystemInfoUtils.getWifiIP(getActivity()));
//                addBasePhoneInfo(phoneInfos, "GPS", SystemInfoUtils.getGpsStatus(getActivity()));
//                addBasePhoneInfo(phoneInfos, "NFC", SystemInfoUtils.getNfsStatus(getActivity()));
//                return phoneInfos;
//            }
//
//            @Override
//            protected void onPostExecute(List<PhoneInfo> phoneInfos) {
//                super.onPostExecute(phoneInfos);
//                send.setPhoneInfos(phoneInfos);
//            }
//        }.execute();

        List<PhoneInfo> phoneInfos = new ArrayList<PhoneInfo>();
        addBasePhoneInfo(phoneInfos, "WIFI", SystemInfoUtils.getWifiIP(getActivity()));
        addBasePhoneInfo(phoneInfos, "GPS", SystemInfoUtils.getGpsStatus(getActivity()));
        addBasePhoneInfo(phoneInfos, "NFC", SystemInfoUtils.getNfsStatus(getActivity()));
        send.setPhoneInfos(phoneInfos);
    }


    private void setOtherPhoneInfoView() {

//        new LoadPhoneInfoAsyncTask() {
//
//            @Override
//            protected List<PhoneInfo> doInBackground(String... params) {
//                List<Sensor> allSensors = SystemInfoUtils.getAllSensor(getActivity());
//                List<PhoneInfo> phoneInfos = new ArrayList<PhoneInfo>();
//                for (int i = 0; i < allSensors.size(); i++) {
//                    String name = SystemInfoUtils.getSensorEnName(allSensors.get(i).getType());
//                    addBasePhoneInfo(phoneInfos, TextUtils.isEmpty(name) ? allSensors.get(i).getName() : name, "");
//                }
//                return phoneInfos;
//            }
//
//            @Override
//            protected void onPostExecute(List<PhoneInfo> phoneInfos) {
//                super.onPostExecute(phoneInfos);
//                other.setPhoneInfos(phoneInfos);
//            }
//        }.execute();

        List<Sensor> allSensors = SystemInfoUtils.getAllSensor(getActivity());
        List<PhoneInfo> phoneInfos = new ArrayList<PhoneInfo>();
        for (int i = 0; i < allSensors.size(); i++) {
            String name = SystemInfoUtils.getSensorEnName(allSensors.get(i).getType());
            addBasePhoneInfo(phoneInfos, TextUtils.isEmpty(name) ? allSensors.get(i).getName() : name, "");
        }
        other.setPhoneInfos(phoneInfos);
    }

    private int BatteryN;       //目前电量
    private int BatteryV;       //电池电压
    private double BatteryT;        //电池温度
    private String BatteryStatus;   //电池状态
    private String BatteryTemp;     //电池使用情况

    /* 创建广播接收器 */
    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            /*
             * 如果捕捉到的action是ACTION_BATTERY_CHANGED， 就运行onBatteryInfoReceiver()
             */
            if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
                BatteryN = intent.getIntExtra("level", 0);    //目前电量
                BatteryV = intent.getIntExtra("voltage", 0);  //电池电压
                BatteryT = intent.getIntExtra("temperature", 0);  //电池温度

                switch (intent.getIntExtra("status", BatteryManager.BATTERY_STATUS_UNKNOWN)) {
                    case BatteryManager.BATTERY_STATUS_CHARGING:
                        BatteryStatus = "充电状态";
                        break;
                    case BatteryManager.BATTERY_STATUS_DISCHARGING:
                        BatteryStatus = "放电状态";
                        break;
                    case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                        BatteryStatus = "未充电";
                        break;
                    case BatteryManager.BATTERY_STATUS_FULL:
                        BatteryStatus = "充满电";
                        break;
                    case BatteryManager.BATTERY_STATUS_UNKNOWN:
                        BatteryStatus = "未知道状态";
                        break;
                }

            }

            switch (intent.getIntExtra("health", BatteryManager.BATTERY_HEALTH_UNKNOWN)) {
                case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                    BatteryTemp = "未知错误";
                    break;
                case BatteryManager.BATTERY_HEALTH_GOOD:
                    BatteryTemp = "状态良好";
                    break;
                case BatteryManager.BATTERY_HEALTH_DEAD:
                    BatteryTemp = "电池没有电";
                    break;
                case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                    BatteryTemp = "电池电压过高";
                    break;
                case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                    BatteryTemp = "电池过热";
                    break;
            }
            setBatteryPhoneInfoView();
            System.out.println("-----------" + "目前电量为" + BatteryN + "% --- " + BatteryStatus + "\n" + "电压为" + BatteryV + "mV --- " + BatteryTemp + "\n" + "温度为" + (BatteryT * 0.1) + "℃");
        }


    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.getActivity().unregisterReceiver(mBatInfoReceiver);
    }

    private GLSurfaceView mGLView;
    private String renderer = "";
    private String vendor = "";
    private String version = "";
    private String extensions = "";

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case Display_message:
                setDisplayPhoneInfoView();
                break;
            default:
                break;
        }
        return false;
    }

    class ClearRenderer implements GLSurfaceView.Renderer {
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            renderer = gl.glGetString(GL10.GL_RENDERER);
            vendor = gl.glGetString(GL10.GL_VENDOR);
            version = gl.glGetString(GL10.GL_VERSION);
            extensions = gl.glGetString(GL10.GL_EXTENSIONS);
            handler.sendEmptyMessage(Display_message);
        }

        public void onSurfaceChanged(GL10 gl, int w, int h) {
        }

        public void onDrawFrame(GL10 gl) {
            gl.glClear(GL11.GL_COLOR_BUFFER_BIT);
        }
    }

    @Override
    public void onResume() {
        // Ideally a game should implement onResume() and onPause()
        // to take appropriate action when the activity looses focus
        super.onResume();
        mGLView.onResume();
    }

    @Override
    public void onPause() {
        // Ideally a game should implement onResume() and onPause()
        // to take appropriate action when the activity looses focus
        super.onPause();
        mGLView.onPause();
    }


    abstract class LoadPhoneInfoAsyncTask extends AsyncTask<String, List<PhoneInfo>, List<PhoneInfo>> {
    }

    abstract class LoadPhoneSensorAsyncTask extends AsyncTask<String, List<PhoneInfo>, List<Sensor>> {
    }
}
