package code_base.ui.activity;

import android.content.Context;
import android.widget.TextView;

import com.damon.approot.R;
import code_base.util.DeviceUtils;
import code_base.ui.activity.BaseTitleActivity;
/**
 * 设备信息页
 */
public class DeviceActivity extends BaseTitleActivity {

    TextView Manufacturer;

    TextView Model;

    TextView DeviceName;

    TextView Version;

    TextView ROM;

    TextView CPU;

    TextView cpuABI;

    TextView widthPixel;

    TextView heightPixel;

    TextView realSize;

    TextView text;

    private Context mContext;


    public void initView() {
        setContentView(R.layout.activity_device);
        super.initView();

        Manufacturer = findViewById(R.id.Manufacturer);
        Model = findViewById(R.id.Model);
        DeviceName = findViewById(R.id.DeviceName);
        Version = findViewById(R.id.Version);
        ROM = findViewById(R.id.ROM);
        CPU = findViewById(R.id.CPU);
        cpuABI = findViewById(R.id.CPU_ABI);
        widthPixel = findViewById(R.id.widthPixel);
        heightPixel = findViewById(R.id.heightPixel);
        realSize = findViewById(R.id.physical);
        text = findViewById(R.id.devicecode);

        mContext = this;
        setmTTitle("我的设备");
        Manufacturer.setText(DeviceUtils.getManufacturer());
        Model.setText(DeviceUtils.getModel());
        DeviceName.setText(DeviceUtils.getDeviceName());
        Version.setText(DeviceUtils.getSDKVersion());

        ROM.setText(DeviceUtils.getDisplay() + "-" + DeviceUtils.getProductName());
        CPU.setText(DeviceUtils.getCpuType());
        cpuABI.setText(DeviceUtils.getCPU_ABI());
        widthPixel.setText(DeviceUtils.getScreenWidth(mContext) + "");
        heightPixel.setText(DeviceUtils.getScreenHeight(mContext) + "");
        realSize.setText("屏幕尺寸: " + DeviceUtils.getScreenInches(this) + "寸\n" + "  density: "
                + DeviceUtils.getScreenDensity(mContext)+"- scaleDensity: "+DeviceUtils.getScreenScaleDensity(mContext)+" -dpi "+DeviceUtils.getDPI(mContext));
        text.setText(DeviceUtils.getImei(mContext));

    }


}
