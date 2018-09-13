buildscript{

    repositories{
        google() //as3.0 必备
        jcenter()
    }
    dependencies{
        //系统tools.build:gradle版本    kotlin-gradle-plugin版本
        classpath 'org.greenrobot:greendao-gradle-plugin:3.2.2' //greendao数据库orm
    }
}

allprojects{
    repositores{
        google()
        jcenter()
    }
}