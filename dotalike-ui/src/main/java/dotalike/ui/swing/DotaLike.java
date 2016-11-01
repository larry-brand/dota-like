package dotalike.ui.swing;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tulskiy.keymaster.common.Provider;

import dotalike.common.model.Match;
import dotalike.core.main.DotaLikeEngine;
import dotalike.core.misc.GuiceFactory;
import dotalike.core.misc.MatchNotStartedException;
import dotalike.core.misc.StdOutErrLog;

public class DotaLike extends JFrame {

	private final Logger logger = LoggerFactory.getLogger(DotaLike.class);

	private MatchPanel matchPanel;

	private DotaLikeEngine dotaLikeEngine = GuiceFactory.getInjector().getInstance(DotaLikeEngine.class);

	public DotaLike() {
		super();
		initUI();
	}

	private void initUI() {
		setUIFont(new javax.swing.plaf.FontUIResource(Constants.font));

		matchPanel = new MatchPanel();
		getContentPane().add(matchPanel);

		setTitle("Dota Like v0.01");
		ImageIcon img = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\resources\\img\\windowIcon.png");
		setIconImage(img.getImage());
		setSize(1100, 550);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		
		// global hotkey for open-close window
		Provider provider = Provider.getCurrentProvider(true);
		provider.register(KeyStroke.getKeyStroke("control shift Z"), new CloseOpenHotKeyListener(this));
		
	}

	private void timer() {
		while (true) {
			Match currentMatch;
			Match previousMatch = matchPanel.getModel();
			try {
				currentMatch = dotaLikeEngine.getCurrentMatch();
				if (previousMatch == null || !(currentMatch.getStartDate().equals(previousMatch.getStartDate()))) {
					matchPanel.setModel(currentMatch);
					logger.debug("setting model in matchPanel");
				}
			} catch (MatchNotStartedException e1) {
				matchPanel.setModel(new Match());
				logger.debug("setting empty model in matchPanel");
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error("timer was interrupted");
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				StdOutErrLog.tieSystemOutAndErrToLog();
				final DotaLike main = new DotaLike();

				Thread threadCore = new Thread() {
					public void run() {
						main.timer();
					};
				};
				threadCore.start();
			}
		});
	}

	private static void setUIFont(javax.swing.plaf.FontUIResource f) {
		java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof javax.swing.plaf.FontUIResource) {
				UIManager.put(key, f);
			}
		}
	}

}