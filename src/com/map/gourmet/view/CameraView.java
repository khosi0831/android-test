package com.map.gourmet.view;

import java.io.FileOutputStream;

import android.content.Context;
import android.hardware.Camera;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
public class CameraView extends SurfaceView implements SurfaceHolder.Callback, Camera.PictureCallback {
	private SurfaceHolder holder; //�z���_�[
	private Camera camera; //�J����
	public  static String path = "";
	public Context _context;
	public CameraView(Context context) {
		super(context);
		
		//�T�[�t�F�C�X�t�H���_�̍쐬
		holder = getHolder();
		holder.addCallback(this);
		
		//�v�b�V���o�b�t�@�̎w��
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
	}
	//�T�[�t�F�C�X�����C�x���g�̏�����
	public void surfaceCreated(SurfaceHolder holder) {
		//�J�����̏�����
		try {
			camera = Camera.open();
			camera.setPreviewDisplay(holder);
		} catch(Exception e) {
		}
	}
	
	//�T�[�t�F�C�X�ύX�C�x���g�̏���
	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		//�J�����v���r���[�̊J�n
		camera.startPreview();
	}
	
	//�T�[�t�F�C�X����C�x���g�̏���
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		//�J�����v���r���[�̒�~
		camera.setPreviewCallback(null);
		camera.stopPreview();
		camera.release();
		camera = null;
	}
	
	//�^�b�`���ɂ�΂��
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			//�J�����̃X�N���[���V���b�g�̎擾
			camera.takePicture(null, null, this);
		}
		return false;
	}
	//�ʐ^�B�e�������ɌĂ΂��
	public void onPictureTaken(byte[] data, Camera camera) {
		//SD�J�[�h�ւ̃f�[�^�ۑ�
		try {
			path = Environment.getExternalStorageDirectory() + "/test.jpeg";
			data2file(data, path);
			
		}catch(Exception e) {
			
		}
	}
	
	//�o�C�g�f�[�^���t�@�C��
	private void data2file(byte[] w, String fileName) throws Exception {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(fileName);
			out.write(w);
			out.close();
		} catch(Exception e) {
			if(out!=null) {
				out.close();
			}
			throw e;
		}
	}
}
