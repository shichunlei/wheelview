# wheelviewLibrary

滚轮

![image](Screenshot_2017-08-29-15-07-50-279_com.chingtech.)

To get a Git project into your build:

Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:
  
    allprojects {
        repositories {
            ...
            maven { url "https://jitpack.io" }
        }
    }
  
  
Step 2. Add the dependency

	dependencies {
	    compile 'com.github.shichunlei:wheelview:1.2.0'
	}
