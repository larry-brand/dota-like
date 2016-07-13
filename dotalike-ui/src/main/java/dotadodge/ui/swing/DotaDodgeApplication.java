package dotadodge.ui.swing;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dotalike.common.model.Match;
import dotalike.core.main.DotaDodge;
import dotalike.core.misc.GuiceFactory;
import dotalike.core.misc.MatchNotStartedException;
import dotalike.core.misc.StdOutErrLog;

public class DotaDodgeApplication extends JFrame {
    
    private final Logger logger = LoggerFactory.getLogger(DotaDodgeApplication.class);
    
    private MatchPanel matchPanel;
    
    private DotaDodge dotaDodge = GuiceFactory.getInjector().getInstance(DotaDodge.class);
    
    public DotaDodgeApplication() {
        super();
        initUI();
    }
    
    private void initUI() {
    	setUIFont (new javax.swing.plaf.FontUIResource(new Font("Constantia",Font.BOLD, 18)));

    	matchPanel = new MatchPanel();
        getContentPane().add(matchPanel);

        setTitle("Dota Dodge");
        setSize(950, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
    
    private void initCore() {
	dotaDodge.run();
    }
    
    private void timer() {
	while (true) {
	    Match currentMatch;
	    Match previousMatch = matchPanel.getModel();
	    try {
		currentMatch = dotaDodge.getCurrentMatch();
		if (previousMatch == null || !(currentMatch.getStartDate().equals(previousMatch.getStartDate())))
		matchPanel.setModel(currentMatch);
	    } catch (MatchNotStartedException e1) {
		matchPanel.setModel(new Match());
		logger.trace("match not started yet");
	    }
	    try {
		Thread.sleep(1000);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
    }

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
        	StdOutErrLog.tieSystemOutAndErrToLog();
                final DotaDodgeApplication main = new DotaDodgeApplication();
                
                Thread threadCore = new Thread() {
        	    public void run() {
        		main.initCore();
        		main.timer();
        	    };
        	};
        	threadCore.start();
            }
        });
    }
    
    private static void setUIFont(javax.swing.plaf.FontUIResource f)
    {
        java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements())
        {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource)
            {
                UIManager.put(key, f);
            }
        }
    }

}
