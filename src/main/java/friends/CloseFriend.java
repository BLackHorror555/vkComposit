package friends;

import java.awt.*;
import java.util.List;

public class CloseFriend extends Friend {

    public CloseFriend(int id) {
        super(id);
    }

    @Override
    public void draw(Graphics graphics, Point point) {
        graphics.drawRect(point.x, point.y, 3, 3);
    }

    @Override
    public void setFriends(List<Friend> friends) {
    }
}
