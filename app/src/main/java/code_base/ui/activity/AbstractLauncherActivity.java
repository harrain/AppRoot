package code_base.ui.activity;

public class AbstractLauncherActivity extends BaseActivity {

    @Override
    public void initView() {
        super.initView();

        requestStoragePermission();
    }
}
