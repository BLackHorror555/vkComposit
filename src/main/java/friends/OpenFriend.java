package friends;

import utils.DrawingUtils;

import java.awt.*;
import java.util.Random;

public class OpenFriend extends Friend {

    private boolean needToVisualizeFriends = true;
    private Random rand;

    public OpenFriend(int id) {
        super(id);
        rand = new Random();
    }

    @Override
    public void draw(Graphics graphics, Point point) {
        if (isHasFriends()) {
            graphics.drawOval(point.x, point.y, DrawingUtils.NODE_SIZE, DrawingUtils.NODE_SIZE);
        } else {
            graphics.drawRect(point.x, point.y, DrawingUtils.NODE_SIZE, DrawingUtils.NODE_SIZE);
        }

        if (needToVisualizeFriends && isHasFriends()) {
            for (int i = 0; i < friends.size(); i++) {
                Point friendPoint = new Point();
                int angle = i * (360 / friends.size()) + rand.nextInt(10) - 5;
                friendPoint.x = (int)(point.x + DrawingUtils.DISTANCE * Math.cos(angle));
                friendPoint.y = (int)(point.y + DrawingUtils.DISTANCE * Math.sin(angle));
                graphics.drawLine(point.x, point.y, friendPoint.x, friendPoint.y);

                friends.get(i).draw(graphics, friendPoint);
            }
        }
    }

    public void setNeedToVisualizeFriends(boolean needToVisualizeFriends) {
        this.needToVisualizeFriends = needToVisualizeFriends;
    }

    private boolean isHasFriends() {
        return !(friends == null || friends.isEmpty());
    }
}
