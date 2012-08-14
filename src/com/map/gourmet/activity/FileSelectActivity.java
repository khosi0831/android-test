package com.map.gourmet.activity;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class FileSelectActivity  extends BaseActivity implements DialogInterface.OnClickListener,DialogInterface.OnKeyListener {
//public class FileSelectActivity  extends Activity  {
	private final static int WC=LinearLayout.LayoutParams.WRAP_CONTENT;
	private final static int MP=LinearLayout.LayoutParams.MATCH_PARENT;

	private	File		_fileCurrent;	//現在表示しているフォルダ
	private	File[]		_aFileList;		//現在表示しているフォルダのファイル一覧
	private	String[]	_astrFileName;	//現在表示しているフォルダのメニュー用ファイル名
	private	Context		_context;

	private	Dialog		_dlgThis;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.main);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        setContentView(layout);
        Show("/");
        final Button button = new Button(this);
        button.setText("ダイアログの表示");
//        button.setOnClickListener(new View.OnClickListener() {
//                    public void onClick(View v) {
//                        AlertDialog.Builder dlg;
//                        dlg = new AlertDialog.Builder(FileSelectActivity.this);
//                        dlg.setTitle("簡単なサンプル");
//                        dlg.setMessage("Hello, AlertDialog!");
//                        dlg.show();
//                    }
//                });
//		      button.setOnClickListener(new View.OnClickListener() {
//		      public void onClick(View v) {
//		    	  Toast.makeText(FileSelectActivity.this, "a", 0);
//		      }
//		  });
//
//        layout.addView(button);
		
	}

//	public FileSelectActivity(Context context)
//	{
//		super();
//		_context = context;
//	}

	@Override
	public void onPause()
	
	{
		if(_dlgThis != null && _dlgThis.isShowing())
			_dlgThis.dismiss();
		
		super.onPause();
	}


/*	@Override
	public void onResume()
	{
		Log.d("Test125","--onResume--- ");

		if(_dlgThis != null)
			_dlgThis.show();
		
		super.onResume();
	}

	public	String	GetCurrentPath()
	{
		if(_dlgThis == null || _dlgThis.isShowing() == false || _fileCurrent == null)
			return	"";

		return	_fileCurrent.getAbsolutePath();
	}
*/	

	public	boolean	Show(String strInitPath)
	{
		boolean	ret;

		ret = CreateFileList(strInitPath);
		if(ret == false)
			return	false;
		
		AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(FileSelectActivity.this);
		dlgBuilder.setCancelable(true);
		dlgBuilder.setOnKeyListener(this);
		dlgBuilder.setTitle(_fileCurrent.getPath());
		dlgBuilder.setItems(_astrFileName,this);
		_dlgThis = dlgBuilder.create();
		_dlgThis.show();

		return	true;
	}


	public void	Close(DialogInterface dialog,File fileSelected)
	{
//		((onSelectFileDialogListener)_context).onFileSelected_by_SelectFileDialog(fileSelected);
//		dialog.dismiss();
//		_dlgThis = null;
		//戻り値の指定
		Intent intent = new Intent();
		intent.putExtra("test", fileSelected.getName());
		setResult(Activity.RESULT_OK, intent);
		//アクティビティ終了
		finish();
		
	}


	@Override
	public void onClick(DialogInterface dialog, int which)
	{
		File	file = _aFileList[which];

		if(file.isDirectory())
		{
			//フォルダが選択されたので開く
			Show(file.getAbsolutePath());
			dialog.dismiss();
		}
		else
		{
			//選択されたので終了
			Close(dialog,file);
		}
	}


	@Override
	public boolean  onKey(DialogInterface dialog, int keyCode, KeyEvent event)
	{
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
		{
			File	fileParent;

			fileParent = _fileCurrent.getParentFile();
			if(fileParent != null)
			{
				Show(fileParent.getAbsolutePath());
				dialog.dismiss();
			}
			else
			{
				//ルートだったので終了
				Close(dialog,null);
			}

			return	true;
		}
		return	false;
	}


	private	boolean	CreateFileList(String strPath)
	{
		File[]	aFiles;
		
		_aFileList = null;
		_astrFileName = null;

		_fileCurrent = new File(strPath);
		if(_fileCurrent == null)
			return	false;
		
		aFiles = _fileCurrent.listFiles();
		if(aFiles == null || aFiles.length == 0)
		{
			_aFileList = new File[0];
			_astrFileName = new String[0];
			return	true;
		}
		

		int			i;
		int			nCount;
		String[]	astrName;

		astrName = new String[aFiles.length];

		nCount = 0;
		for(i = 0; i < aFiles.length; i++)
		{
			if(aFiles[i].isDirectory() && aFiles[i].isHidden() == false)
			{
				//ディレクトリの場合
				astrName[i] = aFiles[i].getName() + "/";
				nCount++;
			}
			else if(aFiles[i].isFile() && aFiles[i].isHidden() == false)
			{
				//通常のファイル
				astrName[i] = aFiles[i].getName();
				nCount++;
			}
			else
			{
				aFiles[i] = null;
			}
		}


		_aFileList = new File[nCount];
		_astrFileName = new String[nCount];

		nCount = 0;
		for(i = 0; i < aFiles.length; i++)
		{
			if(aFiles[i] != null)
			{
				_aFileList[nCount] = aFiles[i];
				_astrFileName[nCount] = astrName[i];
				nCount++;
			}
		}

		//ソートするならここでソート
		
		return	true;
	}
//
//	public interface onSelectFileDialogListener
//	{
//		public void onFileSelected_by_SelectFileDialog(File file);
//	}


}
