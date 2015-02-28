package net.onefree.aiphone.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;

import net.onefree.aiphone.MainAcitivity;
import net.onefree.aiphone.bean.Devices;

public class SystemInfoUtils {

    /**
     * Build.BOARD // 主板
     * Build.BRAND // android系统定制商
     * Build.CPU_ABI // cpu指令集
     * Build.DEVICE // 设备参数
     * Build.DISPLAY // 显示屏参数
     * Build.FINGERPRINT // 硬件名称
     * Build.HOST
     * Build.ID // 修订版本列表
     * Build.MANUFACTURER // 硬件制造商
     * Build.MODEL // 版本
     * Build.PRODUCT // 手机制造商
     * Build.TAGS // 描述build的标签
     * Build.TIME
     * Build.TYPE // builder类型
     * Build.USER
     */
    /////////////////


    public static String TAG = SystemInfoUtils.class.getSimpleName();

    /**
     * Ram 总大小
     */
    public static long getTotalRAMSize(Context context) {
//		if (android.os.Build.VERSION.SDK_INT >= 16) {
//			return getTotalRamJellYBean(context);
//		} else {
//			return getTotalRam();
//		}

        return getTotalRam();


    }

    private static long getTotalRam() {
        RandomAccessFile reader = null;
        long total = 0;
        try {
            reader = new RandomAccessFile("/proc/meminfo", "r");
            Pattern p = Pattern.compile("[0-9]+");
            Matcher m = p.matcher(reader.readLine());
            m.find();
            total = Long.parseLong(m.group());
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return total;
    }

//	/**
//	 *
//	 * @param context
//	 * @return kb
//	 */
//	@SuppressLint("NewApi")
//	private static long getTotalRamJellYBean(Context context) {
//		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//		ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
//		am.getMemoryInfo(memInfo);
//		return memInfo.totalMem / 1024;
//	}

    /**
     * 可用 Ram
     *
     * @param context
     * @return
     */
    public static long getAvailRAMSize(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(memInfo);
        return memInfo.availMem;
    }

    /**
     * 手机型号
     *
     * @return
     */
    public static String getOSModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 设备厂商
     *
     * @return
     */
    public static String getOSBrand() {
        return Build.BRAND;
    }


    /**
     * 分辩率
     *
     * @param activity
     * @return
     */
    public static int[] getPhoneResolution(Activity activity) {
        int[] resolution = new int[2];
        Display mDisplay = activity.getWindowManager().getDefaultDisplay();
        resolution[0] = mDisplay.getWidth();
        resolution[1] = mDisplay.getHeight();
        return resolution;
    }

    /**
     * 获取屏幕密度
     *
     * @param activity
     * @return
     */
    public static int getDpi(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;  // 屏幕宽度（像素）
        int height = metric.heightPixels;  // 屏幕高度（像素）
        float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
        return densityDpi;
    }

    // 获取CPU名字
    public static String getCpuName() {
        try {
            FileReader fr = new FileReader("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split(":\\s+", 2);
            return array[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 获取CPU最大频率（单位KHZ）
    // "/system/bin/cat" 命令行
    // "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq" 存储最大频率的文件的路径
    public static String getMaxCpuFreq() {
        String result = "";
        ProcessBuilder cmd;
        try {
            String[] args = {"/system/bin/cat",
                    "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            result = "N/A";
        }
        return result.trim();
    }


    // 获取CPU最小频率（单位KHZ）
    public static String getMinCpuFreq() {
        String result = "";
        ProcessBuilder cmd;
        try {
            String[] args = {"/system/bin/cat",
                    "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            result = "N/A";
        }
        return result.trim();
    }

    // 实时获取CPU当前频率（单位KHZ）
    public static String getCurCpuFreq() {
        String result = "N/A";
        try {
            FileReader fr = new FileReader(
                    "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            result = text.trim();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Gets the number of cores available in this device, across all processors.
     * Requires: Ability to peruse the filesystem at "/sys/devices/system/cpu"
     *
     * @return The number of cores, or 1 if failed to get result
     */
    public static int getNumCores() {
        // Private Class to display only CPU devices in the directory listing
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                // Check if filename is "cpu", followed by a single digit number
                if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }

        try {
            // Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            // Filter to only list the devices we care about
            File[] files = dir.listFiles(new CpuFilter());
            return files.length;
        } catch (Exception e) {
            e.printStackTrace();
            // Default to return 1 core
            return 1;
        }
    }

    public static String ReadCPUinfo() {
        ProcessBuilder cmd;
        String result = "";

        try {
            String[] args = {"/system/bin/cat", "/proc/cpuinfo"};
            cmd = new ProcessBuilder(args);

            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[1024];
            while (in.read(re) != -1) {
                System.out.println(new String(re));
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }


    /**
     * 系统内核
     *
     * @return
     */
    public static String getKernelVersion() {
        String kernelVersion = "";
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("/proc/version");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return kernelVersion;
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 8 * 1024);
        String info = "";
        String line = "";
        try {
            while ((line = bufferedReader.readLine()) != null) {
                info += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            if (info != "") {
                final String keyword = "version ";
                int index = info.indexOf(keyword);
                line = info.substring(index + keyword.length());
                index = line.indexOf(" ");
                kernelVersion = line.substring(0, index);
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return info;
    }


    /**
     * 检查gps 状态
     *
     * @param context
     * @return
     */
    public static String getGpsStatus(Context context) {
        if (!hasGPSDevice(context)) {
            return "不支持";
        }
        if (isEnableGPS(context)) {
            return "开启";
        }

        return "未开启";
    }

    /**
     * 是否支持gps
     *
     * @param context
     * @return
     */
    public static boolean hasGPSDevice(Context context) {
        final LocationManager mgr = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (mgr == null)
            return false;
        final List<String> providers = mgr.getAllProviders();
        if (providers == null)
            return false;
        return providers.contains(LocationManager.GPS_PROVIDER);
    }

    /**
     * 是否支持gps
     *
     * @param context
     * @return
     */
    public static boolean isEnableGPS(Context context) {
        LocationManager locationManager =
                ((LocationManager) context.getSystemService(Context.LOCATION_SERVICE));
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     *
     * @param context
     * @return true 表示开启
     */
    public static final boolean isGpsAgpsOPen(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }


    /**
     * 所有传感器
     *
     * @param context
     * @return
     */
    public static List<Sensor> getAllSensor(Context context) {
        //从系统服务中获得传感器管理器
        SensorManager sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        //从传感器管理器中获得全部的传感器列表
        List<Sensor> allSensors = sm.getSensorList(Sensor.TYPE_ALL);
        return allSensors;
    }


    /**
     * 传感器名字
     * <p/>
     * #define SENSOR_TYPE_ACCELEROMETER       1 //加速度
     * #define SENSOR_TYPE_MAGNETIC_FIELD      2 //磁力
     * #define SENSOR_TYPE_ORIENTATION         3 //方向
     * #define SENSOR_TYPE_GYROSCOPE           4 //陀螺仪
     * #define SENSOR_TYPE_LIGHT               5 //光线感应
     * #define SENSOR_TYPE_PRESSURE            6 //压力
     * #define SENSOR_TYPE_TEMPERATURE         7 //温度
     * #define SENSOR_TYPE_PROXIMITY           8 //接近
     * #define SENSOR_TYPE_GRAVITY             9 //重力
     * #define SENSOR_TYPE_LINEAR_ACCELERATION 10//线性加速度
     * #define SENSOR_TYPE_ROTATION_VECTOR     11//旋转矢量
     *
     * @param type
     * @return
     */
    public static String getSensorEnName(int type) {
        switch (type) {
            case Sensor.TYPE_ACCELEROMETER:
                return "加速度传感器";
            case Sensor.TYPE_MAGNETIC_FIELD:
                return "电磁场传感器";
            case Sensor.TYPE_ORIENTATION:
                return "方向传感器";
            case Sensor.TYPE_GYROSCOPE:
                return "陀螺仪传感器";
            case Sensor.TYPE_LIGHT:
                return "环境光线传感器";
            case Sensor.TYPE_PRESSURE:
                return "压力传感器";
            case Sensor.TYPE_TEMPERATURE:
                return "温度传感器";
            case Sensor.TYPE_PROXIMITY:
                return "距离传感器";
            case Sensor.TYPE_GRAVITY:
                return "重力传感器";
            case Sensor.TYPE_LINEAR_ACCELERATION:
                return "线性加速度传感器";
            case Sensor.TYPE_ROTATION_VECTOR:
                return "旋转矢量度传感器";
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                return "湿度传感器";
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                return "温度传感器";
            case Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED:
                return "磁场传感器";
            case Sensor.TYPE_GAME_ROTATION_VECTOR:
                return "GAME_ROTATION_VECTOR";
            case Sensor.TYPE_GYROSCOPE_UNCALIBRATED:
                return "GYROSCOPE_UNCALIBRATED";
            case Sensor.TYPE_SIGNIFICANT_MOTION:
                return "SIGNIFICANT_MOTION";
            case Sensor.TYPE_STEP_DETECTOR:
                return "计步传感器";
            case Sensor.TYPE_STEP_COUNTER:
                return "计数传感器";
            case Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR:
                return "GEOMAGNETIC_ROTATION_VECTOR";
            default:
                return "";
        }

    }

    /**
     * 检查wifi状态
     *
     * @param context
     * @return
     */
    public static String getWifiIP(Context context) {

        WifiManager wifimanage = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);//获取WifiManager
        //检查wifi是否开启
        if (!wifimanage.isWifiEnabled()) {
            return "未开启";
        }
        if (!isWifiConnect(context)) {
            return "未连接网络";
        }
        WifiInfo wifiinfo = wifimanage.getConnectionInfo();
        int i = wifiinfo.getIpAddress();
        //将获取的int转为真正的ip地址,参考的网上的，修改了下
        return intToIp(i);
    }

    private static String intToIp(int i) {
        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }

    /**
     * wifi 是否连接网络
     *
     * @param context
     * @return
     */
    public static boolean isWifiConnect(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return mWifi.isConnected();
    }

    /**
     * 检查nfc 状态
     *
     * @param context
     * @return
     */
    public static String getNfsStatus(Context context) {
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(context);
        if (nfcAdapter == null) {
            return "不支持";
        }
        if (nfcAdapter.isEnabled()) {
            return "开启";
        }
        return "未开启";
    }

    /**
     * 获得相机像素
     *
     * @param id
     * @return 0 -> 没有此相机
     */
    public static long getCameraPx(int id) {
        Camera c = null;
        long px = 0;
        try {
            c = Camera.open(id); // attempt to get a Camera instance
            List<Camera.Size> sizes = c.getParameters().getSupportedPictureSizes();
            px = sizes.get(0).width * sizes.get(0).height;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.stopPreview();
                c.release();
                c = null;
            }
        }
        return px;
    }

    /**
     * 一个获取相机对象实例的安全方法
     */
    public static long getCameraBackPx() {
        return getCameraPx(Camera.CameraInfo.CAMERA_FACING_BACK);
    }

    public static long getCameraFontPx() {
        return getCameraPx(Camera.CameraInfo.CAMERA_FACING_FRONT);
    }

    public int getCameraId(int type) {
        int numberOfCameras = Camera.getNumberOfCameras();
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == type) {
                return i;
            }
        }
        return 2;
    }

    /**
     * 设备 信息
     *
     * @param context
     * @return
     */
    public static Devices getDevicesInfo(Context context) {
        Devices devices = new Devices();
        devices.setBrand(Build.BRAND);
        devices.setModel(Build.MODEL);
        devices.setOsVersion(android.os.Build.VERSION.RELEASE);
        devices.setDevicesId(getPhoneUniquenessString(context));
        return devices;
    }

    /**
     * 获取手机唯一标识
     *
     * @return
     */
    public static String getPhoneUniquenessString(Context context) {
        StringBuffer sb = new StringBuffer();

        TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = TelephonyMgr.getDeviceId();
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String macAddress = "";
        if (wm != null && wm.getConnectionInfo() != null && wm.getConnectionInfo().getMacAddress() != null) {
            macAddress = wm.getConnectionInfo().getMacAddress().replace(":", "");
        }
        if (!TextUtils.isEmpty(imei)) {
            sb.append(imei);
        }
        if (!TextUtils.isEmpty(macAddress)) {
            sb.append(macAddress);
        }
        if (TextUtils.isEmpty(sb.toString())) {
            sb.append(Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID));
        }
        return sb.toString();
    }
}
