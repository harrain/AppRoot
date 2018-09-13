
android{

    compileSdkVersion
    buildToolsVersion ""
    defaultConfig{
        applicationId ""
        minSdkVersion
        targetSdkVersion    // = compileSdk > minSdkVersion
        versionCode
        versionName ""

        multiDexEnable true
    }

    sourceSets{
        main{
            res.srcDirs = [
                'src/main/res-base',
                'src/main/res'
            ]
        }
    }

    signingConfigs{
        release{
            storeFile file("keystore.jks")
            storePassword "helloworld"
            storeType "jks"
            keyAlias ""
            keyPassword "helloworld"
            v2SigningEnable false
        }
    }

    buildTypes{
        release{
            shrinkResource false
            zipAlignEnable true
            //debuggable true 可以使release状态下仍可以debug 看见log
        }
    }

    //打包apk时出现的问题解决办法
    lintOptions {
        checkReleaseBuilds false
        abortOnError false
    }

    //关掉AndroidStudio对PNG图片的合法性检验
    aaptOptions {

        cruncherEnabled = false

        useNewCruncher = false

    }
}

dependencies{

    implementation 'com.android.support:multidex:1.0.3'
    implementation 'com.android.support:recyclerview-v7:'
    implementation 'com.zhy:base-rvadapter:3.0.3' //recyclerview adapter
    implementation 'com.yqritc:recyclerview-flexibledivider:1.4.0' //recyclerview decoration
    implementation 'com.ashokvarma.android:bottom-navigation-bar:2.0.4'
    implementation 'com.github.bumptech.glide:glide:3.7.0'

    implementation 'io.reactivex.rxjava2:rxandroid:2.0.1'
    implementation 'com.tbruyelle.rxpermissions2:rxpermissions:0.9.4@aar' //permissions
    implementation 'com.squareup.okhttp3:okhttp:3.8.1'
    implementation 'com.squareup.okhttp3:logging-interceptor:3.8.1'
    implementation 'com.google.code.gson:gson:2.7'

    implementation  'joda-time:joda-time:2.3'//强大的时间处理库
}