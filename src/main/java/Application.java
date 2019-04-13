import friends.Friend;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Application extends JPanel {

    private List<Friend> friendsToDraw;

    private Application() {
        VkApiHelper vkApiHelper = new VkApiHelper();
        vkApiHelper.initClient();
        try {
            vkApiHelper.authenticate(183184707);
            friendsToDraw = vkApiHelper.getFriends();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Application application = new Application();
        SwingUtilities.invokeLater(() -> createAndShowGUI(application));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1500, 1500);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (friendsToDraw != null) {
            for (Friend friend : friendsToDraw) {
                friend.draw(g, new Point(500, 250));
            }
        }
    }

    private static void createAndShowGUI(Application application) {
        JFrame f = new JFrame("Graph friend");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.add(application);
        f.pack();
        f.setVisible(true);
    }
}
