package org.rhythmcatchball.service;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
/*import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.windows.Win32FullScreenStrategy;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;*/

public class TutorialUI extends JFrame {
	
	public void TutorialUI() 
	{
		setTitle("튜토리얼");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane = getContentPane(); // 컨텐트팬 알아내기
		contentPane.setLayout(null);

		JButton View = new JButton("View");
		View.setSize(150, 150);
		View.setLocation(50, 50);
		JButton Exit = new JButton("Exit");
		Exit.setSize(100, 100);
		Exit.setLocation(700, 0);
		
		View.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                play_Tutorial();
	            }
	        });
		
		Exit.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	System.exit(0);
	            }
	        });
	 
		
		
		contentPane.add(View);
		contentPane.add(Exit);

		setSize(1000, 1000); // 프레임 크기 300x200 설정
		setVisible(true); // 프레임을 화면에 출력
	}
	
	public void play_Tutorial()// player와 동영상 파일 필요
	{
		// 배경그리기
		/*JFrame f = new JFrame();
		f.setLocation(100, 100);
		f.setSize(1000, 600);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setVisible(true);

		Canvas c = new Canvas();
		c.setBackground(Color.black);
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add(c);
		f.add(p);
		// 동영상 로드
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:/Program Files/VideoLAN/VLC");
		Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
		MediaPlayerFactory mpf = new MediaPlayerFactory();
		EmbeddedMediaPlayer emp = mpf.newEmbeddedMediaPlayer(new Win32FullScreenStrategy(f));
		emp.setVideoSurface(mpf.newVideoSurface(c));

		// emp.toggleFullScreen(); 풀스크린
		emp.setEnableMouseInputHandling(false);
		emp.setEnableKeyInputHandling(false);

		String fname = "RhythmCatchball.mp4";
		emp.prepareMedia(fname);
		emp.play();
         */
 
	}
	
	public static void main(String[] args) {
		 TutorialUI TUI = new TutorialUI();
		 TUI.TutorialUI();
		
	}
}

