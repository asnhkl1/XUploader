# Uploader
类似前端框架antd的uploader
# 集成方式：
## Step 1. Add the JitPack repository to your build file<br>
Add it in your root build.gradle at the end of repositories:<br>
	```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	```
## Step 2. Add the dependency
	```
	  dependencies {
			implementation 'com.github.asnhkl1:XUploader:1.0.0'
		}
	```
## Step 3.XML
	```
	   <com.mrlee.library.XUploader
		android:id="@+id/uploader"
		app:layout_constraintTop_toTopOf="parent"
		app:layout_constraintLeft_toLeftOf="parent"
		android:layout_width="match_parent"
		android:layout_height="wrap_content"
		app:canEdit="true"
		app:maxSelectNum="2"
		/>
	```
        
        说明： <attr name="maxSelectNum" format="integer" /><!--最大选择数量-->
              <attr name="canEdit" format="boolean" /><!--是否可以编辑-->
  
## Step 4.Get Data
	```
	List<ResultData> imageData = uploader.getImageData();
	private String url = "";//真实URL
	private String thumbnail = "";//压缩过后的URL
	```
  
