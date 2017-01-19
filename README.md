# [DYP Qanunlar və Cərimələr](https://play.google.com/store/apps/details?id=com.mobapphome.avtolowpenal) - Simple android catalog application

[![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=15) [![Hex.pm](https://img.shields.io/hexpm/l/plug.svg?maxAge=2592000)](http://www.apache.org/licenses/LICENSE-2.0)

<img src="https://raw.githubusercontent.com/hummatli/DYPQanunlarVeCerimeler/master/icon_create/imgs_github/img1.png" width="200px"/>
<img src="https://raw.githubusercontent.com/hummatli/DYPQanunlarVeCerimeler/master/icon_create/imgs_github/img2.png" width="200px"/>
<img src="https://raw.githubusercontent.com/hummatli/DYPQanunlarVeCerimeler/master/icon_create/imgs_github/img3.png" width="200px"/>

## Contributors
* Developer:
[Sattar Hummatli](https://github.com/hummatli) - [LinkedIn](https://www.linkedin.com/in/hummatli), settarxan@gmail.com, [Other libs](https://github.com/hummatli/DYPQanunlarVeCerimeler#other-libraries-by-developer)


## Description
`DYP Qanunlar və Cərimələr` is sample catalog appliaction containing collection of Azerbaijan Republic Traffic Rules - Offences and Penalties. It is live on [Google Play Market](https://play.google.com/store/apps/details?id=com.mobapphome.avtolowpenal) but has depreciated due to old version of rules and penals list. Application has build on IDE `Android Studio 2.2.3` by native java language. It has integrated following APIs and libs:

## Integrated APIs and libraries
* Firebase - For advertisment lib
* Fabirc.io(Answers) - for realtime analytics
* MAHAds lib - for advertisment of my own apps
* MAHAndroidUpdater lib - to keep fresh and updated

## How can you use it
* To create application like this but with fresh data of Azerbaijan Republic Traffic Rules - Offences and Penalties
* To create another catalog application with different kind of data
* To learn Android programing based on this sample


## Sample App in PlayStore
<a href="https://play.google.com/store/apps/details?id=com.mobapphome.avtolowpenal">DYP Qanunlar ve Cerimeler</a> app has published on Google PlayStore. You can easly test module functionality with downloading it.
<br><a href="https://play.google.com/store/apps/details?id=com.mobapphome.avtolowpenal"><img src="https://raw.githubusercontent.com/hummatli/DYPQanunlarVeCerimeler/master/icon_create/imgs_github/google-play-badge.png" width="200px"/></a> 

## How to configure
You can remove from project any of following APIs if you don't want to use them
* Firebase. To use Admob in your project you have to configure it [Link](https://firebase.google.com/docs/admob/). 
You will need followings:
    **1.** google-service.json   
    **2** Admob ids     
	`<string name="admob_banner_unit_id">Your banner id</string>`    
	`<string name="admob_interstitial_unit_id">your interstitial id</string>`
* Fabric.io - You will need your fabric key to add Manafest file, [Link](https://docs.fabric.io/android/fabric/overview.html)
* MAHAds lib - [Link](https://github.com/hummatli/MAHAds)
* MAHAndroidUpdater - [Link](https://github.com/hummatli/MAHAndroidUpdater)


## Help - Issues
If you have any problem with setting and using library or want to ask question, please let me know. Create [issue](https://github.com/hummatli/DYPQanunlarVeCerimeler/issues) or write to <i><a href="mailto:settarxan@gmail.com">settarxan@gmail.com</a></i>. I will help.

## Proguard configuration
MAHAndroidUpdater uses <a href="https://github.com/google/gson">Gson</a> and <a href="https://github.com/jhy/jsoup">Jsoup</a> libs. There for if you want to create your project with proguard you need to add following configuration to your proguard file.

```gradle
##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }

##---------------End: proguard configuration for Gson  ----------

##---------------Begin: proguard configuration for Jsoup--------------------------------
-keep public class org.jsoup.** {
public *;
}
##---------------End: proguard configuration for Jsoup--------------------------------
```


## Releases - Upgrade documentation
See [releases](https://github.com/hummatli/MAHAndroidUpdater/releases). Please,read release notes to migrate your app from old version to a newer.

## To contribute
I am open to here offers and opinions from you 

* Fork it
* Create your feature branch (git checkout -b my-new-feature)
* Commit your changes (git commit -am 'Added some feature')
* Push to the branch (git push origin my-new-feature)
* Create new Pull Request
* Star it

## Localization
Library now supports following languages 
* Azerbaijan
* English
* Hindi
* Portuguese
* Russia
* Turkey
* [Add your language](https://github.com/hummatli/MAHAndroidUpdater/blob/master/README.md#to-contribute-for-localization)

### To contribute for localization
We need help to add new language localization support for libarary. If you have any hope to help us we were very happy and you can check following <i><a href="https://github.com/hummatli/MAHAndroidUpdater/issues">GitHub Issues URL</a></i> to contribute. To contribute get <a href="https://github.com/hummatli/MAHAndroidUpdater/blob/master/MAHAndroidUpdater/mah-android-updater/src/main/res/values/strings.xml">res/values/string.xml</a> file and translate to newer language. Place it on res/values-"spacific_lang"/string.xml

## Applications using MAHAndroidUpdater
Please [ping](mailto:settarxan@gmail.com) me or send a pull request if you would like to see your app in the start of the list.

Icon | Application | Icon | Application
------------ | ------------- | ------------- | -------------
[Your app] |[ping](mailto:settarxan@gmail.com) me or send a pull request | <img src="https://lh3.googleusercontent.com/UhNXotmmhpK3eCV5XEeLSX555Tu_k-A9VgqrPK_4EWLJsJaCUugNVGEahafCrO45Lg=w300-rw" width="48" height="48" /> | [Indian Railway PNRStatus IRCTC](https://play.google.com/store/apps/details?id=com.emilartin.travel.indianrailwaypnrstatusirctc)
<img src="https://project-943403214286171762.firebaseapp.com/mah_ads_dir/imgs/millionaire_ru.png" width="48" height="48" /> | [Миллионер - на Pусском](https://play.google.com/store/apps/details?id=com.mobapphome.millionaire.ru) | <img src="https://project-943403214286171762.firebaseapp.com/mah_ads_dir/imgs/millionaire_tr.png" width="48" height="48" /> | [Milyoner - Türkçe](https://play.google.com/store/apps/details?id=com.mobapphome.millionaire.tr)
<img src="https://project-943403214286171762.firebaseapp.com/mah_ads_dir/imgs/millionaire_en.png" width="48" height="48" /> | [Millionaire - in English](https://play.google.com/store/apps/details?id=com.mobapphome.millionaire.en) | <img src="https://lh3.ggpht.com/kfuLs-Ic0xR3SOFdjJ3FVeI0es2oXTCEt1T2y8tEVeYm7otSuSSBDlrpz4wXtIygf4k=w300-rw" width="48" height="48" /> | [Məzənnə](https://play.google.com/store/apps/details?id=com.mobapphome.currency)
<img src="https://project-943403214286171762.firebaseapp.com/mah_ads_dir/imgs/mah_ads_sample_icon.png" width="48" height="48" /> | [MAHAds - Sample](https://play.google.com/store/apps/details?id=com.mobapphome.mahads.sample) | <img src="https://project-943403214286171762.firebaseapp.com/mah_ads_dir/imgs/mah_android_updater_sample_icon.png" width="48" height="48" /> | [MAHAndroidUpdater - Sample](https://play.google.com/store/apps/details?id=com.mobapphome.mahandroidupdater.sample)
<img src="https://project-943403214286171762.firebaseapp.com/mah_ads_dir/imgs/mah_encryptor_lib_sample_icon.png" width="48" height="48" /> | [MAHEncryptorLib - Sample](https://play.google.com/store/apps/details?id=com.mobapphome.mahencryptorlib) | <img src="https://lh5.ggpht.com/P_TyFmB5BzYDGWl3yliDHkQr_ttrYzHS3yQk3mBS3QuJJ5TJZ1pMj8lx-wmUmAHiUw=w300-rw" width="48" height="48" /> | [Ləzzət](https://play.google.com/store/apps/details?id=com.mobapphome.lezzet)
<img src="https://project-943403214286171762.firebaseapp.com/mah_ads_dir/imgs/millionaire_az.png" width="48" height="48" /> | [Milyonçu](https://play.google.com/store/apps/details?id=com.mobapphome.milyoncu) | | 


## Other libraries by developer
* [![MAHAds](https://img.shields.io/badge/GitHUB-MAHAds-green.svg)](https://github.com/hummatli/MAHAds) - Library for advertisement own apps through your other apps.  
* [![MAHEncryptorLib](https://img.shields.io/badge/GitHUB-MAHEncryptorLib-green.svg)](https://github.com/hummatli/MAHEncryptorLib) - Library for encryption and decryption strings on Android apps and PC Java applications.

## License
Copyright 2016  - <a href="https://www.linkedin.com/in/hummatli">Sattar Hummatli</a>   

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
